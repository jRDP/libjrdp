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

import de.coderarea.jrdp.protocol.X224.RdpProtocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Client Core Data (TS_UD_CS_CORE). <br/>
 * The TS_UD_CS_CORE data block contains core client connection-related information.
 *
 * @author Sascha Biedermann
 */
public class TsClientCoreData extends TsData {
    private final static Logger logger = LogManager.getLogger(TsClientCoreData.class);
    // mandatory fields
    private int majorVersion;
    private int minorVersion;
    private int desktopWidth;
    private int desktopHeight;
    private TsColorDepth colorDepth;
    private int sASSequence;
    private long keyboardLayout;
    private long clientBuild;
    private String clientName;
    private long keyboardType;
    private long keyboardSubType;
    private long keyboardFunctionKey;
    private String imeFileName;

    // optional fields
    private TsColorDepth postBeta2ColorDepth;
    private Integer clientProductId;
    private Long serialNumber;
    private TsHighColorDepth highColorDepth;
    private Set<TsSupportedDepth> supportedColorDepths;
    private Set<TsClientEarlyCapability> earlyCapabilityFlags;
    private byte[] clientDigProductId;
    private TsConnectionType connectionType;
    private RdpProtocol serverSelectedProtocol;
    private Long desktopPhysicalWidth;
    private Long desktopPhysicalHeight;
    private TsDesktopOrientation desktopOrientation;
    private Long desktopScaleFactor;
    private Long deviceScaleFactor;

    public static enum TsColorDepth {
        COLOR_4BPP(0xCA00),
        COLOR_8BBP(0xCA01),
        COLOR_16BPP_555(0xCA02),
        COLOR_16BPP_565(0xCA03),
        COLOR_24BPP(0xCA04);


        int value;

        TsColorDepth(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static TsColorDepth valueOf(int value) {
            for (TsColorDepth item : TsColorDepth.values()) {
                if (item.getValue() == value)
                    return item;
            }
            return null;
        }
    }

    public static enum TsHighColorDepth {
        HIGH_COLOR_4BPP(0x0004),
        HIGH_COLOR_8BPP(0x0008),
        HIGH_COLOR_15BPP(0x000F),
        HIGH_COLOR_16BPP(0x0010),
        HIGH_COLOR_24BPP(0x0018);

        int value;

        TsHighColorDepth(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static TsHighColorDepth valueOf(int value) {
            for (TsHighColorDepth item : TsHighColorDepth.values()) {
                if (item.getValue() == value)
                    return item;
            }
            return null;
        }
    }

    public static enum TsSupportedDepth {
        SUPPORT_24BPP(0x0001),
        SUPPORT_16BPP(0x0002),
        SUPPORT_15BPP(0x0004),
        SUPPORT_32BPP(0x0008);

        int value;

        TsSupportedDepth(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Set<TsSupportedDepth> valueOf(int value) {
            Set<TsSupportedDepth> res = new HashSet<>();
            for (TsSupportedDepth item : TsSupportedDepth.values()) {
                if ((item.getValue() & value) != 0)
                    res.add(item);
            }
            return res;
        }
    }

    public static enum TsConnectionType {
        /**
         * Modem (56 Kbps)
         */
        CONNECTION_TYPE_MODEM(0x01),
        /**
         * Low-speed broadband (256 Kbps - 2 Mbps)
         */
        CONNECTION_TYPE_BROADBAND_LOW(0x02),

        /**
         * Satellite (2 Mbps - 16 Mbps with high latency)
         */
        CONNECTION_TYPE_SATELLITE(0x03),

        /**
         * High-speed broadband (2 Mbps - 10 Mbps)
         */
        CONNECTION_TYPE_BROADBAND_HIGH(0x04),

        /**
         * WAN (10 Mbps or higher with high latency)
         */
        CONNECTION_TYPE_WAN(0x05),

        /**
         * LAN (10 Mbps or higher)
         */
        CONNECTION_TYPE_LAN(0x06),

        /**
         * The server SHOULD attempt to detect the connection type. If the connection type can be successfully
         * determined then the performance flags, sent by the client in the performanceFlags field of the Extended
         * Info Packet (section 2.2.1.11.1.1.1), SHOULD be ignored and the server SHOULD determine the
         * appropriate set of performance flags to apply to the remote session (based on the detected connection
         * type). If the <code>RNS_UD_CS_SUPPORT_NETCHAR_AUTODETECT</code> (0x0080) flag is not set in
         * the <code>earlyCapabilityFlags </code> field, then this value SHOULD be ignored.
         */
        CONNECTION_TYPE_AUTODETECT(0x07);

        int value;

        TsConnectionType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static TsConnectionType valueOf(int value) {
            for (TsConnectionType item : TsConnectionType.values()) {
                if (item.getValue() == value)
                    return item;
            }
            return null;
        }
    }

    /**
     * A 16-bit, unsigned integer. The requested orientation of the desktop, in degrees.
     */
    public static enum TsDesktopOrientation {
        ORIENTATION_LANDSCAPE(0),
        ORIENTATION_PORTRAIT(90),
        ORIENTATION_LANDSCAPE_FLIPPED(180),
        ORIENTATION_PORTRAIT_FLIPPED(270);

        int value;

        TsDesktopOrientation(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static TsDesktopOrientation valueOf(int value) {
            for (TsDesktopOrientation item : TsDesktopOrientation.values()) {
                if (item.getValue() == value)
                    return item;
            }
            return null;
        }
    }

