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

/**
 * Implements the Connection Sequence conducted in order to establish a connection. </br>
 *
 * <pre>
 * CLIENT                                                                    SERVER
 *   |                                                                         |----------------------------------------
 *   |-----------------------X.224 Connection Request PDU--------------------->|
 *   |                                                                         |   Connection Initiation
 *   |<----------------------X.224 Connection Confirm PDU----------------------|
 *   |                                                                         |----------------------------------------
 *   |-------MCS Connect-Initial PDU with GCC Conference Create Request------->|
 *   |                                                                         |   Basic Settings Exchange
 *   |<-----MCS Connect-Response PDU with GCC Conference Create Response-------|
 *   |                                                                         |----------------------------------------
 *   |------------------------MCS Erect Domain Request PDU-------------------->|
 *   |                                                                         |
 *   |------------------------MCS Attach User Request PDU--------------------->|
 *   |                                                                         |
 *   |<-----------------------MCS Attach User Confirm PDU----------------------|   Channel Connection
 *   |                                                                         |
 *   |------------------------MCS Channel Join Request PDU-------------------->|
 *   |                                                                         |
 *   |<-----------------------MCS Channel Join Confirm PDU---------------------|
 *   |                                                                         |----------------------------------------
 *   |----------------------------Security Exchange PDU----------------------->|   RDP Security Commencement
 *   |                                                                         |----------------------------------------
 *   |-------------------------------Client Info PDU-------------------------->|   Secure Settings Exchange
 *   |                                                                         |----------------------------------------
 *   |<---------------------License Error PDU - Valid Client-------------------|   Licensing
 *   |                                                                         |----------------------------------------
 *   |<-----------------------------Demand Active PDU--------------------------|
 *   |------------------------------Confirm Active PDU------------------------>|
 *   |-------------------------------Synchronize PDU-------------------------->|
 *   |---------------------------Control PDU - Cooperate---------------------->|
 *   |------------------------Control PDU - Request Control------------------->|  TODO :-(
 *   |--------------------------Persistent Key List PDU(s)-------------------->|
 *   |--------------------------------Font List PDU--------------------------->|
 *   |<------------------------------Synchronize PDU---------------------------|
 *   |<--------------------------Control PDU - Cooperate-----------------------|
 *   |<-----------------------Control PDU - Granted Control--------------------|
 *   |<-------------------------------Font Map PDU-----------------------------|
 * </pre>
 *
 *
 * @author Sascha Biedermann
 */
package de.coderarea.jrdp.protocol.connection;