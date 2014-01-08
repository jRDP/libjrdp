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
package de.coderarea.jrdp;


import de.coderarea.jrdp.protocol.*;
import de.coderarea.jrdp.protocol.T123.T123Packet;
import de.coderarea.jrdp.protocol.X224.X224Data;
import de.coderarea.jrdp.protocol.X224.X224Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by uidv6498 on 12.12.13.
 *
 * @author Sascha Biedermann
 */
public class ConnectionHandler implements Runnable {
    private final static Logger logger = LogManager.getLogger(ConnectionHandler.class);

    private final Socket socket;
    private RDPSettings settings;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
        this.settings = new RDPSettings();
    }


    @Override
    public void run() {
        logger.debug("{} started running.", Thread.currentThread());
        logger.info("New connection from {}", socket.getInetAddress());

        try {

            new ConnectionInitiationSequence(this).run();

            BasicSettingsExchangeSequence bseSequence = new BasicSettingsExchangeSequence(this);
            bseSequence.run();

            new ChannelConnectionSequence(this).run();

            new LicensingSequence(this).run();

            System.out.println("--End--");
            while (!socket.isClosed()) {
                if (socket.getInputStream().available() > 0) {
                    byte b = (byte) socket.getInputStream().read();
                    System.out.format("%02X ", b);
                } else
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
            }


        } catch (Exception e) {
            logger.error("Error in connection thread.", e);
        } finally {
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e1) {
                }
            }
            logger.info("Connection closed.");
        }
    }

    public RDPSettings getSettings() {
        return settings;
    }

    protected T123Packet receiveT123Packet() throws IOException {
        T123Packet tPacket = new T123Packet();
        tPacket.decode(socket.getInputStream());
        return tPacket;
    }

    public X224Packet receiveX224Packet() throws IOException {
        return X224Packet.getInstance(receiveT123Packet());
    }

    public void sendX224DataPacket(byte[] data) throws IOException {
        X224Data packet = new X224Data();
        packet.setData(data);
        sendT123Packet(packet.encode());

    }

    public void sendT123Packet(byte[] data) throws IOException {
        T123Packet tPacket = new T123Packet();
        tPacket.setTPDU(data);
        tPacket.encode(socket.getOutputStream());
    }
}
