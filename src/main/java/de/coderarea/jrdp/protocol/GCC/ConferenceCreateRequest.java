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

import de.coderarea.jrdp.protocol.ASN1.ASN1Object;
import de.coderarea.jrdp.protocol.ASN1.ASN1Sequence;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Extensible;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Optional;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

/**
 * ConferenceCreateRequest.<br />
 * <p/>
 * <pre>
 * ConferenceCreateRequest ::=
 * SEQUENCE { -- MCS-Connect-Provider request user data
 * conferenceName          ConferenceName,
 * convenerPassword        Password OPTIONAL,
 * password                Password OPTIONAL,
 * lockedConference        BOOLEAN,
 * listedConference        BOOLEAN,
 * conductibleConference   BOOLEAN,
 * terminationMethod       TerminationMethod,
 * conductorPrivileges     SET OF Privilege OPTIONAL,
 * conductedPrivileges     SET OF Privilege OPTIONAL,
 * nonConductedPrivileges  SET OF Privilege OPTIONAL,
 * conferenceDescription   TextString OPTIONAL,
 * callerIdentifier        TextString OPTIONAL,
 * userData                UserData OPTIONAL,
 * ...,
 * conferencePriority      ConferencePriority OPTIONAL,
 * conferenceMode          ConferenceMode OPTIONAL
 * }
 * </pre>
 */
@ASN1Extensible
public class ConferenceCreateRequest extends ASN1Sequence {
    @ASN1Tag(0)
    private ConferenceName conferenceName;

    @ASN1Tag(1)
    @ASN1Optional
    private ASN1Object convenerPassword; //Password OPTIONAL

    @ASN1Tag(2)
    @ASN1Optional
    private ASN1Object password; //Password OPTIONAL

    @ASN1Tag(3)
    private Boolean lockedConference;

    @ASN1Tag(4)
    private Boolean listedConference;

    @ASN1Tag(5)
    private Boolean conductibleConference;

    @ASN1Tag(6)
    private TerminationMethod terminationMethod;

    @ASN1Tag(7)
    @ASN1Optional
    private ASN1Object conductorPrivileges;    //SET OF Privilege OPTIONAL,

    @ASN1Tag(8)
    @ASN1Optional
    private ASN1Object conductedPrivileges;     //SET OF Privilege OPTIONAL,

    @ASN1Tag(9)
    @ASN1Optional
    private ASN1Object nonConductedPrivileges;  //SET OF Privilege OPTIONAL,

    @ASN1Tag(10)
    @ASN1Optional
    private ASN1Object conferenceDescription;   //TextString OPTIONAL,

    @ASN1Tag(11)
    @ASN1Optional
    private ASN1Object callerIdentifier;        //TextString OPTIONAL,

    @ASN1Tag(12)
    @ASN1Optional
    private UserData userData;                //UserData OPTIONAL,


    public ConferenceCreateRequest() {
    }

    public ConferenceName getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(ConferenceName conferenceName) {
        this.conferenceName = conferenceName;
    }

    public Boolean getListedConference() {
        return listedConference;
    }

    public void setListedConference(Boolean listedConference) {
        this.listedConference = listedConference;
    }

    public Boolean getLockedConference() {
        return lockedConference;
    }

    public void setLockedConference(Boolean lockedConference) {
        this.lockedConference = lockedConference;
    }

    public Boolean getConductibleConference() {
        return conductibleConference;
    }

    public void setConductibleConference(Boolean conductibleConference) {
        this.conductibleConference = conductibleConference;
    }

    public TerminationMethod getTerminationMethod() {
        return terminationMethod;
    }

    public void setTerminationMethod(TerminationMethod terminationMethod) {
        this.terminationMethod = terminationMethod;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
