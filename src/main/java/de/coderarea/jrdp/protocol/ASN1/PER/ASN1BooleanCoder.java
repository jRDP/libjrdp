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
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Sascha Biedermann
 */
class ASN1BooleanCoder extends ASN1ObjectCoder {
    private final static Logger logger = LogManager.getLogger(ASN1BooleanCoder.class);

    protected ASN1BooleanCoder(ASN1PerEncoder encoder, Boolean obj) throws IOException {
        super(encoder, obj);
    }

    protected ASN1BooleanCoder(ASN1PerDecoder decoder, AtomicReference<Boolean> obj) throws IOException {
        super(decoder, obj);
    }

    protected void encode() throws IOException {
        logger.trace("encode - value: {}", obj);
        if (obj == true)
            encoder.writeBit(1);
        else
            encoder.writeBit(0);
    }

    @Override
    protected void decode() throws IOException {

        int value = decoder.readBit();

        logger.trace("encode - value: {}", value);

        if (value == 1)
            ((AtomicReference<Boolean>) obj).set(true);
        else
            ((AtomicReference<Boolean>) obj).set(false);
    }
}
