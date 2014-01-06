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

import de.coderarea.jrdp.protocol.T123.T123Packet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is the parent class for all X.224 packages.
 *
 * @author Sascha Biedermann
 * @see <a href="http://www.itu.int/rec/T-REC-X.224-199511-I/en">X.224</a>
 */
public abstract class X224Packet {


    public static X224Packet getInstance(T123Packet tpkt) throws IOException {
        return getInstance(tpkt.getTPDU());
    }

    public static X224Packet getInstance(byte[] tpdu) throws IOException {
        int length = tpdu[0];
        X224Packet packet;

        byte code = (byte) (tpdu[1] & 0xF0);
        switch (code) {
            case X224ConnectionRequest.CODE:
                packet = new X224ConnectionRequest();
                break;
            case X224Data.CODE:
                packet = new X224Data();
                break;
            case X224DisconnectRequest.CODE:
                packet = new X224DisconnectRequest();
                break;
            default:
                throw new IllegalArgumentException(String.format("Code not implemented: 0x%02X", code));
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(tpdu);
        packet.decode(bis);
        return packet;
    }

    protected abstract void decode(InputStream input) throws IOException;

    public void encode(OutputStream os) throws IOException {
        os.write(encode());
    }

    public abstract byte[] encode() throws IOException;

    public abstract int getSrcRef();

    public abstract int getDstRef();

}
