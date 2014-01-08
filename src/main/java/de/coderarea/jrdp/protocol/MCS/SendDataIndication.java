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
import de.coderarea.jrdp.protocol.ASN1.ASN1Object;
import de.coderarea.jrdp.protocol.ASN1.ASN1OctetString;
import de.coderarea.jrdp.protocol.ASN1.ASN1Sequence;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Extensible;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Optional;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

/**
 * SendDataIndication ::= SEQUENCE
 * {
 * initiator UserId,
 * channelId ChannelId,
 * reliability BOOLEAN,
 * domainReferenceID INTEGER (0 .. 65535) OPTIONAL,
 * dataPriority DataPriority,
 * segmentation Segmentation,
 * userData OCTET STRING,
 * totalDataSize INTEGER OPTIONAL,
 * nonStandard SEQUENCE OF NonStandardParameter OPTIONAL,
 * ...
 * }
 *
 * @author Sascha Bidermann
 */
@ASN1Extensible
public class SendDataIndication extends ASN1Sequence {

    @ASN1Tag(0)
    private UserId initiator;

    @ASN1Tag(1)
    private ChannelId channelId;

    @ASN1Tag(2)
    private Boolean reliability;

    @ASN1Tag(3)
    @ASN1Optional
    private DomainReferenceId domainReferenceId;

    @ASN1Tag(4)
    private DataPriority dataPriority;

    @ASN1Tag(5)
    private Segmentation segmentation;

    @ASN1Tag(6)
    private ASN1OctetString userData;

    @ASN1Tag(7)
    @ASN1Optional
    private ASN1Integer totalDataSize;

    @ASN1Tag(8)
    @ASN1Optional
    private ASN1Object nonStandard; // not implemented

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

    public Boolean getReliability() {
        return reliability;
    }

    public void setReliability(Boolean reliability) {
        this.reliability = reliability;
    }

    public DataPriority getDataPriority() {
        return dataPriority;
    }

    public void setDataPriority(DataPriority dataPriority) {
        this.dataPriority = dataPriority;
    }
}
