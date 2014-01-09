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

/**
 * Represents an ASN.1 OCTET STRING.
 * @see de.coderarea.jrdp.protocol.ASN1.annotation.ASN1SizeConstraint
 *
 * @author Sascha Biedermann
 */
public class ASN1OctetString extends ASN1Object {
    byte[] value;

    public ASN1OctetString() {
    }

    public ASN1OctetString(byte[] data) {
        setValue(data);
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName());
        sb.append("{");
        sb.append("length=");
        sb.append(getValue().length);
        sb.append(", ");
        sb.append("value=");
        if (getValue() == null)
            sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < getValue().length; i++) {
                sb.append(String.format("%02X", getValue()[i]));
                if (i + 1 < getValue().length) sb.append(" ");
            }
            sb.append("]");
        }
        sb.append("}");
        return sb.toString();
    }
}
