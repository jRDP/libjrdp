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
 * Base class for TS_UD_*
 *
 * @author Sascha Biedermann
 */
public abstract class TsPacket {
    private InputStream input;
    private int bytesRead;

    protected TsPacket(InputStream input) throws IOException {
        this.input = input;
        bytesRead = 0;
    }

    protected TsPacket() {

    }

    protected abstract void decode() throws IOException;

    protected abstract void encode(OutputStream output) throws IOException;


    protected final void writeShort(OutputStream output, int value) throws IOException {
        output.write(value & 0xFF);
        output.write((value >> 8) & 0xFF);
    }

    protected final void writeInteger(OutputStream output, int value) throws IOException {
        output.write(value & 0xFF);
        output.write((value >> 8) & 0xFF);
        output.write((value >> 16) & 0xFF);
        output.write((value >> 24) & 0xFF);
    }

    protected final void read(byte[] buffer) throws IOException {
        bytesRead += input.read(buffer);
    }

    /**
     * Reads a 8-bit unsigned integer from <code>input</code>.
     *
     * @return 8-bit integer
     * @throws IOException
     */
    protected final int readByte() throws IOException {
        bytesRead++;
        return input.read();
    }

    /**
     * Reads a 16-bit unsigned integer from <code>input</code>
     *
     * @return 16-bit integer
     * @throws IOException
     */
    protected final int readShort() throws IOException {
        bytesRead += 2;
        return input.read() | input.read() << 8;
    }

    /**
     * Reads a 32-bit unsigned integer from <code>input</code>.
     *
     * @return 32-bit integer
     * @throws IOException
     */
    protected final long readInteger() throws IOException {
        bytesRead += 4;
        return input.read() | input.read() << 8 | input.read() << 16 | input.read() << 24;
    }

    /**
     * Returns the number of bytes that have been read from <code>input</code>.
     *
     * @return number of bytes
     */
    protected final int getBytesRead() {
        return bytesRead;
    }
}
