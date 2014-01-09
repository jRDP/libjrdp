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
import de.coderarea.jrdp.protocol.ASN1.ASN1Encoder;
import de.coderarea.jrdp.protocol.ASN1.ASN1EncodingRules;
import de.coderarea.jrdp.protocol.ASN1.ASN1OctetString;
import de.coderarea.jrdp.protocol.MCS.*;
import de.coderarea.jrdp.protocol.TS.TsValidLicenseData;

import java.io.ByteArrayOutputStream;

/**
 * @author Sascha Biedermann
 */
public class LicensingSequence extends SequenceHandler {


    public LicensingSequence(ConnectionHandler connectionHandler) {
        super(connectionHandler);
    }


    @Override
    public void run() throws Exception {

        // send a Server License Error PDU to signal Licensing is OK, wtf M$ ?

        SendDataIndication sdi = new SendDataIndication();
        sdi.setInitiator(new UserId(getConnectionHandler().getSettings().getChannelRegistry().getChannel("server").getId()));
        sdi.setChannelId(new ChannelId(getConnectionHandler().getSettings().getChannelRegistry().getChannel("io").getId()));

        // TODO what is this for?
        sdi.setReliability(true);

        // TODO what should this be?
        sdi.setDataPriority(DataPriority.medium);

        sdi.setSegmentation(new Segmentation(true, true));


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        new TsValidLicenseData().encode(bos);

        sdi.setUserData(new ASN1OctetString(bos.toByteArray()));

        DomainMCSPDU domain = new DomainMCSPDU();
        domain.setSendDataIndication(sdi);

        bos = new ByteArrayOutputStream();
        ASN1Encoder encoder = ASN1Encoder.newEncoder(ASN1EncodingRules.PER, bos);
        encoder.encode(domain);

        getConnectionHandler().sendX224DataPacket(bos.toByteArray());

    }
}
