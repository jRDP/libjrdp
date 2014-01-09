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
package de.coderarea.jrdp.protocol.ASN1.annotation;

import java.lang.annotation.*;

/**
 * Applies a size constraint to an ASN.1 Integer.
 * @author Sascha Biedermann
 */
@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ASN1ValueConstraint {

    /**
     * Lower Bound
     * @return lower bound
     */
    int lb() default Integer.MIN_VALUE;

    /**
     * Upper Bound
     * @return upper bound
     */
    int ub() default Integer.MAX_VALUE;
}
