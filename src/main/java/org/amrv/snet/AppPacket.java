package org.amrv.snet;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Arrays;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class AppPacket {

    private byte[] data;
    private int amount;
    private DatagramPacket internalPacket;

    public AppPacket setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public AppPacket setData(byte[] data) {
        this.data = data;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public byte[] getData() {
        return data;
    }

    public byte[] getReadData() {
        return Arrays.copyOf(data, amount);
    }

    public void clear() {
        this.amount = 0;
    }

    public DatagramPacket getDatagram() {
        internalPacket.setData(data, 0, amount);
        return internalPacket;
    }

    public DatagramPacket getDatagram(InetAddress address, int port) {
        internalPacket.setData(data, 0, amount);
        internalPacket.setAddress(address);
        internalPacket.setPort(port);
        return internalPacket;
    }
    
    public DatagramPacket getDatagram(SocketAddress address) {
        internalPacket.setData(data, 0, amount);
        internalPacket.setSocketAddress(address);
        return internalPacket;
    }

}
