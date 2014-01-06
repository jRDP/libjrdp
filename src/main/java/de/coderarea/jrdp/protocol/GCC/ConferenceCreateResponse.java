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

import de.coderarea.jrdp.protocol.ASN1.ASN1Integer;
import de.coderarea.jrdp.protocol.ASN1.ASN1Sequence;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Extensible;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Optional;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

/**
 * ConferenceCreateResponse. <br/>
 * <pre>
 * ConferenceCreateResponse ::=
 * SEQUENCE { -- MCS-Connect-Provider response user data
 * nodeID    UserID, -- Node ID of the sending node
 * tag       INTEGER,
 * result
 * ENUMERATED {success(0), userRejected(1), resourcesNotAvailable(2),
 * rejectedForSymmetryBreaking(3),
 * lockedConferenceNotSupported(4), ...
 * },
 * userData  UserData OPTIONAL,
 * ...
 * }
 * </pre>
 */
@ASN1Extensible
public class ConferenceCreateResponse extends ASN1Sequence {
    @ASN1Tag(0)
    private UserID nodeID;

    @ASN1Tag(1)
    private ASN1Integer tag;

    @ASN1Tag(2)
    private Result result;

    @ASN1Tag(3)
    @ASN1Optional
    private UserData userData;

    public ConferenceCreateResponse() {
    }


    public UserID getNodeID() {
        return nodeID;
    }

    public void setNodeID(UserID nodeID) {
        this.nodeID = nodeID;
    }

    public ASN1Integer getTag() {
        return tag;
    }

    public void setTag(ASN1Integer tag) {
        this.tag = tag;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
