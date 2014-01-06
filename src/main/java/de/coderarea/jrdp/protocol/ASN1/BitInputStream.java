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
package de.coderarea.jrdp.protocol.ASN1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream that allows bit-wise reading from an underlying byte-source.
 *
 * @author Sascha Biedermann
 */
public class BitInputStream extends InputStream {
    private final static Logger logger = LogManager.getLogger(BitInputStream.class);

    private InputStream stream;
    private int currentByte;
    private byte currentIdx;


    /**
     * Encapsulates an existing <code>InputStream</code>.
     *
     * @param stream stream to encapsulate
     */
    public BitInputStream(InputStream stream) {
        this.stream = stream;
        currentIdx = -1;
    }

    /**
     * Creates a new stream from an existing <code>byte[]</code> array.
     *
     * @param data existing data
     */
    public BitInputStream(byte data[]) {
        this(new ByteArrayInputStream(data));
    }


    /**
     * Reads one byte from the stream. <br/>
     * The byte read is not necessarily byte-aligned to the underlying source.
     *
     * @return byte read
     * @throws IOException
     */
    public int read() throws IOException {
        logger.trace("read byte");
        if (currentIdx == -1)
            return stream.read();

        int nextByte = stream.read();
        int res = ((currentByte << (7 - currentIdx)) | (nextByte >> (currentIdx + 1))) & 0xFF;
        currentByte = nextByte;

        return res;
    }

    /**
     * Reads one bit from the stream.
     *
     * @return bit read
     * @throws IOException
     */
    public int readBit() throws IOException {
        int bit = _readBit();
        logger.trace("readBit(): {}", bit);
        return bit;
    }

    private int _readBit() throws IOException {
        if (currentIdx == -1) {
            logger.trace("new byte");
            currentByte = stream.read();
            currentIdx = 7;
        }

        return (currentByte >> (currentIdx--)) & 0x01;
    }

    /**
     * Reads up to 32 bits from the stream.
     *
     * @param n number of bits to be read. <code>1..32</code>
     * @return bits read
     * @throws IllegalArgumentException thrown when <code>n</code> is out of bounds
     * @throws IOException
     */
    public int readBit(int n) throws IOException {
        if (n == 8)
            return read();

        if (n > 32)
            throw new IllegalArgumentException("Cannot read more then 32 bits into one int.");

        if (n < 0)
            throw new IllegalArgumentException("Cannot read negative amount of bits.");

        int res = 0;
        for (int i = 0; i < n; i++)
            res = (res << 1) | _readBit();

        if (n > 0)
            logger.trace("readBit({}): {}", n, String.format("%" + n + "s", Integer.toBinaryString(res)).replace(' ', '0'));
        return res;
    }

    /**
     * Reads and discards bits until the stream becomes byte-aligned. <br/>
     * If the stream is already byte-aligned then nothing happens - no bits are discarded.
     * At least 0 and at most 7 bits are discarded.
     *
     * @return number of bits discarded
     */
    public int skipPadding() {
        int skipped = currentIdx + 1;
        currentIdx = -1;
        logger.trace("padding skipped: {}", skipped);
        return skipped;
    }
}
