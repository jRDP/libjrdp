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

/**
 * Created by uidv6498 on 13.12.13.
 *
 * @author Sascha Biedermann
 */

public enum RdpProtocol {
    PROTOCOL_RDP(0x00000000),
    PROTOCOL_SSL(0x00000001),
    PROTOCOL_HYBRID(0x00000002),
    PROTOCOL_HYBRID_EX(0x00000008);

    private final long value;

    RdpProtocol(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static RdpProtocol valueOf(long value) {
        for (RdpProtocol item : RdpProtocol.values()) {
            if (item.getValue() == value)
                return item;
        }
        return null;
    }

}