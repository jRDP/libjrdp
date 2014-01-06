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

import de.coderarea.jrdp.protocol.ASN1.ASN1OctetString;
import de.coderarea.jrdp.protocol.ASN1.ASN1Sequence;
import de.coderarea.jrdp.protocol.ASN1.annotation.ASN1Tag;

/**
 * ConnectData ::= SEQUENCE {
 * t124Identifier  Key, -- This shall be set to the value {itu-t recommendation t 124 version(0) 1}
 * connectPDU      OCTET STRING
 * }
 *
 * @author Sascha Biedermann
 */
public class ConnectData extends ASN1Sequence {
    @ASN1Tag(0)
    private Key t124Identifier;

    @ASN1Tag(1)
    private ASN1OctetString connectPDU;

    public Key getT124Identifier() {
        return t124Identifier;
    }

    public void setT124Identifier(Key t124Identifier) {
        this.t124Identifier = t124Identifier;
    }

    public byte[] getConnectPDU() {
        return connectPDU.getValue();
    }

    public void setConnectPDU(byte[] data) {
        this.connectPDU = new ASN1OctetString(data);
    }

    @Override
    public String toString() {
        return "ConnectData{" +
                "t124Identifier=" + t124Identifier +
                ", connectPDU=" + connectPDU +
                '}';
    }
}