    public TsClientCoreData(InputStream input, TsUserDataHeader header) throws IOException {
        super(input, header);
        decode();
    }

    protected void decode() throws IOException {
        int length = getHeader().getLength();

        majorVersion = readShort();
        minorVersion = readShort();
        desktopWidth = readShort();
        desktopHeight = readShort();

        this.colorDepth = TsColorDepth.valueOf(readShort());

        // Secure access sequence. This field
        // SHOULD be set to RNS_UD_SAS_DEL (0xAA03).
        sASSequence = readShort();
        assert sASSequence == 0xAA03;

        keyboardLayout = readInteger();
        clientBuild = readInteger();

        byte[] buf = new byte[32];
        read(buf);
        clientName = new String(buf, "UTF-16LE");
        clientName = clientName.substring(0, clientName.indexOf('\0'));

        keyboardType = readInteger();
        keyboardSubType = readInteger();
        keyboardFunctionKey = readInteger();

        buf = new byte[64];
        read(buf);
        imeFileName = new String(buf, "UTF-16LE");
        imeFileName = imeFileName.substring(0, imeFileName.indexOf('\0'));

        /****** start of optional fields ******/

        logger.trace("start of optional section, length: {}, read: {}, remaining: {}", length, getBytesRead(), length - getBytesRead());

        if (isEOS()) return;
        postBeta2ColorDepth = TsColorDepth.valueOf(readShort());

        if (isEOS()) return;
        clientProductId = readShort();

        if (isEOS()) return;
        serialNumber = readInteger();

        if (isEOS()) return;
        highColorDepth = TsHighColorDepth.valueOf(readShort());

        if (isEOS()) return;
        supportedColorDepths = TsSupportedDepth.valueOf(readShort());

        if (isEOS()) return;
        earlyCapabilityFlags = TsClientEarlyCapability.valueOf(readShort());

        if (isEOS()) return;
        clientDigProductId = new byte[64];
        read(clientDigProductId);

        if (isEOS()) return;
        connectionType = TsConnectionType.valueOf(readByte());

        if (isEOS()) return;
        // pad1octet
        readByte();

        if (isEOS()) return;
        serverSelectedProtocol = RdpProtocol.valueOf(readInteger());

        if (isEOS()) return;
        desktopPhysicalWidth = readInteger();

        if (isEOS()) return;
        desktopPhysicalHeight = readInteger();

        if (isEOS()) return;
        desktopOrientation = TsDesktopOrientation.valueOf(readShort());

        if (isEOS()) return;
        desktopScaleFactor = readInteger();

        if (isEOS()) return;
        deviceScaleFactor = readInteger();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TsClientCoreData{");
        sb.append("\n");
        sb.append("majorVersion=" + majorVersion);
        sb.append("\n\t");
        sb.append("minorVersion=" + minorVersion);
        sb.append("\n\t");
        sb.append("desktopWidth=" + desktopWidth);
        sb.append("\n\t");
        sb.append("desktopHeight=" + desktopHeight);
        sb.append("\n\t");
        sb.append("colorDepth=" + colorDepth);
        sb.append("\n\t");
        sb.append("sASSequence=" + sASSequence);
        sb.append("\n\t");
        sb.append("keyboardLayout=" + keyboardLayout);
        sb.append("\n\t");
        sb.append("clientBuild=" + clientBuild);
        sb.append("\n\t");
        sb.append("clientName='" + clientName + '\'');
        sb.append("\n\t");
        sb.append("keyboardType=" + keyboardType);
        sb.append("\n\t");
        sb.append("keyboardSubType=" + keyboardSubType);
        sb.append("\n\t");
        sb.append("keyboardFunctionKey=" + keyboardFunctionKey);
        sb.append("\n\t");
        sb.append("imeFileName='" + imeFileName + '\'');
        sb.append("\n\t");
        sb.append("postBeta2ColorDepth=" + postBeta2ColorDepth);
        sb.append("\n\t");
        sb.append("clientProductId=" + clientProductId);
        sb.append("\n\t");
        sb.append("serialNumber=" + serialNumber);
        sb.append("\n\t");
        sb.append("highColorDepth=" + highColorDepth);
        sb.append("\n\t");
        sb.append("supportedColorDepths=" + supportedColorDepths);
        sb.append("\n\t");
        sb.append("earlyCapabilityFlags=" + earlyCapabilityFlags);
        sb.append("\n\t");
        sb.append("clientDigProductId=" + Arrays.toString(clientDigProductId));
        sb.append("\n\t");
        sb.append("connectionType=" + connectionType);
        sb.append("\n\t");
        sb.append("serverSelectedProtocol=" + serverSelectedProtocol);
        sb.append("\n\t");
        sb.append("desktopPhysicalWidth=" + desktopPhysicalWidth);
        sb.append("\n\t");
        sb.append("desktopPhysicalHeight=" + desktopPhysicalHeight);
        sb.append("\n\t");
        sb.append("desktopOrientation=" + desktopOrientation);
        sb.append("\n\t");
        sb.append("desktopScaleFactor=" + desktopScaleFactor);
        sb.append("\n\t");
        sb.append("deviceScaleFactor=" + deviceScaleFactor);
        sb.append("\n");
        sb.append('}');

        return sb.toString();
    }
}
