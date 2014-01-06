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
 * A 16-bit, unsigned integer that specifies capabilities early in the connection sequence.
 *
 * @author Sascha Biedermann
 */
public enum TsClientEarlyCapability {

    /**
     * Indicates that the client supports the Set Error Info PDU.
     */
    RNS_UD_CS_SUPPORT_ERRINFO_PDU(0x0001),

    /**
     * Indicates that the client is requesting a session color depth of 32 bpp.
     * This flag is necessary because the highColorDepth field does not support a value of 32.
     * If this flag is set, the highColorDepth field SHOULD be set to 24 to support a value of 32.
     * If this flag is set, the provide an acceptable fallback for the scenario where the server does not support 32 bpp color.
     */
    RNS_UD_CS_WANT_32BPP_SESSION(0x0002),


    /**
     * Indicates that the client supports the Server Status Info PDU.
     */
    RNS_UD_CS_SUPPORT_STATUSINFO_PDU(0x0004),

    /**
     * Indicates that the client supports asymmetric keys larger than 512 bits for use with the
     * Server Certificate (section 2.2.1.4.3.1) sent in the Server Security Data block.
     */
    RNS_UD_CS_STRONG_ASYMMETRIC_KEYS(0x0008),

    /**
     * An unused flag that MUST be ignored by the server.
     */
    RNS_UD_CS_UNUSED(0x0010),

    /**
     * Indicates that the connectionType field contains valid data.
     */
    RNS_UD_CS_VALID_CONNECTION_TYPE(0x0020),

    /**
     * Indicates that the client supports the Monitor Layout PDU.
     */
    RNS_UD_CS_SUPPORT_MONITOR_LAYOUT_PDU(0x0040),

    /**
     * Indicates that the client supports network characteristics detection using the structures
     * and PDUs described in section 2.2.14.
     */
    RNS_UD_CS_SUPPORT_NETCHAR_AUTODETECT(0x0080),

    /**
     * Indicates that the client supports the Graphics Pipeline Extension Protocol
     * described in [MS-RDPEGFX] sections 1, 2, and 3.
     */
    RNS_UD_CS_SUPPORT_DYNVC_GFX_PROTOCOL(0x0100),

    /**
     * Indicates that the client supports Dynamic DST.
     * Dynamic DST information is provided by the client in the
     * <code>cbDynamicDSTTimeZoneKeyName</code>,
     * <code>dynamicDSTTimeZoneKeyName</code> and
     * <code>dynamicDaylightTimeDisabled</code> fields of the
     * Extended Info Packet (section 2.2.1.11.1.1.1).
     */
    RNS_UD_CS_SUPPORT_DYNAMIC_TIME_ZONE(0x0200),

    /**
     * Indicates that the client supports the Heartbeat PDU (section 2.2.16.1).
     */
    RNS_UD_CS_SUPPORT_HEARTBEAT_PDU(0x0400);


    int value;

    TsClientEarlyCapability(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Set<TsClientEarlyCapability> valueOf(int value) {
        Set<TsClientEarlyCapability> res = new HashSet<>();
        for (TsClientEarlyCapability item : TsClientEarlyCapability.values()) {
            if ((item.getValue() & value) != 0)
                res.add(item);
        }
        return res;
    }
}
