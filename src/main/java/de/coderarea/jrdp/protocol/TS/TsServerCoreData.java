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

import de.coderarea.jrdp.protocol.X224.RdpProtocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Client Core Data (TS_UD_CS_CORE). <br/>
 * The TS_UD_CS_CORE data block contains core client connection-related information.
 *
 * @author Sascha Biedermann
 */
public class TsServerCoreData extends TsData {
    //0x00080001 RDP 4.0 servers
    //0x00080004 RDP 5.0, 5.1, 5.2, 6.0, 6.1, 7.0, 7.1, and 8.0 servers
    private final int version = 0x00080001;

    private Set<RdpProtocol> clientRequestedProtocols;
    private Set<TsClientEarlyCapability> earlyCapabilityFlags;


    public TsServerCoreData() throws IOException {
        this.clientRequestedProtocols = new HashSet<>();
        this.clientRequestedProtocols.add(RdpProtocol.PROTOCOL_RDP);
        this.earlyCapabilityFlags = new HashSet<>();
    }


    @Override
    protected void encode(OutputStream output) throws IOException {
        ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();

        // version
        writeInteger(bufferStream, version);

        // clientRequestedProtocols, integer that contains the flags sent
        // by the client in the requestedProtocols field of the RDP Negotiation Request
        int flag = 0;
        for (RdpProtocol p : clientRequestedProtocols) {
            flag += p.getValue();
        }
        writeInteger(bufferStream, flag);

        // earlyCapabilityFlags (4 bytes): A 32-bit, unsigned integer that specifies capabilities early in
        // the connection sequence.
        flag = 0;
        for (TsClientEarlyCapability cap : earlyCapabilityFlags) {
            flag += cap.getValue();
        }
        writeInteger(bufferStream, flag);

        this.setHeader(new TsUserDataHeader(TsUserDataHeader.TsUserDataType.SC_CORE, bufferStream.size()));
        this.getHeader().encode(output);
        output.write(bufferStream.toByteArray());
    }

    @Override
    public String toString() {
        return "TsServerCoreData{" +
                "version=" + version +
                ", clientRequestedProtocols=" + clientRequestedProtocols +
                ", earlyCapabilityFlags=" + earlyCapabilityFlags +
                '}';
    }
}


