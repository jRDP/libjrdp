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

import de.coderarea.jrdp.protocol.ASN1.ASN1Object;
import de.coderarea.jrdp.protocol.ASN1.ASN1Sequence;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Extensible;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Optional;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

/**
 * ConferenceName ::= SEQUENCE {
 * numeric          SimpleNumericString,
 * text             SimpleTextString OPTIONAL,
 * ...,
 * unicode-text     TextString OPTIONAL
 * }
 *
 * @author Sascha Biedermann
 */
@ASN1Extensible
public class ConferenceName extends ASN1Sequence {

    @ASN1Tag(0)
    private SimpleNumericString numeric;

    @ASN1Tag(1)
    @ASN1Optional
    private ASN1Object text; // not implemented


    public ConferenceName() {
    }

    public SimpleNumericString getNumeric() {
        return numeric;
    }

    public void setNumeric(SimpleNumericString numeric) {
        this.numeric = numeric;
    }

}
