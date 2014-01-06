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
package de.coderarea.jrdp.protocol;

import de.coderarea.jrdp.ConnectionHandler;
import de.coderarea.jrdp.SequenceHandler;
import de.coderarea.jrdp.protocol.ASN1.*;
import de.coderarea.jrdp.protocol.GCC.*;
import de.coderarea.jrdp.protocol.MCS.BN_BER.Connect_Initial;
import de.coderarea.jrdp.protocol.MCS.BN_BER.Connect_Response;
import de.coderarea.jrdp.protocol.MCS.BN_BER.Result;
import de.coderarea.jrdp.protocol.TS.*;
import de.coderarea.jrdp.protocol.X224.X224Data;
import de.coderarea.jrdp.protocol.X224.X224Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bn.CoderFactory;
import org.bn.IDecoder;
import org.bn.IEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by uidv6498 on 13.12.13.
 *
 * @author Sascha Biedermann
 */
public class BasicSettingsExchangeSequence extends SequenceHandler {
    private final static Logger logger = LogManager.getLogger(BasicSettingsExchangeSequence.class);

    private final static String CLIENT_TO_SERVER_KEY = "Duca";
    private final static String SERVER_TO_CLIENT_KEY = "McDn";

    private TsUserData tsUserData;

    public BasicSettingsExchangeSequence(ConnectionHandler connectionHandler) {
        super(connectionHandler);
    }

    private Connect_Initial.Connect_InitialSequenceType receiveConnectInitial() throws Exception {
        X224Packet x224 = getConnectionHandler().receiveX224Packet();
        assert x224 instanceof X224Data : "Expected X224Data packet, got: " + x224.getClass().getSimpleName();
        X224Data dt = (X224Data) x224;
        debugDump("Connect_Initial.ber", dt.getData());

        IDecoder berDecoder = CoderFactory.getInstance().newDecoder("BER");
        Connect_Initial ci = berDecoder.decode(new ByteArrayInputStream(dt.getData()), Connect_Initial.class);

        // TODO verify stuff
        return ci.getValue();
    }


    private ConferenceCreateRequest decodeConferenceCreateRequest(Connect_Initial.Connect_InitialSequenceType connectInitial) throws IOException {
        // Connect_Initial encapsulates ConnectData
        debugDump("ConnectData.per", connectInitial.getUserData());
        ASN1Decoder<ConnectData> connectDataASN1Decoder = ASN1Decoder.newDecoder(ASN1EncodingRules.PER, new BitInputStream(connectInitial.getUserData()));
        ConnectData connectData = connectDataASN1Decoder.decode(ConnectData.class);

        // ConnectData encapsulates ConnectGCCPDU
        debugDump("ConnectGCCPDU.per", connectData.getConnectPDU());
        ASN1Decoder<ConnectGCCPDU> connectGCCPDUASN1Decoder = ASN1Decoder.newDecoder(ASN1EncodingRules.PER, new BitInputStream(connectData.getConnectPDU()));
        ConnectGCCPDU connectGCCPDU = connectGCCPDUASN1Decoder.decode(ConnectGCCPDU.class);

        // ConnectGCCPDU encapsulates ConferenceCreateRequest
        assert connectGCCPDU.getConferenceCreateRequest() != null : "Expected ConferenceCreateRequest in ConnectGCCPDU";
        ConferenceCreateRequest conferenceCreateRequest = connectGCCPDU.getConferenceCreateRequest();
        return conferenceCreateRequest;
    }

