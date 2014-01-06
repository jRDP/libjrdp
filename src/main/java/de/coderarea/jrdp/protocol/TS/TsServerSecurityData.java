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

/**
 * The TS_UD_SC_SEC1 data block returns negotiated security-related information to the client. See
 * section 5.3.2 for a detailed discussion of how this information is used.
 *
 * @author Sascha Biedermann
 */
public class TsServerSecurityData extends TsData {

    public TsServerSecurityData() throws IOException {
    }

    @Override
    protected void encode(OutputStream output) throws IOException {
        ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();

        // TODO encryptionMethod
        writeInteger(bufferStream, 0);

        // TODO encryptionLevel
        writeInteger(bufferStream, 0);


        this.setHeader(new TsUserDataHeader(TsUserDataHeader.TsUserDataType.SC_SECURITY, bufferStream.size()));
        this.getHeader().encode(output);
        output.write(bufferStream.toByteArray());
    }


}


