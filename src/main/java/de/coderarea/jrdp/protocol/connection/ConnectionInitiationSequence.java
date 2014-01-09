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
package de.coderarea.jrdp.protocol.connection;

import de.coderarea.jrdp.ConnectionHandler;
import de.coderarea.jrdp.protocol.X224.RdpProtocol;
import de.coderarea.jrdp.protocol.X224.X224ConnectionConfirm;
import de.coderarea.jrdp.protocol.X224.X224ConnectionRequest;
import de.coderarea.jrdp.protocol.X224.X224Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by uidv6498 on 13.12.13.
 *
 * @author Sascha Biedermann
 */
public class ConnectionInitiationSequence extends SequenceHandler {
    private final static Logger logger = LogManager.getLogger(ConnectionInitiationSequence.class);

    public ConnectionInitiationSequence(ConnectionHandler connectionHandler) {
        super(connectionHandler);
    }

    @Override
    public void run() throws IOException {

        X224Packet x224 = getConnectionHandler().receiveX224Packet();
        assert x224 instanceof X224ConnectionRequest : "Expected ConnectionRequest, got " + x224.getClass().getSimpleName();
        X224ConnectionRequest cr = (X224ConnectionRequest) x224;
        logger.debug("Connection-Request: {}", cr);

        X224ConnectionConfirm cc = new X224ConnectionConfirm();

        // TODO: only PROTOCOL_RDP supported
        cc.getRdpNegRsp().getProtocolSet().add(RdpProtocol.PROTOCOL_RDP);

        // the Destination reference is set to zero
        cc.setDstRef(0);

        // the Source reference is set to 0x1234
        cc.setSrcRef(0x1234);

        getConnectionHandler().sendT123Packet(cc.encode());
        logger.debug("Connection-Confirm: {}", cc);

        //TODO: RDP Negotiation Failure (RDP_NEG_FAILURE) - not implemented
    }

}
