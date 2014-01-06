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

import de.coderarea.jrdp.protocol.ASN1.ASN1Sequence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author Sascha Biedermann
 */
class ASN1SequenceCoder extends ASN1ObjectCoder<ASN1Sequence> {
    private final static Logger logger = LogManager.getLogger(ASN1SequenceCoder.class);


    ASN1SequenceCoder(ASN1PerEncoder encoder, ASN1Sequence obj) throws IOException {
        super(encoder, obj);
    }

    ASN1SequenceCoder(ASN1PerDecoder decoder, ASN1Sequence obj) throws IOException {
        super(decoder, obj);
    }

    @Override
    protected void decode() throws IOException {
        List<Field> fieldList = getTaggedFields();

        int optionalFieldCount = 0;
        for (Field field : fieldList) {
            if (isOptional(field))
                optionalFieldCount++;
        }

        logger.debug("decode - extensible: {}, fields: {}, optional: {}", isExtensible(), fieldList.size(), optionalFieldCount);

        // read extension bit
        if (isExtensible()) {
            int extension = decoder.readBit();
            if (extension != 0)
                throw new UnsupportedEncodingException("Extension bit is set: not suported.");
        }

        // read preamble
        int preamble = decoder.readBit(optionalFieldCount);

        int mask = 1 << (optionalFieldCount);

        // read fields
        for (Field field : fieldList) {
            field.setAccessible(true);
            if (isOptional(field)) mask >>= 1;

            if (isOptional(field) && ((preamble & mask) == 0)) {
                logger.trace("decode - skipping field {} - absent", field.getName());
                continue;
            }

            logger.trace("decode - field: {} {}", field.getType().getSimpleName(), field.getName());

            try {
                field.set(obj, decoder._decode(field.getType()));
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }

    }

    protected void encode() throws IOException {

        List<Field> fieldList = getTaggedFields();

        // build sequence preamble
        // and check for absent mandatory fields
        int preamble = 0;
        int optionalFieldCount = 0;
        try {
            for (Field field : fieldList) {
                field.setAccessible(true);

                if (isOptional(field)) {
                    optionalFieldCount++;
                    preamble <<= 1;
                    if (field.get(obj) != null) {
                        preamble |= 0x01;
                    }
                } else {
                    if (field.get(obj) == null)
                        throw new IllegalArgumentException(MessageFormat.format("Non-optional field is absent (null) when expected to be present: {0}", field.getName()));
                }
            }
        } catch (IllegalAccessException e) {
            logger.error(e);
        }

        logger.debug("encoding {} - extensible: {}, preamble: 0b{}, fields: {}",
                type.getSimpleName(),
                isExtensible(),
                optionalFieldCount > 0 ? String.format("%" + optionalFieldCount + "s", Integer.toBinaryString(preamble)).replace(' ', '0') : "--",
                fieldList);

        try {
            // write extension bit
            if (isExtensible())
                encoder.writeBit(0);

            // write preamble
            if (optionalFieldCount > 0)
                encoder.writeBit(preamble, optionalFieldCount);

            // write fields
            for (Field field : fieldList) {
                field.setAccessible(true);

                if (isOptional(field) && field.get(obj) == null) {
                    logger.trace("encode - skipping field {} - absent", field.getName());
                    continue;
                }

                encoder._encode(field.get(obj));
            }

        } catch (IllegalAccessException e) {
            logger.error(e);
        }
    }


}
