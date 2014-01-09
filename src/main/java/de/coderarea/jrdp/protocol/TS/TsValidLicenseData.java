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
import java.util.Collections;

/**
 * The LICENSE_VALID_CLIENT_DATA structure contains information which indicates that the server
 * will not issue the client a license to store and that the Licensing Phase has ended successfully.
 *
 * @author Sascha Biedermann
 */
public class TsValidLicenseData extends TsSecurityPacket {


    public TsValidLicenseData() {
    }

    @Override
    public void encode(OutputStream output) throws IOException {
        ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();

        /** begin preamble **/

        // bMsgType - MUST be set to ERROR_ALERT (0xFF)
        writeByte(bufferStream, 0xFF);

        //flags - including version - RDP 4.0
        writeByte(bufferStream, 0x02);

        //wMsgSize - An 16-bit, unsigned integer. The size in bytes of the licensing packet (including the size of the preamble).
        writeShort(bufferStream, 16);


        /** begin validClientMessage **/
        // A Licensing Error Message (section 2.2.1.12.1.3) structure.
        // The dwStateTransition field MUST be set to ST_NO_TRANSITION (0x00000002). The
        // bbErrorInfo field MUST contain an empty binary large object (BLOB)


        // dwErrorCode STATUS_VALID_CLIENT 0x00000007
        writeInteger(bufferStream, 0x07);

        // dwStateTransition ST_NO_TRANSITION 0x00000002
        writeInteger(bufferStream, 0x02);

        /** begin bbErrorInfo  **/
        // wBlobType of type BB_ERROR_BLOB (0x0004)
        writeShort(bufferStream, 0x0004);

        // wBlobLen The size in bytes of the binary information in the blobData field
        writeShort(bufferStream, 0); // empty

        this.setHeader(new TsBasicSecurityHeader(Collections.singleton(TsSecurityFlag.SEC_LICENSE_PKT)));
        this.getHeader().encode(output);
        output.write(bufferStream.toByteArray());
    }


}


