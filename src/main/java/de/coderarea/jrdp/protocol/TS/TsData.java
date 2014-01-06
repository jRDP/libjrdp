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
import java.io.OutputStream;

/**
 * User: sascha
 * Date: 23.12.13
 * Time: 16:45
 */
public abstract class TsData extends TsPacket {


    private TsUserDataHeader header;

    protected TsData(InputStream input, TsUserDataHeader header) throws IOException {
        super(input);
        this.header = header;
    }

    protected TsData() {

    }

    protected void decode() throws IOException {
        throw new UnsupportedOperationException("Decoding " + this.getClass().getSimpleName() + " is not implemented.");
    }

    protected void encode(OutputStream output) throws IOException {
        throw new UnsupportedOperationException("Encoding " + this.getClass().getSimpleName() + " is not implemented.");
    }


    protected void setHeader(TsUserDataHeader header) {
        this.header = header;
    }

    public TsUserDataHeader getHeader() {
        return header;
    }

    /**
     * Checks if the end of the stream is reached.
     *
     * @return true if end is reached, otherwise false
     */
    protected final boolean isEOS() {
        return header.getLength() - TsUserDataHeader.SIZE - getBytesRead() == 0;
    }
}
