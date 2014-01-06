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

import de.coderarea.jrdp.protocol.ASN1.ASN1NumericString;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1AlphabetConstraint;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1SizeConstraint;

/**
 * SimpleNumericString ::= NumericString(SIZE (1..255))(FROM ("0123456789"))
 *
 * @author Sascha Biedermann
 */
@ASN1SizeConstraint(lb = 1, ub = 255)
@ASN1AlphabetConstraint("0123456789")
public class SimpleNumericString extends ASN1NumericString {
    public SimpleNumericString() {
    }

    public SimpleNumericString(String value) {
        super(value);
    }
}
