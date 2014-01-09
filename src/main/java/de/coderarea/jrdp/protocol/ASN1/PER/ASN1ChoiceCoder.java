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

import de.coderarea.jrdp.protocol.ASN1.ASN1Choice;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Encoder/Decoder for ASN.1 CHOICE.
 * @author Sascha Biedermann
 */
class ASN1ChoiceCoder extends ASN1ObjectCoder<ASN1Choice> {
    private final static Logger logger = LogManager.getLogger(ASN1ChoiceCoder.class);

    protected ASN1ChoiceCoder(ASN1PerEncoder encoder, ASN1Choice obj) throws IOException {
        super(encoder, obj);
    }

    protected ASN1ChoiceCoder(ASN1PerDecoder decoder, ASN1Choice obj) throws IOException {
        super(decoder, obj);
    }

    @Override
    protected void encode() throws IOException {
        List<Field> fieldList = getTaggedFields();

        Object choice = null;
        int idx = -1;
        try {
            for (Field field : fieldList) {
                field.setAccessible(true);

                if (field.get(obj) != null && choice == null) {
                    choice = field.get(obj);
                    idx = field.getAnnotation(ASN1Tag.class).value();
                } else if (field.get(obj) != null && choice != null) {
                    throw new IllegalArgumentException("Only one choice can be set. All other tagged fields have to be null.");
                }
            }

        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }

        if (choice == null)
            throw new IllegalArgumentException("No choice has been made. One tagged field MUST NOT be null.");

        int upperBound = fieldList.size() - 1;

        logger.debug("encode - extensible: {}, choices: {}, chosen: {} ({})", isExtensible(), fieldList.size(), choice.getClass().getSimpleName(), idx);

        // write extension bit
        if (isExtensible())
            encoder.writeBit(0);

        // TODO: should be Constrained Integer
        ConstrainedWholeNumberHelper.encode(encoder, idx, 0, upperBound);

        encoder._encode(choice);

    }

    protected void decode() throws IOException {
        List<Field> fieldList = getTaggedFields();
        logger.debug("decode - extensible: {}, choices: {}", isExtensible(), fieldList.size());

        // read extension bit
        if (isExtensible()) {
            int extension = decoder.readBit();
            if (extension != 0)
                throw new UnsupportedEncodingException("Extension bit is set: not suported.");
        }

        int upperBound = fieldList.size() - 1;

        // TODO: should be Constrained Integer
        int idx = ConstrainedWholeNumberHelper.decode(decoder, 0, upperBound);

        Field field = fieldList.get(idx);
        field.setAccessible(true);

        logger.trace("decode - choice: {} ({})", field.getType().getSimpleName(), idx);

        try {
            field.set(obj, decoder._decode(field.getType()));
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }

    }
}
