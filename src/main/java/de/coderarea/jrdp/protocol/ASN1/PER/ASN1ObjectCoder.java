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

import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Extensible;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Optional;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Base class for all PerCoders
 *
 * @author Sascha Biedermann
 */
abstract class ASN1ObjectCoder<T> {
    private final static Logger logger = LogManager.getLogger(ASN1ObjectCoder.class);

    protected T obj;
    protected Class<?> type;
    protected ASN1PerEncoder encoder;
    protected ASN1PerDecoder decoder;

    protected ASN1ObjectCoder(ASN1PerEncoder encoder, T obj) throws IOException {
        this.obj = obj;
        this.type = obj.getClass();
        this.encoder = encoder;
        encode();
    }

    protected ASN1ObjectCoder(ASN1PerDecoder decoder, T obj) throws IOException {
        this.obj = obj;
        if (obj instanceof AtomicReference)
            this.type = ((AtomicReference) obj).get().getClass();
        else
            this.type = obj.getClass();
        this.decoder = decoder;
        decode();
    }

    protected void decode() throws IOException {
        throw new UnsupportedOperationException("decoding not implemented - override this method!");

    }

    protected void encode() throws IOException {
        throw new UnsupportedOperationException("encoding not implemented - override this method!");
    }

    protected final boolean isExtensible() {
        return type.isAnnotationPresent(ASN1Extensible.class);
    }

    protected final boolean isOptional(Field field) {
        return field.isAnnotationPresent(ASN1Optional.class);
    }

    private List<Field> getEnumFields() {
        if (!type.isEnum())
            return null;

        List<Field> fieldList = new ArrayList<>();
        for (Object enumConstant : type.getEnumConstants()) {
            try {
                Field field = type.getField(((Enum) enumConstant).name());
                if (field.isAnnotationPresent(ASN1Tag.class)) {
                    fieldList.add(field);
                }
            } catch (NoSuchFieldException e) {
                logger.error("tried to get enum field {}, {}", enumConstant.getClass().getCanonicalName(), e);
            }
        }

        return fieldList;
    }


    /**
     * Returns an unmodifiable list of non-static fields that are annotated with <code>@ASN1Tag</code> ordered
     * by their tag number
     *
     * @return ordered list of fields
     */
    protected final List<Field> getTaggedFields() {
        // get @ASN1Tag annotated, non-static fields
        List<Field> fieldList;

        if (type.isEnum()) {
            fieldList = getEnumFields();
        } else {
            fieldList = new ArrayList<>();
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(ASN1Tag.class)) {
                    if (Modifier.isStatic(field.getModifiers()))
                        throw new IllegalArgumentException(MessageFormat.format("Cannot encode static field: {0}", field.getName()));
                    fieldList.add(field);
                }
            }
        }
        // sort by tag
        Collections.sort(fieldList, new Comparator<Field>() {
            public int compare(Field o1, Field o2) {
                return Integer.compare(o1.getAnnotation(ASN1Tag.class).value(),
                        o2.getAnnotation(ASN1Tag.class).value());
            }
        });

        return Collections.unmodifiableList(fieldList);
    }


}
