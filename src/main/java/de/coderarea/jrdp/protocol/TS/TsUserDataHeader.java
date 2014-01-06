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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User Data Header
 *
 * @author Sascha Biedermann
 */
public class TsUserDataHeader extends TsPacket {
    private final static Logger logger = LogManager.getLogger(TsUserDataHeader.class);

    /**
     * User Data Header has a constant size of 4 bytes.
     */
    public final static int SIZE = 4;

    public static enum TsUserDataType {
        CS_CORE(0xC001),
        CS_SECURITY(0xC002),
        CS_NET(0xC003),
        CS_CLUSTER(0xC004),
        CS_MONITOR(0xC005),
        CS_MCS_MSGCHANNEL(0xC006),
        CS_MONITOR_EX(0xC008),
        CS_MULTITRANSPORT(0xC00A),
        SC_CORE(0x0C01),
        SC_SECURITY(0x0C02),
        SC_NET(0x0C03),
        SC_MCS_MSGCHANNEL(0x0C04),
        SC_MULTITRANSPORT(0x0C08);

        private int value;

        TsUserDataType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private TsUserDataType type;
    private int length;

    public TsUserDataHeader(InputStream input) throws IOException {
        super(input);
        decode();
    }

    public TsUserDataHeader(TsUserDataType type, int length) {
        this.type = type;
        this.length = length + SIZE;
    }

    protected void decode() throws IOException {

        int t = readShort();
        for (TsUserDataType item : TsUserDataType.values()) {
            if (item.getValue() == t) {
                type = item;
                break;
            }
        }

        length = readShort();
        logger.trace("type: {}, length: {}", type, length);
    }

    @Override
    protected void encode(OutputStream output) throws IOException {
        writeShort(output, getType().getValue());
        writeShort(output, getLength());
    }

    public TsUserDataType getType() {
        return type;
    }

    public int getLength() {
        return length;
    }
}
