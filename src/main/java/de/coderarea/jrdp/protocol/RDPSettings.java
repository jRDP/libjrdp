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

import de.coderarea.jrdp.protocol.MCS.BN_BER.DomainParameters;

/**
 * @author Sascha Biedermann
 */
public class RDPSettings {


    private DomainParameters domainParameters;
    private RDPChannelRegistry channelRegistry;


    public RDPSettings() {
        channelRegistry = new RDPChannelRegistry();
    }

    public DomainParameters getDomainParameters() {
        return domainParameters;
    }

    public void setDomainParameters(DomainParameters domainParameters) {
        this.domainParameters = domainParameters;
    }

    public RDPChannelRegistry getChannelRegistry() {
        return channelRegistry;
    }
}

