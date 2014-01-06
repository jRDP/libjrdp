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
 * Client Security Data (TS_UD_CS_SEC). <br/>
 * The TS_UD_CS_SEC data block contains security-related information used to advertise client
 * cryptographic support. This information is only relevant when Standard RDP Security mechanisms
 * (section 5.3) will be used. See sections 3 and 5.3.2 for a detailed discussion of how this information
 * is used.
 *
 * @author Sascha Biedermann
 */
public class TsClientSecurityData extends TsData {

    /**
     * A 32-bit unsigned integer. Cryptographic encryption methods
     * supported by the client and used in conjunction with Standard RDP Security. The client MUST
     * specify at least one encryption method, and the server MUST select one of the methods
     * specified by the client.
     */
    private Set<TsEncryptionMethods> encryptionMethods;

    /**
     * A 32-bit unsigned integer. This field is used exclusively for
     * the French locale. In French locale clients, encryptionMethods MUST be set to zero and
     * extEncryptionMethods MUST be set to the value to which encryptionMethods would have
     * been set. For non-French locale clients, this field MUST be set to zero.
     */
    private Set<TsEncryptionMethods> extEncryptionMethods;

    /**
     * A 32-bit unsigned integer. Cryptographic encryption methods
     * supported by the client and used in conjunction with Standard RDP Security. The client MUST
     * specify at least one encryption method, and the server MUST select one of the methods
     * specified by the client.
     */
    public static enum TsEncryptionMethods {
        /**
         * 40-bit session keys MUST be used to encrypt data (with RC4) and
         * generate Message Authentication Codes (MAC).
         */
        ENCRYPTION_FLAG_40BIT(0x00000001),

        /**
         * 128-bit session keys MUST be used to encrypt data (with RC4) and
         * generate MACs.
         */
        ENCRYPTION_FLAG_128BIT(0x00000002),

        /**
         * 56-bit session keys MUST be used to encrypt data (with RC4) and
         * generate MACs.
         */
        ENCRYPTION_FLAG_56BIT(0x00000008),

        /**
         * All encryption and Message Authentication Code generation routines
         * MUST be Federal Information Processing Standard (FIPS) 140-1
         * compliant.
         */
        ENCRYPTION_FLAG_FIPS(0x00000010);

        long value;

        TsEncryptionMethods(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }

        public static Set<TsEncryptionMethods> valueOf(long value) {
            Set<TsEncryptionMethods> res = new HashSet<>();
            for (TsEncryptionMethods item : TsEncryptionMethods.values()) {
                if ((item.getValue() & value) != 0)
                    res.add(item);
            }
            return res;
        }
    }


    protected TsClientSecurityData(InputStream input, TsUserDataHeader header) throws IOException {
        super(input, header);
        decode();
    }

    protected void decode() throws IOException {
        encryptionMethods = TsEncryptionMethods.valueOf(readInteger());
        extEncryptionMethods = TsEncryptionMethods.valueOf(readInteger());
    }

    @Override
    public String toString() {
        return "TsClientSecurityData{" +
                "encryptionMethods=" + encryptionMethods +
                ", extEncryptionMethods=" + extEncryptionMethods +
                '}';
    }
}
