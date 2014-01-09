/**
 * This file is part of libjrdp.
 *
 * libjrdp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * libjrdp is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with libjrdp. If not, see <http://www.gnu.org/licenses/>.
 */
package de.coderarea.jrdp.protocol.connection;

import de.coderarea.jrdp.ConnectionHandler;
import de.coderarea.jrdp.protocol.ASN1.ASN1Decoder;
import de.coderarea.jrdp.protocol.ASN1.ASN1Encoder;
import de.coderarea.jrdp.protocol.ASN1.ASN1EncodingRules;
import de.coderarea.jrdp.protocol.MCS.*;
import de.coderarea.jrdp.protocol.RDPChannelRegistry;
import de.coderarea.jrdp.protocol.TS.TsBasicSecurityHeader;
import de.coderarea.jrdp.protocol.TS.TsInfoPacket;
import de.coderarea.jrdp.protocol.TS.TsSecurityFlag;
import de.coderarea.jrdp.protocol.TS.TsSecurityPacket;
import de.coderarea.jrdp.protocol.X224.X224Data;
import de.coderarea.jrdp.protocol.X224.X224Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Sascha Biedermann
 */
public class ChannelConnectionSequence extends SequenceHandler {
    private final static Logger logger = LogManager.getLogger(ChannelConnectionSequence.class);

    public ChannelConnectionSequence(ConnectionHandler connectionHandler) {
        super(connectionHandler);
    }

    private DomainMCSPDU receiveDomainMCSPDU() throws IOException {
        X224Packet packet = getConnectionHandler().receiveX224Packet();
        assert packet instanceof X224Data;
        X224Data data = (X224Data) packet;
        ASN1Decoder<DomainMCSPDU> decoder = ASN1Decoder.newDecoder(ASN1EncodingRules.PER, new ByteArrayInputStream(data.getData()));
        DomainMCSPDU domain = decoder.decode(DomainMCSPDU.class);
        debugDump("DomainMCSPDU.per", data.getData());
        return domain;
    }

    private ErectDomainRequest receiveErectDomainRequest() throws IOException {
        DomainMCSPDU domain = receiveDomainMCSPDU();
        assert domain.getErectDomainRequest() != null;
        logger.trace("{}", domain.getErectDomainRequest());
        return domain.getErectDomainRequest();
    }

    private AttachUserRequest receiveAttachUserRequest() throws IOException {
        DomainMCSPDU domain = receiveDomainMCSPDU();

        assert domain.getAttachUserRequest() != null;
        logger.trace("{}", domain.getAttachUserRequest());
        return domain.getAttachUserRequest();
    }

    private void sendAttachUserConfirm() throws IOException {
        AttachUserConfirm attachUserConfirm = new AttachUserConfirm();

        attachUserConfirm.setResult(Result.rt_successful);

        // TODO: set a meaningful number
        attachUserConfirm.setInitiator(new UserId(getConnectionHandler().getSettings().getChannelRegistry().getChannel("user").getId()));

        DomainMCSPDU domain = new DomainMCSPDU();
        domain.setAttachUserConfirm(attachUserConfirm);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ASN1Encoder encoder = ASN1Encoder.newEncoder(ASN1EncodingRules.PER, bos);
        encoder.encode(domain);

        getConnectionHandler().sendX224DataPacket(bos.toByteArray());
    }

    private ChannelJoinRequest receiveChannelJoinRequest() throws IOException {
        DomainMCSPDU domain = receiveDomainMCSPDU();

        assert domain.getChannelJoinRequest() != null;
        logger.trace("{}", domain.getChannelJoinRequest());
        return domain.getChannelJoinRequest();
    }

    private void sendChannelJoinConfirm(UserId initiator, ChannelId channelId) throws IOException {
        ChannelJoinConfirm channelJoinConfirm = new ChannelJoinConfirm();


        channelJoinConfirm.setResult(Result.rt_successful);

        // The initiator value which was sent in the corresponding MCS Channel Join Request PDU.
        channelJoinConfirm.setInitiator(initiator);

        // The MCS channel ID which was sent in the corresponding MCS Channel Join Request PDU.
        channelJoinConfirm.setRequested(channelId);

        // The MCS channel ID which was sent in the corresponding MCS Channel Join Request PDU.
        channelJoinConfirm.setChannelId(channelId);

        DomainMCSPDU domain = new DomainMCSPDU();
        domain.setChannelJoinConfirm(channelJoinConfirm);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ASN1Encoder encoder = ASN1Encoder.newEncoder(ASN1EncodingRules.PER, bos);
        encoder.encode(domain);

        getConnectionHandler().sendX224DataPacket(bos.toByteArray());
    }

    @Override
    public void run() throws Exception {
        receiveErectDomainRequest();
        receiveAttachUserRequest();
        sendAttachUserConfirm();


        DomainMCSPDU pdu = receiveDomainMCSPDU();
        while (pdu.getChannelJoinRequest() != null) {
            ChannelJoinRequest channelJoinRequest = pdu.getChannelJoinRequest();
            logger.trace("ChannelJoinRequest: {}", channelJoinRequest);
            int id = channelJoinRequest.getChannelId().getValue();
            RDPChannelRegistry.Channel chan = getConnectionHandler().getSettings().getChannelRegistry().getChannel(id);
            assert chan != null : "Channel unknown.";
            chan.setState(RDPChannelRegistry.ChannelState.JOINED);
            logger.trace("Channel [{}] ({}) joined.", chan.getName(), chan.getId());

            sendChannelJoinConfirm(channelJoinRequest.getInitiator(), channelJoinRequest.getChannelId());
            pdu = receiveDomainMCSPDU();
        }

        logger.trace("{}", pdu.getSendDataRequest());

        ByteArrayInputStream bis = new ByteArrayInputStream(pdu.getSendDataRequest().getUserData().getValue());
        TsBasicSecurityHeader header = new TsBasicSecurityHeader(bis);

        TsInfoPacket infoPacket = null;

        if (header.getFlags().contains(TsSecurityFlag.SEC_EXCHANGE_PKT)) {
            TsSecurityPacket pack = new TsSecurityPacket(bis, header);
            logger.trace("{}", pack);

            pdu = receiveDomainMCSPDU();
            logger.trace("{}", pdu.getSendDataRequest());
            bis = new ByteArrayInputStream(pdu.getSendDataRequest().getUserData().getValue());
            header = new TsBasicSecurityHeader(bis);
            assert header.getFlags().contains(TsSecurityFlag.SEC_INFO_PKT);
            infoPacket = new TsInfoPacket(bis, header);

        } else if (header.getFlags().contains(TsSecurityFlag.SEC_INFO_PKT)) {
            infoPacket = new TsInfoPacket(bis, header);

        }

        logger.trace("{}", infoPacket);



        /*pdu = receiveDomainMCSPDU();
        logger.trace("{}",pdu.getSendDataRequest());
         pack = new TsSecurityPacket(new ByteArrayInputStream(pdu.getSendDataRequest().getUserData().getValue()));
        logger.trace("{}", pack);*/
        //req = receiveChannelJoinRequest();
        //sendChannelJoinConfirm(req.getInitiator(), req.getChannelId());
    }
}
