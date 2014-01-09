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

import de.coderarea.jrdp.protocol.ASN1.ASN1OctetString;
import de.coderarea.jrdp.protocol.ASN1.ASN1Sequence;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

/**
 SendDataIndication ::= [APPLICATION 26] IMPLICIT SEQUENCE {
 initiator     UserId,
 channelId     ChannelId,
 dataPriority  DataPriority,
 segmentation  Segmentation,
 userData      OCTET STRING
 }
 *
 * @author Sascha Bidermann
 */
public class SendDataIndication extends ASN1Sequence {

    @ASN1Tag(0)
    private UserId initiator;

    @ASN1Tag(1)
    private ChannelId channelId;

    @ASN1Tag(4)
    private DataPriority dataPriority;

    @ASN1Tag(5)
    private Segmentation segmentation;

    @ASN1Tag(6)
    private ASN1OctetString userData;

    public UserId getInitiator() {
        return initiator;
    }

    public void setInitiator(UserId initiator) {
        this.initiator = initiator;
    }

    public ChannelId getChannelId() {
        return channelId;
    }

    public void setChannelId(ChannelId channelId) {
        this.channelId = channelId;
    }

    public ASN1OctetString getUserData() {
        return userData;
    }

    public void setUserData(ASN1OctetString userData) {
        this.userData = userData;
    }

    public Segmentation getSegmentation() {
        return segmentation;
    }

    public void setSegmentation(Segmentation segmentation) {
        this.segmentation = segmentation;
    }

    public DataPriority getDataPriority() {
        return dataPriority;
    }

    public void setDataPriority(DataPriority dataPriority) {
        this.dataPriority = dataPriority;
    }
}
