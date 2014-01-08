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
import java.util.HashSet;
import java.util.Set;

/**
 * The TS_SECURITY_HEADER structure is used to store security flags.
 *
 * @author Sascha Biedermann
 */
public class TsBasicSecurityHeader extends TsPacket {
    private final static Logger logger = LogManager.getLogger(TsBasicSecurityHeader.class);

    /**
     * User Data Header has a constant size of 4 bytes.
     */
    public final static int SIZE = 4;

    private Set<TsSecurityFlag> flags;
    private int flagsHi;

    public TsBasicSecurityHeader(InputStream input) throws IOException {
        super(input);
        decode();
    }

    public TsBasicSecurityHeader(Set<TsSecurityFlag> flags) {
        this.flags = new HashSet<>(flags);
    }

    protected void decode() throws IOException {

        // A 16-bit, unsigned integer that contains security flags.
        int flags = readShort();
        this.flags = TsSecurityFlag.valueOf(flags);

        // A 16-bit, unsigned integer. This field is reserved for future use. It is
        // currently unused and all values are ignored. This field MUST contain valid data only if the
        // SEC_FLAGSHI_VALID bit (0x8000) is set in the flags field. If this bit is not set, the flagsHi
        // field is uninitialized and MAY contain random data.
        this.flagsHi = readShort();

        logger.trace("falgs: {}", this.flags);
    }

    @Override
    protected void encode(OutputStream output) throws IOException {
        assert !getFlags().contains(TsSecurityFlag.SEC_FLAGSHI_VALID) : "not implemented";

        int flags = 0;
        for (TsSecurityFlag f : getFlags()) {
            flags += f.getValue();
        }

        // A 16-bit, unsigned integer that contains security flags.
        writeShort(output, flags);

        // A 16-bit, unsigned integer. This field is reserved for future use.
        writeShort(output, 0);
    }

    public Set<TsSecurityFlag> getFlags() {
        return flags;
    }

    @Override
    public String toString() {
        return "TsBasicSecurityHeader{" +
                "flags=" + flags +
                ", flagsHi=" + flagsHi +
                '}';
    }
}
