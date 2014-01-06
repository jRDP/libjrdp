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

import de.coderarea.jrdp.protocol.ASN1.ASN1Integer;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1ValueConstraint;

/**
 * ChannelId ::= INTEGER (0..65535)
 * DynamicChannelId ::= ChannelId (1001..65535)
 * UserId ::= DynamicChannelId
 *
 * @author Sascha Biedermann
 */
@ASN1ValueConstraint(lb = 1001, ub = 65535)
public class UserId extends ASN1Integer {

    public UserId() {
    }

    public UserId(int value) {
        super(value);
    }
}
