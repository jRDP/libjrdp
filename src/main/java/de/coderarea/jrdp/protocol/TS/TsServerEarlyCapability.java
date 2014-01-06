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
package de.coderarea.jrdp.protocol.TS;

import java.util.HashSet;
import java.util.Set;

/**
 * A 32-bit, unsigned integer that specifies capabilities early in the connection sequence.
 *
 * @author Sascha Biedermann
 */
public enum TsServerEarlyCapability {

    /**
     * Indicates that the following key combinations are
     * reserved by the server operating system:
     *  WIN + Z
     *  WIN + CTRL + TAB
     *  WIN + C
     *  WIN + .
     *  WIN + SHIFT + .
     * In addition, the monitor boundaries of the remote
     * session are employed by the server operating system
     * to trigger user interface elements via touch or mouse
     * gestures.
     */
    RNS_UD_SC_EDGE_ACTIONS_SUPPORTED(0x00000001),

    /**
     * Indicates that the server supports Dynamic DST.
     * Dynamic DST information is provided by the client in
     * the cbDynamicDSTTimeZoneKeyName,
     * dynamicDSTTimeZoneKeyName and
     * dynamicDaylightTimeDisabled fields of the
     * Extended Info Packet (section 2.2.1.11.1.1.1).
     */
    RNS_UD_SC_DYNAMIC_DST_SUPPORTED(0x00000002);

    int value;

    TsServerEarlyCapability(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Set<TsServerEarlyCapability> valueOf(int value) {
        Set<TsServerEarlyCapability> res = new HashSet<>();
        for (TsServerEarlyCapability item : TsServerEarlyCapability.values()) {
            if ((item.getValue() & value) != 0)
                res.add(item);
        }
        return res;
    }
}
