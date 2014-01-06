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
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Optional;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

/**
 * ChannelJoinConfirm ::= [APPLICATION 15] IMPLICIT SEQUENCE
 * {
 * result Result,
 * initiator UserId,
 * requested ChannelId,
 * channelId ChannelId OPTIONAL
 * }
 *
 * @author Sascha Biedermann
 */
public class ChannelJoinConfirm extends ASN1Sequence {

    @ASN1Tag(0)
    private Result result;

    @ASN1Tag(1)
    private UserId initiator;

    @ASN1Tag(2)
    private ChannelId requested;

    @ASN1Tag(3)
    @ASN1Optional
    private ChannelId channelId;


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public UserId getInitiator() {
        return initiator;
    }

    public void setInitiator(UserId initiator) {
        this.initiator = initiator;
    }

    public ChannelId getRequested() {
        return requested;
    }

    public void setRequested(ChannelId requested) {
        this.requested = requested;
    }

    public ChannelId getChannelId() {
        return channelId;
    }

    public void setChannelId(ChannelId channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "ChannelJoinConfirm{" +
                "result=" + result +
                ", initiator=" + initiator +
                ", requested=" + requested +
                ", channelId=" + channelId +
                '}';
    }
}
