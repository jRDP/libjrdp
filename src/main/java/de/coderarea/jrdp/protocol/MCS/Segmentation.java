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
package de.coderarea.jrdp.protocol.MCS;

import de.coderarea.jrdp.protocol.ASN1.ASN1Sequence;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1SizeConstraint;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

/**
 * Segmentation ::= BIT STRING
 * {
 * begin (0),
 * end (1)
 * } (SIZE (2))
 *
 * @author Sascha Biedermann
 */
@ASN1SizeConstraint(lb = 2, ub = 2)
public class Segmentation extends ASN1Sequence {

    @ASN1Tag(0)
    private Boolean begin;

    @ASN1Tag(1)
    private Boolean end;

    public Segmentation() {
    }

    public Segmentation(Boolean begin, Boolean end) {
        this.begin = begin;
        this.end = end;
    }


    public Boolean getBegin() {
        return begin;
    }

    public void setBegin(Boolean begin) {
        this.begin = begin;
    }

    public Boolean getEnd() {
        return end;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Segmentation{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }
}
