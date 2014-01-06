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
package de.coderarea.jrdp.protocol.X224;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * This is an implementation of a Class 0 X224ConnectionRequest-TPDU as in X.224 Section 13.3.1.
 *
 * @author Sascha Biedermann
 * @see <a href="http://www.itu.int/rec/T-REC-X.224-199511-I/en">X.224 Section 13.3.1</a>
 */
public class X224ConnectionRequest extends X224Packet {
    private final static Logger logger = LogManager.getLogger(X224ConnectionRequest.class);

    /**
     * CR – Connection request code: 1110.
     * Bits 8 to 5 of octet 2.
     */
    public final static byte CODE = (byte) 0xE0;

    /**
     * CDT – Initial credit allocation (set to 0000 in classes 0 and 1 when specified as preferred class).
     * Bits 4 to 1 of octet 2.
     */
    private final static byte CDT = 0x00;

    private int srcRef;
    private RdpNegReq rdpNegReq;

    protected void decode(InputStream input) throws IOException {

        int length = input.read();

        byte cr_cdt = (byte) input.read();
        byte cr = (byte) (cr_cdt & 0xF0);

        if (cr != CODE)
            throw new IllegalArgumentException(String.format("Expected X224ConnectionRequest-Code 0x%02X, got 0x%02X", CODE, cr));

        byte cdt = (byte) (cr_cdt & 0x0F);
        if (cdt != CDT)
            throw new IllegalArgumentException(String.format("Expected CDT 0x%02X, got 0x%02X", CDT, cdt));

        int dstRef = input.read() << 8 | input.read();
        if (dstRef != 0) {
            throw new IllegalArgumentException(String.format("Expected dstRef 0, got %d", dstRef));
        }

        this.srcRef = input.read() << 8 | input.read();

        int classOption = input.read();
        if (classOption != 0) {
            throw new IllegalArgumentException(String.format("Expected class option 0x00, got 0x%02X", classOption));
        }

        /* TODO better read all into a byte array
           then use a ByteArrayInputStream for further processing after this line
        */
        length -= 6; // length of X224 Header, excluding LengthIndicator

        if (length > 0) {
            byte type = (byte) input.read();
            length--;


            if (type == 0x03) {
                // routingToken 0x03 \r\n
                throw new UnsupportedOperationException("routingToken not implemented");
            } else if (type == 0x043) {
                // Cookie 0x43 \r\n
                //throw new UnsupportedOperationException("Cookie not implemented");

                String cookie = new BufferedReader(new InputStreamReader(input)).readLine();
                length -= cookie.length() + 2;

                // type contains the 'C', needs to be prepended
                cookie = 'C' + cookie;

                logger.debug("Cookie: {}", cookie);


                if (length > 0) {
                    type = (byte) input.read();
                    length--;
                } else {
                    return;
                }
            }

            //rdpNegReq 0x01 8
            if (type == 0x01) {
                byte[] data = new byte[8];
                data[0] = 0x01;
                input.read(data, 1, 7);
                length -= 7;
                this.rdpNegReq = new RdpNegReq();
                rdpNegReq.decode(new ByteArrayInputStream(data));

                if (length > 0)
                    type = (byte) input.read();
                length--;
            }

            //rdpCorrelationInfo 0x06 36
            if (type == 0x06) {
                throw new UnsupportedOperationException("rdpCorrelationInfo not implemented");
            }
        }

    }

    public byte[] encode() throws IOException {
        throw new UnsupportedOperationException();
    }

    public int getSrcRef() {
        return srcRef;
    }

    public int getDstRef() {
        return 0;
    }

    @Override
    public String toString() {
        return "X224ConnectionRequest{" +
                "srcRef=" + srcRef +
                ", rdpNegReq=" + rdpNegReq +
                '}';
    }
}
