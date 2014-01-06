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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * RDP Negotiation Request (RDP_NEG_REQ)
 *
 * @author Sascha Biedermann
 */
public class RdpNegReq {

    public final static byte TYPE_RDP_NEG_REQ = 0x01;

    public enum Flag {
        RESTRICTED_ADMIN_MODE_REQUIRED((byte) 0x01),
        CORRELATION_INFO_PRESENT((byte) 0x08);

        private final byte flag;

        Flag(byte flag) {
            this.flag = flag;
        }

        public byte getFlag() {
            return flag;
        }
    }

    private Set<Flag> flagSet;
    private Set<RdpProtocol> protocolSet;

    public RdpNegReq() {
        this.flagSet = new HashSet<>();
        this.protocolSet = new HashSet<>();
    }

    public void decode(InputStream input) throws IOException {
        int type = input.read();
        if (TYPE_RDP_NEG_REQ != type) {
            throw new IllegalArgumentException(String.format("Expected type 0x%02X, got 0x%02X", TYPE_RDP_NEG_REQ, type));
        }

        byte flag = (byte) input.read();
        for (Flag f : Flag.values()) {
            if ((flag & f.getFlag()) != 0)
                flagSet.add(f);
        }

        int length = input.read() | input.read() << 8;
        assert length == 8;

        int protocol = input.read() | input.read() << 8 | input.read() << 16 | input.read() << 24;
        for (RdpProtocol p : RdpProtocol.values()) {
            if ((protocol & p.getValue()) != 0)
                protocolSet.add(p);
            if (p.getValue() == 0)
                protocolSet.add(p);
        }
    }

    public Set<Flag> getFlagSet() {
        return flagSet;
    }

    public Set<RdpProtocol> getProtocolSet() {
        return protocolSet;
    }

    @Override
    public String toString() {
        return "RdpNegReq{" +
                "flagSet=" + flagSet +
                ", protocolSet=" + protocolSet +
                '}';
    }
}
