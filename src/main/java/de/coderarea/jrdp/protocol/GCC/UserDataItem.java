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
package de.coderarea.jrdp.protocol.GCC;

import de.coderarea.jrdp.protocol.ASN1.ASN1OctetString;
import de.coderarea.jrdp.protocol.ASN1.ASN1Sequence;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Optional;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

/**
 * SEQUENCE {key    Key,
 * value  OCTET STRING OPTIONAL}
 *
 * @author Sascha Biedermann
 */
public class UserDataItem extends ASN1Sequence {
    @ASN1Tag(0)
    private Key key;

    @ASN1Tag(1)
    @ASN1Optional
    private ASN1OctetString value;

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public ASN1OctetString getValue() {
        return value;
    }

    public void setValue(ASN1OctetString value) {
        this.value = value;
    }
}
