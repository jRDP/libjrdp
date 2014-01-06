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

import java.io.IOException;
import java.io.OutputStream;

/**
 * OutputStream that allows bit-wise writing to an underlying byte-sink.
 *
 * @author Sascha Biedermann
 */
public class BitOutputStream extends OutputStream {
    private final static Logger logger = LogManager.getLogger(BitOutputStream.class);

    private OutputStream stream;
    private int currentByte;
    private byte currentIdx;


    /**
     * Encapsulates an existing <code>OutputStream</code>.
     *
     * @param stream stream to encapsulate
     */
    public BitOutputStream(OutputStream stream) {
        this.stream = stream;
        currentIdx = -1;
    }

    /**
     * Writes one byte to the stream. <br/>
     * The write is not necessarily byte-aligned to the stream.
     *
     * @param b byte to be written
     * @throws IOException
     */
    public void write(int b) throws IOException {
        logger.trace("write byte");
        if (currentIdx == -1) {
            stream.write(b);
        } else {
            int next = (currentByte | (b >> (7 - currentIdx))) & 0xFF;
            stream.write(next);
            currentByte = b << (currentIdx + 1);
        }
    }

    /**
     * Writes a single bit to the stream.
     *
     * @param bit bit to be written
     * @throws IOException
     */
    public void writeBit(int bit) throws IOException {
        if (currentIdx == -1) {
            currentByte = 0;
            currentIdx = 7;
        }

        currentByte = currentByte | ((bit & 0x01) << currentIdx--);

        if (currentIdx == -1) {
            stream.write(currentByte);
        }
    }

    /**
     * Writes <code>n</code> bits to the stream.
     *
     * @param bits bits to be written (right-aligned)
     * @param n    number of bits to be written
     * @throws IOException
     */
    public void writeBit(int bits, int n) throws IOException {
        if (n == 8) {
            write(bits & 0xFF);
        } else if (n > 32) {
            throw new IllegalArgumentException("The bits parameter cannot hold mir then 32 bits.");
        } else if (n <= 0) {
            throw new IllegalArgumentException("Cannot write less than one bit.");
        } else {
            for (int i = n - 1; i >= 0; i--) {
                writeBit((bits >> i) & 0x01);
            }
        }
    }

    /**
     * Writes zero-padding to the stream. <br/>
     * Bits of zeros are written to the stream until it becomes byte aligned. Nothing happens
     * when the stream is already byte-aligned - no bits are written.
     * At least 0 and at most 7 bits are written.
     *
     * @return number of padding bits written
     */
    public int writePadding() throws IOException {
        int count = 0;
        while (currentIdx != -1) {
            count++;
            writeBit(0);
        }
        logger.debug("write Padding: {} bits", count);
        return count;
    }
}
