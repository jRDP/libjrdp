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

import de.coderarea.jrdp.protocol.ASN1.ASN1OctetString;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1SizeConstraint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Encoder/Decoder for ASN.1 OCTET STRING.
 * @author Sascha Biedermann
 */
class ASN1OctetStringCoder extends ASN1ObjectCoder<ASN1OctetString> {
    private final static Logger logger = LogManager.getLogger(ASN1OctetStringCoder.class);

    protected ASN1OctetStringCoder(ASN1PerEncoder encoder, ASN1OctetString obj) throws IOException {
        super(encoder, obj);
    }

    ASN1OctetStringCoder(ASN1PerDecoder decoder, ASN1OctetString obj) throws IOException {
        super(decoder, obj);
    }

    protected void encode() throws IOException {
        int lb = 0;
        Integer ub = null;

        if (type.isAnnotationPresent(ASN1SizeConstraint.class)) {
            ASN1SizeConstraint constraint = type.getAnnotation(ASN1SizeConstraint.class);
            lb = constraint.lb();
            ub = constraint.ub();
        }


        int length = obj.getValue().length;
        logger.debug("encode - length: {}, lb: {}, ub: {}", length, lb, ub);

        if (length < lb || (ub != null && length > ub))
            throw new IllegalArgumentException("Length does not meet size constraint.");

        if (ub == null || lb != ub) {
            LengthDeterminantHelper.encode(encoder, length, lb, ub);
        }

        encoder.writePadding();
        encoder.write(obj.getValue());

    }

    protected void decode() throws IOException {
        int lb = 0;
        Integer ub = null;

        if (type.isAnnotationPresent(ASN1SizeConstraint.class)) {
            ASN1SizeConstraint constraint = type.getAnnotation(ASN1SizeConstraint.class);
            lb = constraint.lb();
            ub = constraint.ub();
        }

        int length;
        if (ub != null && lb == ub) {
            length = lb;
        } else {
            length = LengthDeterminantHelper.decode(decoder, lb, ub);
        }

        logger.debug("decode - length: {}, lb: {}, ub: {}", length, lb, ub);

        decoder.skipPadding();
        byte[] buffer = new byte[length];
        decoder.read(buffer);
        obj.setValue(buffer);
    }
}
