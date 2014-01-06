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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Client Network Data (TS_UD_CS_NET). <br/>
 * The TS_UD_CS_NET packet contains a list of requested virtual channels.
 *
 * @author Sascha Biedermann
 */
public class TsClientNetworkData extends TsData {


    private long channelCount;
    private TsChannelDefinition[] channelDefArray;

    public static class TsChannelDefinition {
        private String name;
        private Set<TsChannelOption> options;

        public TsChannelDefinition(String name, Set<TsChannelOption> options) {
            this.name = name;
            this.options = options;
        }

        /**
         * Channel option flags.
         */
        public static enum TsChannelOption {
            /**
             * Absence of this flag indicates that this channel is a
             * placeholder and that the server MUST NOT set it up.
             */
            CHANNEL_OPTION_INITIALIZED(0x80000000),

            /**
             * This flag is unused and its value MUST be ignored by the
             * server.
             */
            CHANNEL_OPTION_ENCRYPT_RDP(0x40000000),

            /**
             * This flag is unused and its value MUST be ignored by the
             * server.
             */
            CHANNEL_OPTION_ENCRYPT_SC(0x20000000),

            /**
             * This flag is unused and its value MUST be ignored by the
             * server.
             */
            CHANNEL_OPTION_ENCRYPT_CS(0x10000000),

            /**
             * Channel data MUST be sent with high MCS priority.
             */
            CHANNEL_OPTION_PRI_HIGH(0x08000000),

            /**
             * Channel data MUST be sent with medium MCS priority.
             */
            CHANNEL_OPTION_PRI_MED(0x04000000),

            /**
             * Channel data MUST be sent with low MCS priority.
             */
            CHANNEL_OPTION_PRI_LOW(0x02000000),

            /**
             * Virtual channel data MUST be compressed if RDP data is
             * being compressed.
             */
            CHANNEL_OPTION_COMPRESS_RDP(0x00800000),

            /**
             * Virtual channel data MUST be compressed, regardless of
             * RDP compression settings.
             */
            CHANNEL_OPTION_COMPRESS(0x00400000),

            /**
             * The value of this flag MUST be ignored by the server. The
             * visibility of the Channel PDU Header (section 2.2.6.1.1) is
             * determined by the CHANNEL_FLAG_SHOW_PROTOCOL
             * (0x00000010) flag as defined in the flags field (section
             * 2.2.6.1.1).
             */
            CHANNEL_OPTION_SHOW_PROTOCOL(0x00200000),

            /**
             * Channel MUST be persistent across remote control
             * transactions.
             */
            REMOTE_CONTROL_PERSISTENT(0x00100000);

            long value;

            TsChannelOption(long value) {
                this.value = value;
            }

            public long getValue() {
                return value;
            }

            public static Set<TsChannelOption> valueOf(long value) {
                Set<TsChannelOption> res = new HashSet<>();
                for (TsChannelOption item : TsChannelOption.values()) {
                    if ((item.getValue() & value) != 0)
                        res.add(item);
                }
                return res;
            }
        }

        @Override
        public String toString() {
            return "TsChannelDefinition{" +
                    "name='" + name + '\'' +
                    ", options=" + options +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<TsChannelOption> getOptions() {
            return options;
        }
    }

    protected TsClientNetworkData(InputStream input, TsUserDataHeader header) throws IOException {
        super(input, header);
        decode();
    }

    protected void decode() throws IOException {
        channelCount = readInteger();
        channelDefArray = new TsChannelDefinition[(int) channelCount];

        byte[] nameBuffer = new byte[8];
        for (int i = 0; i < channelCount; i++) {
            read(nameBuffer);
            String name = new String(nameBuffer, "Windows-1252");
            name = name.substring(0, name.indexOf('\0'));

            long options = readInteger();
            channelDefArray[i] = new TsChannelDefinition(name, TsChannelDefinition.TsChannelOption.valueOf(options));
        }
    }

    public TsChannelDefinition[] getChannelDefArray() {
        return channelDefArray;
    }

    public void setChannelDefArray(TsChannelDefinition[] channelDefArray) {
        this.channelDefArray = channelDefArray;
    }

    @Override
    public String toString() {
        return "TsClientNetworkData{" +
                "channelCount=" + channelCount +
                ", channelDefArray=" + Arrays.toString(channelDefArray) +
                '}';
    }
}
