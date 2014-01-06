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

import de.coderarea.jrdp.protocol.ASN1.ASN1Choice;
import de.coderarea.jrdp.protocol.ASN1.ASN1Object;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

/**
 * DomainMCSPDU ::= CHOICE {
 * plumbDomainIndication        PlumbDomainIndication,
 * erectDomainRequest           ErectDomainRequest,
 * mergeChannelsRequest         MergeChannelsRequest,
 * mergeChannelsConfirm         MergeChannelsConfirm,
 * purgeChannelsIndication      PurgeChannelsIndication,
 * mergeTokensRequest           MergeTokensRequest,
 * mergeTokensConfirm           MergeTokensConfirm,
 * purgeTokensIndication        PurgeTokensIndication,
 * disconnectProviderUltimatum  DisconnectProviderUltimatum,
 * rejectMCSPDUUltimatum        RejectMCSPDUUltimatum,
 * attachUserRequest            AttachUserRequest,
 * attachUserConfirm            AttachUserConfirm,
 * detachUserRequest            DetachUserRequest,
 * detachUserIndication         DetachUserIndication,
 * channelJoinRequest           ChannelJoinRequest,
 * channelJoinConfirm           ChannelJoinConfirm,
 * channelLeaveRequest          ChannelLeaveRequest,
 * channelConveneRequest        ChannelConveneRequest,
 * channelConveneConfirm        ChannelConveneConfirm,
 * channelDisbandRequest        ChannelDisbandRequest,
 * channelDisbandIndication     ChannelDisbandIndication,
 * channelAdmitRequest          ChannelAdmitRequest,
 * channelAdmitIndication       ChannelAdmitIndication,
 * channelExpelRequest          ChannelExpelRequest,
 * channelExpelIndication       ChannelExpelIndication,
 * sendDataRequest              SendDataRequest,
 * sendDataIndication           SendDataIndication,
 * uniformSendDataRequest       UniformSendDataRequest,
 * uniformSendDataIndication    UniformSendDataIndication,
 * tokenGrabRequest             TokenGrabRequest,
 * tokenGrabConfirm             TokenGrabConfirm,
 * tokenInhibitRequest          TokenInhibitRequest,
 * tokenInhibitConfirm          TokenInhibitConfirm,
 * tokenGiveRequest             TokenGiveRequest,
 * tokenGiveIndication          TokenGiveIndication,
 * tokenGiveResponse            TokenGiveResponse,
 * tokenGiveConfirm             TokenGiveConfirm,
 * tokenPleaseRequest           TokenPleaseRequest,
 * tokenPleaseIndication        TokenPleaseIndication,
 * tokenReleaseRequest          TokenReleaseRequest,
 * tokenReleaseConfirm          TokenReleaseConfirm,
 * tokenTestRequest             TokenTestRequest,
 * tokenTestConfirm             TokenTestConfirm
 * }
 *
 * @author Sascha Biedermann
 */
public class DomainMCSPDU extends ASN1Choice {

    @ASN1Tag(0)
    private ASN1Object plumbDomainIndication;//	PlumbDomainIndication,

    @ASN1Tag(1)
    private ErectDomainRequest erectDomainRequest;//	ErectDomainRequest,

    // not implemented
    @ASN1Tag(2)
    private ASN1Object mergeChannelsRequest;//	MergeChannelsRequest,

    @ASN1Tag(3)
    private ASN1Object mergeChannelsConfirm;//	MergeChannelsConfirm,

    @ASN1Tag(4)
    private ASN1Object purgeChannelsIndication;//		PurgeChannelsIndication,

    @ASN1Tag(5)
    private ASN1Object mergeTokensRequest;//		MergeTokensRequest,

    @ASN1Tag(6)
    private ASN1Object mergeTokensConfirm;//		MergeTokensConfirm,

    @ASN1Tag(7)
    private ASN1Object purgeTokensIndication;//		PurgeTokensIndication,

    @ASN1Tag(8)
    private ASN1Object disconnectProviderUltimatum;//DisconnectProviderUltimatum,

    @ASN1Tag(9)
    private ASN1Object rejectMCSPDUUltimatum;//		RejectMCSPDUUltimatum,

    @ASN1Tag(10)
    private AttachUserRequest attachUserRequest;//	AttachUserRequest,

    @ASN1Tag(11)
    private AttachUserConfirm attachUserConfirm;//		AttachUserConfirm,

    @ASN1Tag(12)
    private ASN1Object detachUserRequest;//	DetachUserRequest,

    @ASN1Tag(13)
    private ASN1Object detachUserIndication;//	DetachUserIndication,

    @ASN1Tag(14)
    private ChannelJoinRequest channelJoinRequest;//	ChannelJoinRequest,

