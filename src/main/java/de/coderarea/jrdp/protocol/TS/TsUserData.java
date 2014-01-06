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
import java.util.List;

/**
 * @author Sascha Biedermann
 */
public class TsUserData {
    private final static Logger logger = LogManager.getLogger(TsUserData.class);

    private TsClientCoreData clientCoreData;
    private TsClientSecurityData clientSecurityData;
    private TsClientClusterData clientClusterData;
    private TsClientNetworkData clientNetworkData;

    public TsUserData(InputStream input) throws IOException {
        decode(input);
    }

    public static void encode(OutputStream output, List<TsData> packets) throws IOException {
        for (TsData packet : packets) {
            logger.trace("encode - {}", packet);
            packet.encode(output);
            logger.trace("encode - type: {}, length: {}", packet.getHeader().getType(), packet.getHeader().getLength());
        }
    }

    protected void decode(InputStream input) throws IOException {


        while (input.available() > 0) {
            TsUserDataHeader header = new TsUserDataHeader(input);
            if (header.getType() == null)
                break;

            switch (header.getType()) {
                case CS_CORE:
                    clientCoreData = new TsClientCoreData(input, header);
                    logger.debug("CS_CORE - length: {}, read: {}", header.getLength() - TsUserDataHeader.SIZE, clientCoreData.getBytesRead());
                    break;

                case CS_SECURITY:
                    clientSecurityData = new TsClientSecurityData(input, header);
                    logger.debug("CS_SECURITY - length: {}, read: {}", header.getLength() - TsUserDataHeader.SIZE, clientSecurityData.getBytesRead());
                    break;

                case CS_CLUSTER:
                    clientClusterData = new TsClientClusterData(input, header);
                    logger.debug("CS_CLUSTER - length: {}, read: {}", header.getLength() - TsUserDataHeader.SIZE, clientClusterData.getBytesRead());
                    break;

                case CS_NET:
                    clientNetworkData = new TsClientNetworkData(input, header);
                    logger.debug("CS_NET - length: {}, read: {}", header.getLength() - TsUserDataHeader.SIZE, clientNetworkData.getBytesRead());
                    break;

                default:
                    throw new UnsupportedEncodingException(header.getType() + " - not implemented");
                    //input.skip(header.getLength() - header.SIZE);
                    //logger.warn("found {}, but was discarded - not implemented", header.getType());
            }

        }
    }

    public TsClientCoreData getClientCoreData() {
        return clientCoreData;
    }

    public TsClientSecurityData getClientSecurityData() {
        return clientSecurityData;
    }

    public TsClientClusterData getClientClusterData() {
        return clientClusterData;
    }

    public TsClientNetworkData getClientNetworkData() {
        return clientNetworkData;
    }
}
