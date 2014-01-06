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
package de.coderarea.jrdp.protocol.ASN1.PER;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * 11.5 Encoding of a constrained whole number
 *
 * @author Sascha Biedermann
 */
class ConstrainedWholeNumberHelper {
    private final static Logger logger = LogManager.getLogger(ConstrainedWholeNumberHelper.class);

    private static int getBitFieldSize(int range) {
        if (range > 255)
            throw new IllegalArgumentException("Range too large: bit-field case only for range <= 255!");

        if (range < 3) return 1;
        else if (range < 5) return 2;
        else if (range < 9) return 3;
        else if (range < 17) return 4;
        else if (range < 33) return 5;
        else if (range < 65) return 6;
        else if (range < 129) return 7;
        else return 8;
    }

    public static void encode(ASN1PerEncoder encoder, int value, int lowerBound, int upperBound) throws IOException {
        if (value < lowerBound)
            throw new IllegalArgumentException("Value is less than lower bound.");
        if (value > upperBound)
            throw new IllegalArgumentException("Value is greater than upper bound.");

        int range = upperBound - lowerBound + 1;
        logger.trace("encode - value: {}, lowerBound: {}, upperBound: {}, range: {}", value, lowerBound, upperBound, range);

        if (range == 1) {
            logger.trace("encode - range is 1, nothing gets written");
            return;
        }

        // a) "range" is less than or equal to 255 (the bit-field case);
        if (range <= 255) {
            int bits = getBitFieldSize(range);


            int raw = value - lowerBound;
            logger.trace("encode - bit-field case, bits: {}, raw: 0b{}, value: {}", bits, String.format("%" + bits + "s", Integer.toBinaryString(raw)).replace(' ', '0'), value);
            encoder.writeBit(raw, bits);

            // b) "range" is exactly 256 (the one-octet case);
        } else if (range == 256) {

            int raw = value - lowerBound;
            logger.trace("encode - one-octet case, raw: {}, value: {}", raw, value);
            encoder.writePadding();
            encoder.write(raw & 0xFF);

            // c) "range" is greater than 256 and less than or equal to 64K (the two-octet case);
        } else if (range <= 65536) {
            int raw = value - lowerBound;

            logger.trace("encode - two-octet case, raw: {}, value: {}", raw, value);
            encoder.writePadding();
            int a = (raw >> 8) & 0xFF;
            int b = raw & 0xFF;
            encoder.write(a);
            encoder.write(b);

        } else {
            throw new UnsupportedOperationException("ConstrainedWholeNumber with range >64K is not supported.");
        }
    }

    public static int decode(ASN1PerDecoder decoder, int lowerBound, int upperBound) throws IOException {
        int range = upperBound - lowerBound + 1;
        logger.trace("decode - lowerBound: {}, upperBound: {}", lowerBound, upperBound);

        if (range == 1)
            return lowerBound;

        // a) "range" is less than or equal to 255 (the bit-field case);
        if (range <= 255) {
            int bits = getBitFieldSize(range);
            int val = decoder.readBit(bits);
            logger.trace("decode - bits: {}, raw: 0b{}, value: {}", bits, String.format("%" + bits + "s", Integer.toBinaryString(val)).replace(' ', '0'), val + lowerBound);

            return val + lowerBound;

            // b) "range" is exactly 256 (the one-octet case);
        } else if (range == 256) {
            decoder.skipPadding();
            int raw = decoder.read();
            int value = raw + lowerBound;
            logger.trace("decode - one-octet case, raw: {}, value: {}", raw, value);
            return value;

            // c) "range" is greater than 256 and less than or equal to 64K (the two-octet case);
        } else if (range <= 65536) {
            decoder.skipPadding();
            int a = decoder.read();
            int b = decoder.read();
            int raw = ((a << 8) | (b & 0xFF)) & 0xFFFF;
            int value = raw + lowerBound;
            logger.trace("encode - two-octet case, raw: {}, value: {}", raw, value);
            return value;
        } else {
            throw new UnsupportedOperationException("ConstrainedWholeNumber with range >64K is not supported.");
        }
    }
}
