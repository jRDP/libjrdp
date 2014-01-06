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

import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

/**
 * Result ::= ENUMERATED -- in Connect, response, confirm
 * {
 * rt-successful (0),
 * rt-domain-merging (1),
 * rt-domain-not-hierarchical (2),
 * rt-no-such-channel (3),
 * rt-no-such-domain (4),
 * rt-no-such-user (5),
 * rt-not-admitted (6),
 * rt-other-user-id (7),
 * rt-parameters-unacceptable (8),
 * rt-token-not-available (9),
 * rt-token-not-possessed (10),
 * rt-too-many-channels (11),
 * rt-too-many-tokens (12),
 * rt-too-many-users (13),
 * rt-unspecified-failure (14),
 * rt-user-rejected (15)
 * }
 *
 * @author Sascha Biedermann
 */
public enum Result {

    @ASN1Tag(0)
    rt_successful,

    @ASN1Tag(1)
    rt_domain_merging,

    @ASN1Tag(2)
    rt_domain_not_hierarchical,

    @ASN1Tag(3)
    rt_no_such_channel,

    @ASN1Tag(4)
    rt_no_such_domain,

    @ASN1Tag(5)
    rt_no_such_user,

    @ASN1Tag(6)
    rt_not_admitted,

    @ASN1Tag(7)
    rt_other_user_id,

    @ASN1Tag(8)
    rt_parameters_unacceptable,

    @ASN1Tag(9)
    rt_token_not_available,

    @ASN1Tag(10)
    rt_token_not_possessed,

    @ASN1Tag(11)
    rt_too_many_channels,

    @ASN1Tag(12)
    rt_too_many_tokens,

    @ASN1Tag(13)
    rt_too_many_users,

    @ASN1Tag(14)
    rt_unspecified_failure,

    @ASN1Tag(15)
    rt_user_rejected

}
