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

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 11.9 General rules for encoding a length determinant
 *
 * @author Sascha Biedermann
 */
class LengthDeterminantHelper {

    public static void encode(ASN1PerEncoder encoder, int value) throws IOException {
        encode(encoder, value, 0);
    }

    public static void encode(ASN1PerEncoder encoder, int value, int lowerBound) throws IOException {
        encode(encoder, value, lowerBound, null);
    }

    public static void encode(ASN1PerEncoder encoder, int value, int lowerBound, Integer upperBound) throws IOException {

        // a) a normally small length with a lower bound "lb" equal to one;
        if (lowerBound == 1 && upperBound == null) {
            throw new UnsupportedEncodingException("encode - normally small length not implemented");
        }
        // b) a constrained whole number with a lower bound "lb" (greater than or equal to zero), and an upper bound "ub" less than 64K;
        else if (lowerBound >= 0 && upperBound != null) {
            ConstrainedWholeNumberHelper.encode(encoder, value, lowerBound, upperBound);
        }
        // unconstrained length ("n" say) is encoded into an octet-aligned bit-field in one of three ways:
        else if (lowerBound == 0 && upperBound == null) {
            encoder.writePadding();

            // a) ("n" less than 128) a single octet containing "n" with bit 8 set to zero;
            if (value < 128) {
                encoder.write(value & 0b01111111);
            }
            // b) ("n" less than 16K) two octets containing "n" with bit 8 of the first octet set to 1 and bit 7 set to zero;
            else if (value < 16384) {
                int a = ((value >> 8) & 0b00111111) | 0b10000000;
                int b = value & 0xFF;
                encoder.write(a);
                encoder.write(b);
            } else {
                throw new UnsupportedEncodingException("encode - length >16K not implemented");
            }
        } else {
            throw new UnsupportedEncodingException("encode - not implemented");
        }
    }

    public static int decode(ASN1PerDecoder decoder) throws IOException {
        return decode(decoder, 0);
    }

    public static int decode(ASN1PerDecoder decoder, int lowerBound) throws IOException {
        return decode(decoder, lowerBound, null);
    }

    public static int decode(ASN1PerDecoder decoder, int lowerBound, Integer upperBound) throws IOException {

        // a) a normally small length with a lower bound "lb" equal to one;
        if (lowerBound == 1 && upperBound == null) {
            throw new UnsupportedEncodingException("decode - normally small length not implemented");
        }
        // b) a constrained whole number with a lower bound "lb" (greater than or equal to zero), and an upper bound "ub" less than 64K;
        else if (lowerBound >= 0 && upperBound != null) {
            return ConstrainedWholeNumberHelper.decode(decoder, lowerBound, upperBound);
        }
        // unconstrained length ("n" say) is encoded into an octet-aligned bit-field in one of three ways:
        else if (lowerBound == 0 && upperBound == null) {
            decoder.skipPadding();

            int a = decoder.read();

            // a) ("n" less than 128) a single octet containing "n" with bit 8 set to zero;
            if ((a & 0b10000000) == 0)
                return a;

            // b) ("n" less than 16K) two octets containing "n" with bit 8 of the first octet set to 1 and bit 7 set to zero;
            if ((a & 0b11000000) == 0b10000000) {
                int b = decoder.read();
                return (((a & 0b00111111) << 8) | (b & 0xFF)) & 0xFFFF;
            }

            throw new UnsupportedEncodingException("decode - length >16K not implemented");

        } else {
            throw new UnsupportedEncodingException("decode - not implemented");
        }
    }
}
