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

import de.coderarea.jrdp.protocol.ASN1.ASN1Choice;
import de.coderarea.jrdp.protocol.ASN1.ASN1Object;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Extensible;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

import java.io.IOException;

/**
 * ConnectGCCPDU ::= CHOICE {
 * conferenceCreateRequest   ConferenceCreateRequest,
 * conferenceCreateResponse  ConferenceCreateResponse,
 * conferenceQueryRequest    ConferenceQueryRequest,
 * conferenceQueryResponse   ConferenceQueryResponse,
 * conferenceJoinRequest     ConferenceJoinRequest,
 * conferenceJoinResponse    ConferenceJoinResponse,
 * conferenceInviteRequest   ConferenceInviteRequest,
 * conferenceInviteResponse  ConferenceInviteResponse,
 * ...
 * }
 *
 * @author Sascha Biedermann
 */
@ASN1Extensible
public class ConnectGCCPDU extends ASN1Choice {
    @ASN1Tag(0)
    private ConferenceCreateRequest conferenceCreateRequest;

    @ASN1Tag(1)
    private ConferenceCreateResponse conferenceCreateResponse;

    // not implemented

    @ASN1Tag(2)
    private ASN1Object conferenceQueryRequest;

    @ASN1Tag(3)
    private ASN1Object conferenceQueryResponse;

    @ASN1Tag(4)
    private ASN1Object conferenceJoinRequest;

    @ASN1Tag(5)
    private ASN1Object conferenceJoinResponse;

    @ASN1Tag(6)
    private ASN1Object conferenceInviteRequest;

    @ASN1Tag(7)
    private ASN1Object conferenceInviteResponse;


    public ConnectGCCPDU() throws IOException {
        // super(input, ConferenceCreateRequest.class, ConferenceCreateResponse.class, ASN1Object.class, ASN1Object.class, ASN1Object.class, ASN1Object.class, ASN1Object.class, ASN1Object.class);
    }

    public ConnectGCCPDU(ConferenceCreateRequest conferenceCreateRequest) {
        this.conferenceCreateRequest = conferenceCreateRequest;
    }

    public ConnectGCCPDU(ConferenceCreateResponse conferenceCreateResponse) {
        this.conferenceCreateResponse = conferenceCreateResponse;
    }


    public ConferenceCreateRequest getConferenceCreateRequest() {
        return conferenceCreateRequest;
    }

    public void setConferenceCreateRequest(ConferenceCreateRequest conferenceCreateRequest) {
        this.conferenceCreateRequest = conferenceCreateRequest;
    }

    public ConferenceCreateResponse getConferenceCreateResponse() {
        return conferenceCreateResponse;
    }

    public void setConferenceCreateResponse(ConferenceCreateResponse conferenceCreateResponse) {
        this.conferenceCreateResponse = conferenceCreateResponse;
    }
}
