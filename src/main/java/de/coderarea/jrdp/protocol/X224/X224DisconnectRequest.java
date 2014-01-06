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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * This is an implementation of a Class 0 X224ConnectionRequest-TPDU as in X.224 Section 13.3.1.
 *
 * @author Sascha Biedermann
 * @see <a href="http://www.itu.int/rec/T-REC-X.224-199511-I/en">X.224 Section 13.3.1</a>
 */
public class X224DisconnectRequest extends X224Packet {
    /**
     * DR – Disconnect request code: 1000 0000.
     */
    public final static byte CODE = (byte) 0x80;


    private int srcRef;
    private int dstRef;
    private int reason;

    protected void decode(InputStream input) throws IOException {

        int length = input.read();

        byte dr = (byte) input.read();
        if (dr != CODE)
            throw new IllegalArgumentException(String.format("Expected X224DisconnectRequest-Code 0x%02X, got 0x%02X", CODE, dr));

        this.dstRef = input.read() << 8 | input.read();
        this.srcRef = input.read();
        this.reason = input.read();



        /* TODO better read all into a byte array
           then use a ByteArrayInputStream for further processing after this line
        */
        length -= 6; // length of X224 Header, excluding LengthIndicator

        if (length > 0) {
            throw new UnsupportedEncodingException("Packet contains variable part and/or user data, not implemented.");
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

    public int getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "X224DisconnectRequest{" +
                "srcRef=" + srcRef +
                ", dstRef=" + dstRef +
                ", reason=" + reason +
                '}';
    }
}
