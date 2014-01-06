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

import de.coderarea.jrdp.protocol.ASN1.ASN1ObjectIdentifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author Sascha Biedermann
 */
class ASN1ObjectIdentifierCoder extends ASN1ObjectCoder<ASN1ObjectIdentifier> {
    private final static Logger logger = LogManager.getLogger(ASN1ObjectIdentifierCoder.class);

    protected ASN1ObjectIdentifierCoder(ASN1PerEncoder encoder, ASN1ObjectIdentifier obj) throws IOException {
        super(encoder, obj);
    }

    ASN1ObjectIdentifierCoder(ASN1PerDecoder decoder, ASN1ObjectIdentifier obj) throws IOException {
        super(decoder, obj);
    }

    @Override
    protected void encode() throws IOException {
        // TODO: dirty hack! - implement real object identifier
        encoder.writePadding();
        LengthDeterminantHelper.encode(encoder, obj.getValue().length);
        encoder.writePadding();
        encoder.write(obj.getValue());
    }


    @Override
    protected void decode() throws IOException {
        // TODO: dirty hack!
        decoder.skipPadding();
        int length = LengthDeterminantHelper.decode(decoder);

        logger.trace("decode - length: {}", length);

        decoder.skipPadding();

        byte[] buffer = new byte[length];
        decoder.read(buffer);

        obj.setValue(buffer);
    }
}
