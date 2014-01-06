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
 * @author Sascha Biedermann
 */
public enum TsInfoFlag {
    INFO_MOUSE(0x00000001),
    INFO_DISABLECTRLALTDEL(0x00000002),
    INFO_AUTOLOGON(0x00000008),
    INFO_UNICODE(0x00000010),
    INFO_MAXIMIZESHELL(0x00000020),
    INFO_LOGONNOTIFY(0x00000040),
    INFO_COMPRESSION(0x00000080),
    //CompressionTypeMask(0x00001E00),
    INFO_ENABLEWINDOWSKEY(0x00000100),
    INFO_REMOTECONSOLEAUDIO(0x00002000),
    INFO_FORCE_ENCRYPTED_CS_PDU(0x00004000),
    INFO_RAIL(0x00008000),
    INFO_LOGONERRORS(0x00010000),
    INFO_MOUSE_HAS_WHEEL(0x00020000),
    INFO_PASSWORD_IS_SC_PIN(0x00040000),
    INFO_NOAUDIOPLAYBACK(0x00080000),
    INFO_USING_SAVED_CREDS(0x00100000),
    INFO_AUDIOCAPTURE(0x00200000),
    INFO_VIDEO_DISABLE(0x00400000),
    INFO_HIDEF_RAIL_SUPPORTED(0x02000000);

    private long value;

    TsInfoFlag(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static Set<TsInfoFlag> valueOf(long value) {
        Set<TsInfoFlag> res = new HashSet<>();
        for (TsInfoFlag item : TsInfoFlag.values()) {
            if ((item.getValue() & value) != 0)
                res.add(item);
        }
        return res;
    }
}
