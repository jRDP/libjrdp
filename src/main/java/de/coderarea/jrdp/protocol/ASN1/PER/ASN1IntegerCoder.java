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
package de.coderarea.jrdp.protocol.ASN1.PER;

import de.coderarea.jrdp.protocol.ASN1.ASN1Integer;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1ValueConstraint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author Sascha Biedermann
 */
class ASN1IntegerCoder extends ASN1ObjectCoder<ASN1Integer> {
    private final static Logger logger = LogManager.getLogger(ASN1IntegerCoder.class);

    ASN1IntegerCoder(ASN1PerEncoder encoder, ASN1Integer obj) throws IOException {
        super(encoder, obj);
    }

    ASN1IntegerCoder(ASN1PerDecoder decoder, ASN1Integer obj) throws IOException {
        super(decoder, obj);
    }

    protected void encode() throws IOException {

        // TODO: add support for SemiConstrained integer
        if (type.isAnnotationPresent(ASN1ValueConstraint.class)) {
            ASN1ValueConstraint constraint = type.getAnnotation(ASN1ValueConstraint.class);
            logger.trace("encoding constrained integer - lb: {}, ub: {}, value: {}", constraint.lb(), constraint.ub(), obj.getValue());
            ConstrainedWholeNumberHelper.encode(encoder, obj.getValue(), constraint.lb(), constraint.ub());
        } else {
            logger.trace("encoding unconstrained integer - value: {}", obj.getValue());
            UnconstrainedWholeNumberHelper.encode(encoder, obj.getValue());
        }
    }

    @Override
    protected void decode() throws IOException {
        if (type.isAnnotationPresent(ASN1ValueConstraint.class)) {
            ASN1ValueConstraint constraint = type.getAnnotation(ASN1ValueConstraint.class);
            // TODO: need semi-constraint numbers.
            Integer lb = constraint.lb();//== Integer.MIN_VALUE ? null : constraint.lb();
            Integer ub = constraint.ub();//== Integer.MAX_VALUE ? null : constraint.ub();
            logger.trace("decoding constrained integer - lb: {}, ub: {}, value: {}", lb, ub, obj.getValue());
            obj.setValue(ConstrainedWholeNumberHelper.decode(decoder, lb, ub));
        } else {
            logger.trace("decoding unconstrained integer");
            obj.setValue(UnconstrainedWholeNumberHelper.decode(decoder));
        }
    }
}
