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

/**
 * @author Sascha Biedermann
 */
public enum TsCompressionType {

    PACKET_COMPR_TYPE_8K(0x0),
    PACKET_COMPR_TYPE_64K(0x1),
    PACKET_COMPR_TYPE_RDP6(0x2),
    PACKET_COMPR_TYPE_RDP61(0x3);

    int value;

    TsCompressionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TsCompressionType valueOf(int value) {
        for (TsCompressionType item : TsCompressionType.values()) {
            if (item.getValue() == value)
                return item;
        }
        return null;
    }
}
