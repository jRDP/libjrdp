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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The TS_UD_SC_NET data block is a reply to the static virtual channel list presented in the Client
 * Network Data structure (section 2.2.1.3.4).
 *
 * @author Sascha Biedermann
 */
public class TsServerNetworkData extends TsData {
    private int mCSChannelId;
    private List<Integer> channelIdArray;


    public TsServerNetworkData() throws IOException {
        this.channelIdArray = new ArrayList<>();
    }


    @Override
    protected void encode(OutputStream output) throws IOException {
        ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();

        // MCSChannelId (2 bytes)
        writeShort(bufferStream, getmCSChannelId());

        // channelCount (2 bytes):
        writeShort(bufferStream, getChannelIdArray().size());

        for (Integer i : getChannelIdArray()) {
            writeShort(bufferStream, i);
        }

        // Pad (2 bytes)
        if (bufferStream.size() % 4 != 0) {
            writeShort(bufferStream, 0);
        }

        assert bufferStream.size() % 4 != 0;

        this.setHeader(new TsUserDataHeader(TsUserDataHeader.TsUserDataType.SC_NET, bufferStream.size()));
        this.getHeader().encode(output);
        output.write(bufferStream.toByteArray());
    }

    public List<Integer> getChannelIdArray() {
        return channelIdArray;
    }

    public int getmCSChannelId() {
        return mCSChannelId;
    }

    public void setmCSChannelId(int mCSChannelId) {
        this.mCSChannelId = mCSChannelId;
    }

    @Override
    public String toString() {
        return "TsServerNetworkData{" +
                "mCSChannelId=" + mCSChannelId +
                ", channelIdArray=" + channelIdArray +
                '}';
    }
}


