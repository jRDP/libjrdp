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

import de.coderarea.jrdp.protocol.ASN1.ASN1Choice;
import de.coderarea.jrdp.protocol.ASN1.ASN1ObjectIdentifier;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

import java.io.IOException;

/**
 * Key ::= CHOICE -- Identifier of a standard or non-standard object
 * {
 * object           OBJECT IDENTIFIER,
 * h221NonStandard  H221NonStandardIdentifier
 * }
 *
 * @author Sascha Biedermann
 */
public final class Key extends ASN1Choice {

    @ASN1Tag(0)
    private ASN1ObjectIdentifier object;

    @ASN1Tag(1)
    private H221NonStandardIdentifier h221NonStandard;

    public Key() {
    }

    public Key(H221NonStandardIdentifier h221NonStandard) throws IOException {
        this.h221NonStandard = h221NonStandard;
    }

    public Key(ASN1ObjectIdentifier object) {
        this.object = object;
    }

    public ASN1ObjectIdentifier getObject() {
        return object;
    }

    public void setObject(ASN1ObjectIdentifier object) {
        this.object = object;
    }

    public H221NonStandardIdentifier getH221NonStandard() {
        return h221NonStandard;
    }

    public void setH221NonStandard(H221NonStandardIdentifier h221NonStandard) {
        this.h221NonStandard = h221NonStandard;
    }

    @Override
    public String toString() {
        return "Key{" +
                "object=" + object +
                ", h221NonStandard=" + h221NonStandard +
                '}';
    }
}
