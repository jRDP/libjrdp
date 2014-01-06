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
package de.coderarea.jrdp.protocol.ASN1;

import de.coderarea.jrdp.protocol.ASN1.PER.ASN1PerEncoder;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Sascha Biedermann
 */
public abstract class ASN1Encoder {

    public static ASN1Encoder newEncoder(ASN1EncodingRules rules, OutputStream output) {
        switch (rules) {
            case PER:
                return new ASN1PerEncoder(output);
            default:
                throw new IllegalArgumentException("Required encoder not available.");
        }
    }

    public abstract void encode(ASN1Object obj) throws IOException;

}
