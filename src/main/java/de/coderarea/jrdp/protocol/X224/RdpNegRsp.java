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
package de.coderarea.jrdp.protocol.X224;

import java.util.HashSet;
import java.util.Set;

/**
 * RDP Negotiation Response (RDP_NEG_RSP)
 *
 * @author Sascha Biedermann
 */
public class RdpNegRsp {

    public final static byte TYPE_RDP_NEG_RSP = 0x02;

    public enum Flag {
        EXTENDED_CLIENT_DATA_SUPPORTED((byte) 0x01),
        DYNVC_GFX_PROTOCOL_SUPPORTED((byte) 0x02),
        NEGRSP_FLAG_RESERVED((byte) 0x04),
        RESTRICTED_ADMIN_MODE_SUPPORTED((byte) 0x08);

        private byte flag;

        Flag(byte flag) {
            this.flag = flag;
        }

        public byte getFlag() {
            return flag;
        }
    }

    private Set<Flag> flagSet;
    private Set<RdpProtocol> protocolSet;

    public RdpNegRsp() {
        this.flagSet = new HashSet<>();
        this.protocolSet = new HashSet<>();
        protocolSet.add(RdpProtocol.PROTOCOL_RDP);
    }

    public Set<Flag> getFlagSet() {
        return flagSet;
    }

    public Set<RdpProtocol> getProtocolSet() {
        return protocolSet;
    }

    public byte[] encode() {
        byte data[] = new byte[8];

        data[0] = TYPE_RDP_NEG_RSP;
        data[1] = 0;
        for (Flag f : flagSet) {
            data[1] |= f.getFlag();
        }

        // length 8 bytes
        data[2] = 0x08;
        data[3] = 0;

        int protocol = 0;
        for (RdpProtocol p : protocolSet) {
            protocol |= p.getValue();
        }

        data[4] = (byte) (protocol & 0xFF);
        data[5] = (byte) ((protocol >> 8) & 0xFF);
        data[6] = (byte) ((protocol >> 16) & 0xFF);
        data[7] = (byte) ((protocol >> 24) & 0xFF);

        return data;
    }

    @Override
    public String toString() {
        return "RdpNegRsp{" +
                "flagSet=" + flagSet +
                ", protocolSet=" + protocolSet +
                '}';
    }
}
