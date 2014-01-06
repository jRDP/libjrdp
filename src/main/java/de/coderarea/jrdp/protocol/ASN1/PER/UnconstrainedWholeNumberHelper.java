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
 * 11.8 Encoding of an unconstrained whole number
 *
 * @author Sascha Biedermann
 */
class UnconstrainedWholeNumberHelper {
    private final static Logger logger = LogManager.getLogger(UnconstrainedWholeNumberHelper.class);

    public static void encode(ASN1PerEncoder encoder, int value) throws IOException {

        /**
         * A minimum octet 2's-complement-binary-integer encoding of the whole number has a field-width that is a
         * multiple of eight bits and also satisfies the condition that the leading nine bits of the field shall
         * not all be zero and shall not all be ones.
         *
         * supports max 4 bytes = Java Integer, can be enhanced as needed, (Long, BigInteger...)
         *
         * cuts of leading 0x00 / 0xFF bytes, to determine length
         */
        int idx = 3;
        byte leadingByte = (byte) ((value >> (8 * idx)) & 0xFF);
        if (leadingByte == 0xFF || leadingByte == 0x00) {
            idx--;
            while (idx > 0) {
                byte buffer = (byte) ((value >> (8 * idx)) & 0xFF);
                if (buffer != leadingByte)
                    break;
                idx--;
            }
        }
        int len = idx + 1;

        logger.trace("encode - bytes: {}, value: {}", len, value);
        LengthDeterminantHelper.encode(encoder, len);
        for (int i = idx; i >= 0; i--) {
            encoder.write((value >> (8 * i)) & 0xFF);
        }
    }

    public static int decode(ASN1PerDecoder decoder) throws IOException {
        int length = LengthDeterminantHelper.decode(decoder);
        logger.trace("encode - bytes: {}", length);
        int value = 0;
        for (int i = length - 1; i >= 0; i--) {
            value |= (decoder.read() & 0xFF) << (8 * i);
        }

        return value;
    }
}