    @ASN1Tag(15)
    private ChannelJoinConfirm channelJoinConfirm;//	ChannelJoinConfirm,

    @ASN1Tag(16)
    private ASN1Object channelLeaveRequest;//	ChannelLeaveRequest,

    @ASN1Tag(17)
    private ASN1Object channelConveneRequest;//	ChannelConveneRequest,

    @ASN1Tag(18)
    private ASN1Object channelConveneConfirm;//	ChannelConveneConfirm,

    @ASN1Tag(19)
    private ASN1Object channelDisbandRequest;//	ChannelDisbandRequest,

    @ASN1Tag(20)
    private ASN1Object channelDisbandIndication;//	ChannelDisbandIndication,

    @ASN1Tag(21)
    private ASN1Object channelAdmitRequest;//	ChannelAdmitRequest,

    @ASN1Tag(22)
    private ASN1Object channelAdmitIndication;//		ChannelAdmitIndication,

    @ASN1Tag(23)
    private ASN1Object channelExpelRequest;//	ChannelExpelRequest,

    @ASN1Tag(24)
    private ASN1Object channelExpelIndication;//	ChannelExpelIndication,

    @ASN1Tag(25)
    private SendDataRequest sendDataRequest;//		SendDataRequest,

    @ASN1Tag(26)
    private ASN1Object sendDataIndication;//	SendDataIndication,

    @ASN1Tag(27)
    private ASN1Object uniformSendDataRequest;//		UniformSendDataRequest,

    @ASN1Tag(28)
    private ASN1Object uniformSendDataIndication;//UniformSendDataIndication,

    @ASN1Tag(29)
    private ASN1Object tokenGrabRequest;//	TokenGrabRequest,

    @ASN1Tag(30)
    private ASN1Object tokenGrabConfirm;//	TokenGrabConfirm,

    @ASN1Tag(31)
    private ASN1Object tokenInhibitRequest;//	TokenInhibitRequest,

    @ASN1Tag(32)
    private ASN1Object tokenInhibitConfirm;//	TokenInhibitConfirm,

    @ASN1Tag(33)
    private ASN1Object tokenGiveRequest;//	TokenGiveRequest,

    @ASN1Tag(34)
    private ASN1Object tokenGiveIndication;//	TokenGiveIndication,

    @ASN1Tag(35)
    private ASN1Object tokenGiveResponse;//	TokenGiveResponse,

    @ASN1Tag(36)
    private ASN1Object tokenGiveConfirm;//	TokenGiveConfirm,

    @ASN1Tag(37)
    private ASN1Object tokenPleaseRequest;//	TokenPleaseRequest,

    @ASN1Tag(38)
    private ASN1Object tokenPleaseIndication;//		TokenPleaseIndication,

    @ASN1Tag(39)
    private ASN1Object tokenReleaseRequest;//		TokenReleaseRequest,

    @ASN1Tag(40)
    private ASN1Object tokenReleaseConfirm;//		TokenReleaseConfirm,

    @ASN1Tag(41)
    private ASN1Object tokenTestRequest;//		TokenTestRequest,

    @ASN1Tag(42)
    private ASN1Object tokenTestConfirm;//		TokenTestConfirm,

    @ASN1Tag(43)
    private ASN1Object nonStandard;//			NonStandardPDU,

    public ErectDomainRequest getErectDomainRequest() {
        return erectDomainRequest;
    }

    public void setErectDomainRequest(ErectDomainRequest erectDomainRequest) {
        this.erectDomainRequest = erectDomainRequest;
    }

    public AttachUserRequest getAttachUserRequest() {
        return attachUserRequest;
    }

    public void setAttachUserRequest(AttachUserRequest attachUserRequest) {
        this.attachUserRequest = attachUserRequest;
    }

    public AttachUserConfirm getAttachUserConfirm() {
        return attachUserConfirm;
    }

    public void setAttachUserConfirm(AttachUserConfirm attachUserConfirm) {
        this.attachUserConfirm = attachUserConfirm;
    }

    public ChannelJoinRequest getChannelJoinRequest() {
        return channelJoinRequest;
    }

    public void setChannelJoinRequest(ChannelJoinRequest channelJoinRequest) {
        this.channelJoinRequest = channelJoinRequest;
    }

    public ChannelJoinConfirm getChannelJoinConfirm() {
        return channelJoinConfirm;
    }

    public void setChannelJoinConfirm(ChannelJoinConfirm channelJoinConfirm) {
        this.channelJoinConfirm = channelJoinConfirm;
    }

    public SendDataRequest getSendDataRequest() {
        return sendDataRequest;
    }

    public void setSendDataRequest(SendDataRequest sendDataRequest) {
        this.sendDataRequest = sendDataRequest;
    }
}
