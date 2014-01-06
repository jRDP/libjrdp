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

import de.coderarea.jrdp.protocol.ASN1.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * PER Decoder - Packed Encoding Rules.
 *
 * @author Sascha Biedermann
 * @see <a href="http://www.itu.int/rec/T-REC-X.691-200811-I/en">ASN.1 encoding rules: Specification of Packed Encoding Rules (PER)</a>
 */
public class ASN1PerDecoder<T extends ASN1Object> extends ASN1Decoder<T> {
    private final static Logger logger = LogManager.getLogger(ASN1PerDecoder.class);


    private BitInputStream input;

    public ASN1PerDecoder(InputStream input) {
        this.input = new BitInputStream(input);
    }


    public T decode(Class<T> type) throws IOException {
        return (T) doDecode(type);
    }

    protected Object _decode(Class<?> type) throws IOException {
        return doDecode(type);
    }

    private Object doDecode(Class<?> type) throws IOException {

        Object obj = null;
        try {
            obj = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e);
        }

        /** ASN1Object - Tree **/
        if (ASN1Object.class.isAssignableFrom(type)) {
            if (obj == null)
                throw new NullPointerException("Decoding type " + type.getName() + " not implemented.");

            if (ASN1Sequence.class.isAssignableFrom(type)) {
                new ASN1SequenceCoder(this, (ASN1Sequence) obj);
            } else if (ASN1Choice.class.isAssignableFrom(type)) {
                new ASN1ChoiceCoder(this, (ASN1Choice) obj);
            } else if (ASN1Integer.class.isAssignableFrom(type)) {
                new ASN1IntegerCoder(this, (ASN1Integer) obj);
            } else if (ASN1OctetString.class.isAssignableFrom(type)) {
                new ASN1OctetStringCoder(this, (ASN1OctetString) obj);
            } else if (ASN1ObjectIdentifier.class.isAssignableFrom(type)) {
                new ASN1ObjectIdentifierCoder(this, (ASN1ObjectIdentifier) obj);
            } else if (ASN1NumericString.class.isAssignableFrom(type)) {
                new ASN1NumericStringCoder(this, (ASN1NumericString) obj);
            } else if (ASN1Null.class.isAssignableFrom(type)) {
                // do nothing for null
            } else {
                throw new UnsupportedOperationException("Decoding type " + type.getName() + " not implemented.");
            }
        } else {

            if (Enum.class.isAssignableFrom(type)) {
                // use AtomicReference to pass enum by reference
                AtomicReference<Enum> atomicReference = new AtomicReference<Enum>((Enum) type.getEnumConstants()[0]);
                new ASN1EnumCoder(this, atomicReference);
                obj = atomicReference.get();
            } else if (Boolean.class.isAssignableFrom(type)) {
                // use AtomicReference to pass enum by reference
                AtomicReference<Boolean> atomicReference = new AtomicReference<Boolean>(true);
                new ASN1BooleanCoder(this, atomicReference);
                obj = atomicReference.get();
            } else if (List.class.isAssignableFrom(type)) {
                new ASN1SetCoder(this, (List) obj);
            } else {
                throw new UnsupportedOperationException("Decoding type " + type.getName() + " not implemented.");
            }
        }

        return obj;
    }

    /**
     * @see de.coderarea.jrdp.protocol.ASN1.BitInputStream#skipPadding()
     */
    protected int skipPadding() throws IOException {
        return this.input.skipPadding();
    }

    /**
     * @see de.coderarea.jrdp.protocol.ASN1.BitInputStream#readBit()
     */
    protected int readBit() throws IOException {
        return this.input.readBit();
    }

    /**
     * @see de.coderarea.jrdp.protocol.ASN1.BitInputStream#readBit(int)
     */
    protected int readBit(int n) throws IOException {
        return this.input.readBit(n);
    }

    /**
     * @see de.coderarea.jrdp.protocol.ASN1.BitInputStream#read()
     */
    protected int read() throws IOException {
        return this.input.read();
    }

    /**
     * @see de.coderarea.jrdp.protocol.ASN1.BitInputStream#read(byte[])
     */
    protected int read(byte[] buffer) throws IOException {
        return this.input.read(buffer);
    }

}
