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
 * @author Sascha Biedermann
 */
public class TsSecurityPacket extends TsPacket {


    private TsBasicSecurityHeader header;
    private long length;

    public TsSecurityPacket(InputStream input, TsBasicSecurityHeader header) throws IOException {
        super(input);

        if (!header.getFlags().contains(TsSecurityFlag.SEC_EXCHANGE_PKT))
            throw new IllegalArgumentException("Header must contain SEC_EXCHANGE_PKT");

        this.header = header;

        decode();
    }

    @Override
    protected void decode() throws IOException {
        length = readInteger();

    }

    @Override
    protected void encode(OutputStream output) throws IOException {

    }

    @Override
    public String toString() {
        return "TsSecurityPacket{" +
                "header=" + header +
                ", length=" + length +
                '}';
    }
}
