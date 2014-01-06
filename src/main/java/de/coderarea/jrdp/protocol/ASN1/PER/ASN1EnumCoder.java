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

import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Sascha Biedermann
 */
class ASN1EnumCoder extends ASN1ObjectCoder {
    private final static Logger logger = LogManager.getLogger(ASN1EnumCoder.class);

    protected ASN1EnumCoder(ASN1PerEncoder encoder, Enum obj) throws IOException {
        super(encoder, obj);
    }

    protected ASN1EnumCoder(ASN1PerDecoder decoder, AtomicReference<Enum> obj) throws IOException {
        super(decoder, obj);
    }

    protected void encode() throws IOException {
        List<Field> fieldList = getTaggedFields();

        String name = ((Enum) obj).name();

        int tag = -1;
        for (Field field : fieldList) {
            if (field.getName().equals(name)) {
                tag = field.getAnnotation(ASN1Tag.class).value();
                break;
            }
        }

        if (tag == -1) {
            throw new IllegalArgumentException("Unknown enum constant: " + name);
        }

        logger.trace("encoding - options: {}, chosen: {} ({})", fieldList.size(), name, tag);
        int upperBound = fieldList.size() - 1;


        // write extension bit
        if (isExtensible())
            encoder.writeBit(0);

        // TODO: should be Constrained Integer
        ConstrainedWholeNumberHelper.encode(encoder, tag, 0, upperBound);
    }

    @Override
    protected void decode() throws IOException {
        List<Field> fieldList = getTaggedFields();

        logger.debug(fieldList);

        logger.trace("decoding - options: {}", fieldList.size());
        int upperBound = fieldList.size() - 1;

        // read extension bit
        if (isExtensible()) {
            int extension = decoder.readBit();
            if (extension != 0)
                throw new UnsupportedEncodingException("Extension bit is set: not suported.");
        }

        int idx = ConstrainedWholeNumberHelper.decode(decoder, 0, upperBound);

        try {
            Enum enumConstant = (Enum) fieldList.get(idx).get(null);
            ((AtomicReference<Enum>) obj).set(enumConstant);
            logger.trace("decoding - chosen: {} ({})", enumConstant.name(), idx);
        } catch (IllegalAccessException e) {
            ((AtomicReference<Enum>) obj).set(null);
        }

    }
}
