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
package de.coderarea.jrdp.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sascha Biedermann
 */
public class RDPChannelRegistry {


    public static class Channel {
        private String name;
        private int id;
        private ChannelState state;

        public Channel(String name, int id) {
            this.name = name;
            this.id = id;
            this.state = ChannelState.NONE;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public ChannelState getState() {
            return state;
        }

        public void setState(ChannelState state) {
            this.state = state;
        }
    }

    public static enum ChannelState {
        NONE,
        JOINED
    }


    private List<Channel> channelList;

    public RDPChannelRegistry() {
        channelList = new ArrayList<>();


        channelList.add(new Channel("msg", 1999));

        // The Server Channel ID store contains the MCS channel identifier of the server channel, which is
        // defined as the arbitrarily chosen but fixed value 0x03EA (1002).
        channelList.add(new Channel("server", 1002));

        channelList.add(new Channel("io", 1003));
        channelList.add(new Channel("rdpdr", 1004));
        channelList.add(new Channel("cliprdr", 1005));
        //channelList.add(new Channel("rdpsnd", 1006));
        channelList.add(new Channel("user", 1007));
    }

    public Channel getChannel(int id) {
        for (Channel c : channelList) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public Channel getChannel(String name) {
        for (Channel c : channelList) {
            if (name.equals(c.getName())) {
                return c;
            }
        }
        return null;
    }


}
