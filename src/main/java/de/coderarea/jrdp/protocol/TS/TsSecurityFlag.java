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
public enum TsSecurityFlag {
    SEC_EXCHANGE_PKT(0x0001),
    SEC_TRANSPORT_REQ(0x0002),
    RDP_SEC_TRANSPORT_RSP(0x0004),
    SEC_ENCRYPT(0x0008),
    SEC_RESET_SEQNO(0x010),
    SEC_IGNORE_SEQNO(0x0020),
    SEC_INFO_PKT(0x0040),
    SEC_LICENSE_PKT(0x0080),
    SEC_LICENSE_ENCRYPT_CS(0x0200),
    SEC_REDIRECTION_PKT(0x0400),
    SEC_SECURE_CHECKSUM(0x0800),
    SEC_AUTODETECT_REQ(0x1000),
    SEC_AUTODETECT_RSP(0x2000),
    SEC_HEARTBEAT(0x4000),
    SEC_FLAGSHI_VALID(0x8000);

    private int value;

    TsSecurityFlag(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Set<TsSecurityFlag> valueOf(int value) {
        Set<TsSecurityFlag> res = new HashSet<>();
        for (TsSecurityFlag item : TsSecurityFlag.values()) {
            if ((item.getValue() & value) != 0)
                res.add(item);
        }
        return res;
    }
}
