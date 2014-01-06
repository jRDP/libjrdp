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
package de.coderarea.jrdp.protocol.ASN1.PER;

import de.coderarea.jrdp.protocol.ASN1.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * PER Encoder - Packed Encoding Rules.
 *
 * @author Sascha Biedermann
 * @see <a href="http://www.itu.int/rec/T-REC-X.691-200811-I/en">ASN.1 encoding rules: Specification of Packed Encoding Rules (PER)</a>
 */
public class ASN1PerEncoder extends ASN1Encoder {


    private BitOutputStream output;

    public ASN1PerEncoder(OutputStream output) {
        this.output = new BitOutputStream(output);
    }

    @Override
    public void encode(ASN1Object obj) throws IOException {
        doEncode(obj);
        output.writePadding();
    }


    protected void _encode(Object obj) throws IOException {
        doEncode(obj);
    }

    private void doEncode(Object obj) throws IOException {

        /** ASN1Object - Tree **/
        if (obj instanceof ASN1Integer) {
            new ASN1IntegerCoder(this, (ASN1Integer) obj);
        } else if (obj instanceof ASN1Sequence) {
            new ASN1SequenceCoder(this, (ASN1Sequence) obj);
        } else if (obj instanceof ASN1Choice) {
            new ASN1ChoiceCoder(this, (ASN1Choice) obj);
        } else if (obj instanceof ASN1OctetString) {
            new ASN1OctetStringCoder(this, (ASN1OctetString) obj);
        } else if (obj instanceof ASN1ObjectIdentifier) {
            new ASN1ObjectIdentifierCoder(this, (ASN1ObjectIdentifier) obj);
        } else if (obj instanceof ASN1Null) {
            // do nothing for NULL

            /** Object - Tree **/
        } else if (obj instanceof Enum) {
            new ASN1EnumCoder(this, (Enum) obj);
        } else if (obj instanceof Boolean) {
            new ASN1BooleanCoder(this, (Boolean) obj);
        } else if (obj instanceof List) {
            new ASN1SetCoder(this, (List<?>) obj);
        } else {
            throw new UnsupportedOperationException("Encoding type " + obj.getClass().getName() + " not implemented.");
        }

    }

    /**
     * @see de.coderarea.jrdp.protocol.ASN1.BitOutputStream#writeBit(int)
     */
    protected void writeBit(int bit) throws IOException {
        output.writeBit(bit);
    }

    /**
     * @see de.coderarea.jrdp.protocol.ASN1.BitOutputStream#writeBit(int, int)
     */
    protected void writeBit(int bits, int n) throws IOException {
        output.writeBit(bits, n);
    }

    /**
     * @see de.coderarea.jrdp.protocol.ASN1.BitOutputStream#write(int)
     */
    protected void write(int b) throws IOException {
        output.write(b);
    }

    /**
     * @see de.coderarea.jrdp.protocol.ASN1.BitOutputStream#write(byte[])
     */
    protected void write(byte[] b) throws IOException {
        output.write(b);
    }

    /**
     * @see de.coderarea.jrdp.protocol.ASN1.BitOutputStream#writePadding()
     */
    protected int writePadding() throws IOException {
        return output.writePadding();
    }
}
