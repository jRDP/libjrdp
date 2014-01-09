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
 * Represents an ASN.1 SEQUENCE.
 *
 * @author Sascha Biedermann
 * @see de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag
 * @see de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Optional
 * @see de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Extensible
 */
public abstract class ASN1Sequence extends ASN1Object {
    public ASN1Sequence() {
    }
}
