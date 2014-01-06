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
import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 * @author Sascha Biedermann
 */
public class TsInfoPacket extends TsPacket {
    private final static Logger logger = LogManager.getLogger(TsInfoPacket.class);

    private TsBasicSecurityHeader header;

    private final static long COMPRESSION_TYPE_MASK = 0x00001E00;

    private long codePage;
    private Set<TsInfoFlag> flags;
    private TsCompressionType compressionType;

    private String domain;
    private String userName;
    private String password;
    private String alternateShell;
    private String workingDir;


    public TsInfoPacket(InputStream input, TsBasicSecurityHeader header) throws IOException {
        super(input);

        if (!header.getFlags().contains(TsSecurityFlag.SEC_INFO_PKT))
            throw new IllegalArgumentException("Header must contain SEC_INFO_PKT");

        this.header = header;

        decode();
    }

    @Override
    protected void decode() throws IOException {
        codePage = readInteger();

        long flags = readInteger();
        this.flags = TsInfoFlag.valueOf(flags & ~COMPRESSION_TYPE_MASK);
        this.compressionType = TsCompressionType.valueOf((int) ((flags & COMPRESSION_TYPE_MASK) >> 9));

        int cbDomain = readShort();
        int cbUserName = readShort();
        int cbPassword = readShort();
        int cbAlternateShell = readShort();
        int cbWorkingDir = readShort();

        int offset = getNullCharLength();
        byte[] buffer = new byte[cbDomain + offset];
        read(buffer);
        domain = decodeString(buffer);

        buffer = new byte[cbUserName + offset];
        read(buffer);
        userName = decodeString(buffer);

        buffer = new byte[cbPassword + offset];
        read(buffer);
        password = decodeString(buffer);

        buffer = new byte[cbAlternateShell + offset];
        read(buffer);
        alternateShell = decodeString(buffer);

        buffer = new byte[cbWorkingDir + offset];
        read(buffer);
        workingDir = decodeString(buffer);


    }

    private int getNullCharLength() throws UnsupportedEncodingException {
        if (this.flags.contains(TsInfoFlag.INFO_UNICODE))
            return "\0".getBytes("UTF-16LE").length;
        else
            return "\0".getBytes("Windows-1252").length;
    }

    private String decodeString(byte[] buffer) throws UnsupportedEncodingException {
        String res;
        if (this.flags.contains(TsInfoFlag.INFO_UNICODE))
            res = new String(buffer, "UTF-16LE");
        else
            res = new String(buffer, "Windows-1252");

        // cut off null-termination
        int idx = res.indexOf('\0');
        if (idx > -1)
            res = res.substring(0, idx);

        return res;
    }

    @Override
    protected void encode(OutputStream output) throws IOException {

    }

    @Override
    public String toString() {
        return "TsInfoPacket{" +
                "header=" + header +
                ", codePage=" + codePage +
                ", flags=" + flags +
                ", compressionType=" + compressionType +
                ", domain='" + domain + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", alternateShell='" + alternateShell + '\'' +
                ", workingDir='" + workingDir + '\'' +
                '}';
    }
}
