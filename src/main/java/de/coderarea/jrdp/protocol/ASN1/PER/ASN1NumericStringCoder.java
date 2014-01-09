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

import de.coderarea.jrdp.protocol.ASN1.ASN1NumericString;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1AlphabetConstraint;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1SizeConstraint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Encoder/Decoder for ASN.1 NUMERIC STRING.
 * @author Sascha Biedermann
 */
class ASN1NumericStringCoder extends ASN1ObjectCoder<ASN1NumericString> {
    private final static Logger logger = LogManager.getLogger(ASN1NumericStringCoder.class);

    /**
     * represents allowed characters in CANONICAL ORDER for NumericString
     */
    private final static String allowedChars = " 0123456789";
    private String alphabet;
    private int bits;
    private int lowerBound;
    private Integer upperBound;

    ASN1NumericStringCoder(ASN1PerEncoder encoder, ASN1NumericString obj) throws IOException {
        super(encoder, obj);
    }

    ASN1NumericStringCoder(ASN1PerDecoder decoder, ASN1NumericString obj) throws IOException {
        super(decoder, obj);
    }

    private void init() {
        if (type.isAnnotationPresent(ASN1AlphabetConstraint.class)) {
            this.alphabet = type.getAnnotation(ASN1AlphabetConstraint.class).value();
        } else {
            this.alphabet = allowedChars;
        }

        if (!isValidAlphabet(alphabet))
            throw new IllegalArgumentException("Supplied ASN1AlphabetConstraint is invalid for NumericString.");

        bits = getBits();

        lowerBound = 0;
        upperBound = null;
        if (type.isAnnotationPresent(ASN1SizeConstraint.class)) {
            ASN1SizeConstraint sizeConstraint = type.getAnnotation(ASN1SizeConstraint.class);
            lowerBound = sizeConstraint.lb();
            upperBound = sizeConstraint.ub();
        }

    }

    private boolean isValidAlphabet(String alphabet) {
        for (int i = 0; i < alphabet.length(); i++) {
            char c = alphabet.charAt(i);
            if (!allowedChars.contains(String.valueOf(c)))
                return false;

            int idx = alphabet.indexOf(c);
            if (alphabet.indexOf(c, idx + 1) != -1) {
                return false;
            }
        }

        return true;
    }

    private String sortAlphabet(String alphabet) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allowedChars.length(); i++) {
            char c = allowedChars.charAt(i);
            if (alphabet.contains(String.valueOf(c)))
                sb.append(c);
        }
        return sb.toString();
    }

    private int getBits() {
        int i = 1;
        while (Math.pow(2, i) < alphabet.length())
            i *= 2;
        return i;
    }

    private int getUb() throws UnsupportedEncodingException {
        // should be ISO 646, but ASCII seems ok
        byte[] bytes = new byte[0];
        bytes = alphabet.getBytes("US-ASCII");
        int max = bytes[0];

        for (byte b : bytes) {
            if (b > max)
                max = b;
        }
        return max;

    }

    private char decodeChar(int v) throws UnsupportedEncodingException {

        // a) if "ub" is less than or equal to 2^b – 1, then "v" is the value specified in above;
        // Item a) above can never apply to a constrained or unconstrained NumericString character, which always
        // encodes into four bits or less using b).
        if (bits > 4 && getUb() <= Math.pow(2, bits) - 1) {
            return allowedChars.charAt(v);

            // the characters are placed in the canonical order defined in ITU-T Rec. X.680 | ISO/IEC 8824-1, clause
            // 43. The first is assigned the value zero and the next in canonical order is assigned a value that is one
            // greater than the value assigned to the previous character in the canonical order. These are the values "v".
        } else {
            return alphabet.charAt(v);
        }

    }


    protected void encode() throws IOException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    protected void decode() throws IOException {

        init();

        logger.trace("decode - alphabet: \"{}\"\", bits: {}", alphabet, bits);

        if (upperBound != null && lowerBound == upperBound)
            throw new UnsupportedEncodingException("lowerBound == upperBound not implemented.");

        int length = LengthDeterminantHelper.decode(decoder, lowerBound, upperBound);
        decoder.skipPadding();

        logger.trace("decode - length: {}", length);

        if (upperBound * bits >= 16)
            decoder.skipPadding();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // TODO: should be non-negative binary integer
            int idx = decoder.readBit(bits);
            char c = decodeChar(idx);
            sb.append(c);
        }
        obj.setValue(sb.toString());
    }

}
