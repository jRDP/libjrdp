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

import java.io.IOException;
import java.util.Arrays;

/**
 * Represents an ASN.1 OBJECT IDENTIFIER.
 * TODO: not really implemented
 *
 * @author Sascha Biedermann
 */
public class ASN1ObjectIdentifier extends ASN1Object {
    byte[] value;

    public ASN1ObjectIdentifier() throws IOException {
    }

    public ASN1ObjectIdentifier(byte[] value) {
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ASN1ObjectIdentifier{" +
                "value=" + Arrays.toString(value) +
                '}';
    }
}
