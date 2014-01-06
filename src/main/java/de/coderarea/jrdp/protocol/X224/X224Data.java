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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.Arrays;

/**
 * Created by uidv6498 on 13.12.13.
 *
 * @author Sascha Biedermann
 */
public class X224Data extends X224Packet {
    /**
     * DT - data code: 1111.
     * Bits 8 to 5 of octet 2.
     */
    public final static byte CODE = (byte) 0xF0;

    private byte[] data;

    protected void decode(InputStream input) throws IOException {
        int length = input.read();

        if (length != 2)
            throw new ProtocolException(String.format("Exepected length of %d, got %d", 2, length));

        byte dt_roa = (byte) input.read();

        byte dt = (byte) (dt_roa & 0xF0);
        if (dt != CODE)
            throw new ProtocolException(String.format("Expected X224Data-Code 0x%02X, got 0x%02X", CODE, dt));

        int tpduNr_eot = input.read();

        if (tpduNr_eot != 0x80)
            throw new ProtocolException(String.format("Unknown tpdu/eot: 0x%02X", tpduNr_eot));

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int b;
        while ((b = input.read()) != -1) {
            os.write(b);
        }
        this.data = os.toByteArray();
    }

    public byte[] getData() {
        return data;
    }

    public byte[] encode() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // length indicator - always 2 for Data TPDUs
        bos.write(2);

        // DT = 1111 and, 000 ROA = 1
        bos.write(0xF0);

        // tpduNr_eot
        bos.write(0x80);

        /**
         * TODO: The length of this field is limited to the negotiated TPDU size for this transport connection minus 3 octets in
         * classes 0 and 1, and minus 5 octets (normal header format) or 8 octets (extended header format) in the other classes. The variable part,
         * if present, may further reduce the size of the user data field.
         */
        bos.write(getData());

        return bos.toByteArray();
    }

    public int getSrcRef() {
        throw new UnsupportedOperationException("srcRef not present in DATA TPDU");
    }

    public int getDstRef() {
        throw new UnsupportedOperationException("dstRef not present in DATA TPDU");
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "X224Data{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
