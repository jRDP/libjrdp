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
package de.coderarea.jrdp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Main class for testing.
 *
 * @author Sascha Biedermann
 */
public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        logger.info("Startup...");

        //FileInputStream fis = new FileInputStream("/tmp/connectGCCPDU.per");

        //ConnectGCCPDU connectGCCPDU = new ConnectGCCPDU(new BitInputStream(fis));

      /*  ConferenceCreateResponse ccr = new ConferenceCreateResponse();
        ccr.setNodeID(new UserID(10902));
        ccr.setTag(new ASN1Integer(1234567890));
        ccr.setResult(Result.resourcesNotAvailable);
        ccr.setUserData(new UserData());

        UserDataItem udi = new UserDataItem();
        udi.setKey(new Key(new H221NonStandardIdentifier(new byte[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20})));
       // ccr.getUserData().add(udi);

        udi = new UserDataItem();
        udi.setKey(new Key(new H221NonStandardIdentifier(new byte[] {1,2,3,4})));
        udi.setValue(new ASN1OctetString(new byte[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16}));
        ccr.getUserData().add(udi);


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ASN1Encoder perEncoder = ASN1Encoder.newEncoder(ASN1EncodingRules.PER, bos);

        ConnectGCCPDU pdu = new ConnectGCCPDU(ccr);
        perEncoder.encode(pdu); */

        /*ASN1Integer val = new ASN1Integer();


        val.setValue(-1);
        perEncoder.encode(val);
        logger.debug("VAL: {}", val.getValue());


        val.setValue(1);
        perEncoder.encode(val);
        logger.debug("VAL: {}", val.getValue());

        byte[] buffer = bos.toByteArray();

        ASN1Decoder<ConnectGCCPDU> perDecoder = ASN1Decoder.newDecoder(ASN1EncodingRules.PER, new ByteArrayInputStream(buffer));
        ConnectGCCPDU pdu2 = perDecoder.decode(ConnectGCCPDU.class);


        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        ASN1Encoder perEncoder2 = ASN1Encoder.newEncoder(ASN1EncodingRules.PER, bos2);
        perEncoder2.encode(pdu2);
        byte[] buffer2 = bos2.toByteArray();

        logger.debug("a: {}, b: {}, macht: {}", buffer.length, buffer2.length, Arrays.equals(buffer, buffer2));
        */

        /*

        FileOutputStream fos = new FileOutputStream("/tmp/ccr.per");
        fos.write(buffer);
        fos.close();

        System.out.println();

        for (int i = 0; i<buffer.length; i++) {
            System.out.format("%02X ", buffer[i]);
        }

        System.out.println();
        */

        ServerSocket serverSocket = new ServerSocket(13389);//, 5, InetAddress.getLoopbackAddress());

        while (true) {
            // blocks until connection comes in
            Socket connectionSocket = serverSocket.accept();
            ConnectionHandler handler = new ConnectionHandler(connectionSocket);
            new Thread(handler).start();
        }
    }
}
