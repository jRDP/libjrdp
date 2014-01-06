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
package de.coderarea.jrdp.protocol.X224;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by uidv6498 on 12.12.13.
 *
 * @author Sascha Biedermann
 */
public class X224ConnectionConfirm extends X224Packet {

    private int dstRef;
    private int srcRef;

    /**
     * CC – Connection confirm code: 1101.
     * Bits 8 to 5 of octet 2.
     */
    public final static byte CODE = (byte) 0xD0;

    /**
     * CDT – Initial credit allocation (set to 0000 in classes 0 and 1 when specified as preferred class).
     * Bits 4 to 1 of octet 2.
     */
    private final static byte CDT = 0x00;

    private RdpNegRsp rdpNegRsp;

    public X224ConnectionConfirm() {
        rdpNegRsp = new RdpNegRsp();
    }

    protected void decode(InputStream input) throws IOException {
        throw new UnsupportedOperationException();
    }

    public byte[] encode() {
        byte[] header = new byte[7];

        header[1] = CODE | CDT;
        header[2] = (byte) ((getDstRef() >> 8) & 0xFF);
        header[3] = (byte) (getDstRef() & 0xFF);

        header[4] = (byte) ((getSrcRef() >> 8) & 0xFF);
        header[5] = (byte) (getSrcRef() & 0xFF);

        // Class Option
        header[6] = 0x00;

        byte[] data = rdpNegRsp.encode();

        // Length excluding Length Indicator
        header[0] = (byte) (header.length - 1 + data.length);

        return ArrayUtils.addAll(header, data);
    }

    public int getDstRef() {
        return dstRef;
    }

    public void setDstRef(int dstRef) {
        this.dstRef = dstRef;
    }

    public int getSrcRef() {
        return srcRef;
    }

    public void setSrcRef(int srcRef) {
        this.srcRef = srcRef;
    }

    public RdpNegRsp getRdpNegRsp() {
        return rdpNegRsp;
    }

    public void setRdpNegRsp(RdpNegRsp rdpNegRsp) {
        this.rdpNegRsp = rdpNegRsp;
    }

    @Override
    public String toString() {
        return "X224ConnectionConfirm{" +
                "dstRef=" + dstRef +
                ", srcRef=" + srcRef +
                ", rdpNegRsp=" + rdpNegRsp +
                '}';
    }
}