    private TsUserData decodeTsUserData(ConferenceCreateRequest conferenceCreateRequest) {
        UserData userData = conferenceCreateRequest.getUserData();
        assert userData != null;
        assert userData.size() == 1 : "Expected 1 UserDataItem, got " + userData.size();

        UserDataItem userDataItem = userData.get(0);

        assert userDataItem.getKey() != null;
        assert userDataItem.getKey().getH221NonStandard() != null : "Expected H221NonStandardIdentifier in Key, but is null";
        String key = null;
        try {
            key = new String(userDataItem.getKey().getH221NonStandard().getValue(), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        assert CLIENT_TO_SERVER_KEY.equals(key) : "Expected CLIENT_TO_SERVER_KEY, but got " + key;
        assert userDataItem.getValue() != null;

        try {
            // TODO validate. maybe here or in run()
            return new TsUserData(new ByteArrayInputStream(userDataItem.getValue().getValue()));
        } catch (IOException e) {
            return null;
        }
    }

    private List<TsData> composeTsServerData() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        List<TsData> tsDataList = new ArrayList<>();

        tsDataList.add(new TsServerCoreData());

        if (tsUserData.getClientNetworkData() != null) {
            TsServerNetworkData tsServerNetworkData = new TsServerNetworkData();

            tsServerNetworkData.setmCSChannelId(getConnectionHandler().getSettings().getChannelRegistry().getChannel("io").getId());
            for (TsClientNetworkData.TsChannelDefinition c : tsUserData.getClientNetworkData().getChannelDefArray()) {
                RDPChannelRegistry.Channel chan = getConnectionHandler().getSettings().getChannelRegistry().getChannel(c.getName());
                int id = chan != null ? chan.getId() : 0;
                tsServerNetworkData.getChannelIdArray().add(id);

                if (chan == null)
                    logger.warn("Unknown channel: {}", c);
            }

            tsDataList.add(tsServerNetworkData);
        }
        tsDataList.add(new TsServerSecurityData());
        return tsDataList;
    }


    private ConferenceCreateResponse composeConferenceCreateResponse(List<TsData> tsServerData) throws IOException {

        ConferenceCreateResponse conferenceCreateResponse = new ConferenceCreateResponse();
        conferenceCreateResponse.setNodeID(new UserID(1001));

        // SHOULD be 1
        conferenceCreateResponse.setTag(new ASN1Integer(1));

        // SHOULD be success
        conferenceCreateResponse.setResult(de.coderarea.jrdp.protocol.GCC.Result.success);


        // create the UserDataItem and set it's key to magic SERVER_TO_CLIENT_KEY
        UserDataItem userDataItem = new UserDataItem();
        userDataItem.setKey(new Key(new H221NonStandardIdentifier(SERVER_TO_CLIENT_KEY.getBytes("US-ASCII")))); // should be ANSI

        // encode tsServerData and assign as UserDataItem value
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        TsUserData.encode(bos, tsServerData);
        userDataItem.setValue(new ASN1OctetString(bos.toByteArray()));

        // create UserData from the UserDataItem and assign to VonferenceCreateResponse UserData
        conferenceCreateResponse.setUserData(new UserData(Collections.singletonList(userDataItem)));

        return conferenceCreateResponse;
    }


    private ConnectData composeConnectData(ConferenceCreateResponse conferenceCreateResponse) throws IOException {
        // encapsulate ConferenceCreateResponse in ConnectGCCPDU
        ConnectGCCPDU connectGCCPDU = new ConnectGCCPDU();
        connectGCCPDU.setConferenceCreateResponse(conferenceCreateResponse);

        // encapsulate ConnectGCCPDU in ConnectData
        ConnectData connectData = new ConnectData();
        // TODO put static identifier in: {0, 20, 124, 0, 1 } (?)
        connectData.setT124Identifier(new Key(new ASN1ObjectIdentifier(new byte[]{0, 20, 124, 0, 1})));
        // encode ConnectGCCPDU and set ConnectPDU data
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ASN1Encoder encoder = ASN1Encoder.newEncoder(ASN1EncodingRules.PER, bos);
        encoder.encode(connectGCCPDU);
        connectData.setConnectPDU(bos.toByteArray());

        return connectData;
    }

    private void sendConnectResponse(ConnectData connectData) throws Exception {
        Connect_Response.Connect_ResponseSequenceType connectResponse = new Connect_Response.Connect_ResponseSequenceType();

        // result SHOULD be rt_successful
        Result result = new Result();
        result.setValue(Result.EnumType.rt_successful);
        connectResponse.setResult(result);

        // SHOULD be set to 0
        connectResponse.setCalledConnectId(0L);

        connectResponse.setDomainParameters(getConnectionHandler().getSettings().getDomainParameters());

        // encapsulate ConnectData in ConnectResponse's UserData field
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ASN1Encoder encoder = ASN1Encoder.newEncoder(ASN1EncodingRules.PER, bos);
        encoder.encode(connectData);
        connectResponse.setUserData(bos.toByteArray());


        // Encode MCS Connect_Response
        Connect_Response cr = new Connect_Response();
        cr.setValue(connectResponse);
        bos = new ByteArrayOutputStream();
        IEncoder berEncoder = CoderFactory.getInstance().newEncoder("BER");
        berEncoder.encode(cr, bos);

        // Send Packet
        getConnectionHandler().sendX224DataPacket(bos.toByteArray());
    }

    @Override
    public void run() throws Exception {

        // 1. RECEIVE and DECODE
        Connect_Initial.Connect_InitialSequenceType connectInitial = receiveConnectInitial();
        ConferenceCreateRequest conferenceCreateRequest = decodeConferenceCreateRequest(connectInitial);
        tsUserData = decodeTsUserData(conferenceCreateRequest);

        logger.debug("{}", tsUserData.getClientCoreData());
        logger.debug("{}", tsUserData.getClientSecurityData());
        logger.debug("{}", tsUserData.getClientClusterData());
        logger.debug("{}", tsUserData.getClientNetworkData());


        // 2. PROCESS

        // TODO: domainParameters field MUST be initialized with the parameters
        // which were derived from processing of the MCS Connect Initial PDU (see section 3.3.5.3.3 for a
        // description of the negotiation rules).

        // HACK: send back, what came in
        getConnectionHandler().getSettings().setDomainParameters(connectInitial.getTargetParameters());
        logger.trace("{}", getConnectionHandler().getSettings().getDomainParameters());


        // 3. RESPOND
        List<TsData> tsServerData = composeTsServerData();
        logger.debug("{}", tsServerData);
        ConferenceCreateResponse conferenceCreateResponse = composeConferenceCreateResponse(tsServerData);
        ConnectData connectData = composeConnectData(conferenceCreateResponse);
        sendConnectResponse(connectData);
    }

}
