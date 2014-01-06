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
import java.util.HashSet;
import java.util.Set;

/**
 * Client Cluster Data (TS_UD_CS_CLUSTER). <br/>
 * The TS_UD_CS_CLUSTER data block is sent by the client to the server either to advertise that it can
 * support the Server Redirection PDUs (sections 2.2.13.2 and 2.2.13.3) or to request a connection to
 * a given session identifier.
 *
 * @author Sascha Biedermann
 */
public class TsClientClusterData extends TsData {


    private Set<TsClusterFlag> flags;
    private TsRedirectionVersion redirectionVersion;
    private long redirectedSessionID;


    /**
     * A 32-bit, unsigned integer. Cluster information flags.
     */
    public static enum TsClusterFlag {
        /**
         * The client can receive server session redirection
         * packets. If this flag is set, the ServerSessionRedirectionVersionMask MUST contain the
         * server session redirection version that the client
         * supports.
         */
        REDIRECTION_SUPPORTED(0x00000001),

        /**
         * The RedirectedSessionID field contains an ID that
         * identifies a session on the server to associate with the
         * connection.
         */
        REDIRECTED_SESSIONID_FIELD_VALID(0x00000002),

        /**
         * The client logged on with a smart card.
         */
        REDIRECTED_SMARTCARD(0x00000040);


        long value;

        TsClusterFlag(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }

        public static Set<TsClusterFlag> valueOf(long value) {
            Set<TsClusterFlag> res = new HashSet<>();
            for (TsClusterFlag item : TsClusterFlag.values()) {
                if ((item.getValue() & value) != 0)
                    res.add(item);
            }
            return res;
        }
    }

    final static int REDIRECTION_VERSION_MASK = 0x3C;

    /**
     * The ServerSessionRedirectionVersionMask is a 4-bit enumerated value containing the server
     * session redirection version supported by the client. The following are possible version values.
     */
    public static enum TsRedirectionVersion {
        REDIRECTION_VERSION1(0x00),
        REDIRECTION_VERSION2(0x01),
        REDIRECTION_VERSION3(0x02),
        REDIRECTION_VERSION4(0x03),
        REDIRECTION_VERSION5(0x04),
        REDIRECTION_VERSION6(0x05);

        int value;

        TsRedirectionVersion(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static TsRedirectionVersion valueOf(int value) {
            for (TsRedirectionVersion item : TsRedirectionVersion.values()) {
                if (item.getValue() == value)
                    return item;
            }
            return null;
        }
    }

    protected TsClientClusterData(InputStream input, TsUserDataHeader header) throws IOException {
        super(input, header);
        decode();
    }

    protected void decode() throws IOException {
        long value = readInteger();

        flags = TsClusterFlag.valueOf(value);

        int redirValue = (int) (((value & REDIRECTION_VERSION_MASK) >> 2) & 0x0F);
        redirectionVersion = TsRedirectionVersion.valueOf(redirValue);


        redirectedSessionID = readInteger();
    }

    @Override
    public String toString() {
        return "TsClientClusterData{" +
                "flags=" + flags +
                ", redirectionVersion=" + redirectionVersion +
                ", redirectedSessionID=" + redirectedSessionID +
                '}';
    }
}
