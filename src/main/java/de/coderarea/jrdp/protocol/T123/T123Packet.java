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
package de.coderarea.jrdp.protocol.T123;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is an implementation of a TPKT as defined in T.123 Section 8.
 *
 * @author Sascha Biedermann
 * @see <a href="http://www.itu.int/rec/T-REC-T.123/en">T.123 Section 8</a>
 */
public class T123Packet {
    private final static Logger logger = LogManager.getLogger(T123Packet.class);

    private final static byte VERSION_NUMBER = 0x03;
    private byte[] tpdu = null;

    public void decode(InputStream input) throws IOException {
        input.mark(4);

        byte[] header = new byte[4];

        input.read(header);

        if (header[0] != VERSION_NUMBER) {
            throw new IllegalArgumentException(String.format("Version not supported: expected 0x%02X, got 0x%02X", VERSION_NUMBER, header[0]));
        }

        int length = 0;
        length = header[3] & 0xFF;
        length |= (header[2] & 0xFF) << 8;

        logger.debug("TPKT length: {}", length);

        this.tpdu = new byte[length - header.length];
        input.read(tpdu);
    }

    public byte[] encode() {
        byte[] header = new byte[4];
        header[0] = VERSION_NUMBER;
        header[1] = 0;

        int length = header.length + getTPDU().length;
        header[2] = (byte) ((length >> 8) & 0xFF);
        header[3] = (byte) (length & 0xFF);

        return ArrayUtils.addAll(header, getTPDU());
    }

    public void encode(OutputStream os) throws IOException {
        os.write(encode());
    }

    /**
     * Returns the data of a previously decoded and unpacked TPKT packet.
     * TPDU is Transport RdpProtocol Data Unit
     *
     * @return the TPDU data of the packet
     * @see de.coderarea.jrdp.protocol.T123.T123Packet#decode(java.io.InputStream)
     */
    public byte[] getTPDU() {
        return tpdu;
    }

    public void setTPDU(byte[] tpdu) {
        this.tpdu = tpdu;
    }
}
