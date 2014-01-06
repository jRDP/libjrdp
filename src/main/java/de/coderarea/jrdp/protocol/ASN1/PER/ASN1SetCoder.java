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

import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1InnerType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * @author Sascha Biedermann
 */
class ASN1SetCoder extends ASN1ObjectCoder<List> {
    private final static Logger logger = LogManager.getLogger(ASN1SetCoder.class);

    protected ASN1SetCoder(ASN1PerEncoder encoder, List<?> obj) throws IOException {
        super(encoder, obj);
    }

    ASN1SetCoder(ASN1PerDecoder decoder, List obj) throws IOException {
        super(decoder, obj);
    }

    protected void encode() throws IOException {
        logger.debug("encode - size: {}", obj.size());
        LengthDeterminantHelper.encode(encoder, obj.size());
        for (Object o : obj) {
            encoder._encode(o);
        }
    }

    @Override
    protected void decode() throws IOException {
        if (!type.isAnnotationPresent(ASN1InnerType.class))
            throw new IllegalArgumentException("ASN1InnerType annotation required on type " + type.getSimpleName());

        Class innerType = type.getAnnotation(ASN1InnerType.class).value();
        int length = LengthDeterminantHelper.decode(decoder);

        logger.debug("decode - type: {}, size: {}", innerType.getSimpleName(), length);

        for (int i = 0; i < length; i++) {
            obj.add(decoder._decode(innerType));
        }
    }
}
