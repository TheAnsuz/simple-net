package org.amrv.snet;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class UDPClient {

    private DatagramSocket socket;

    public UDPClient() {

    }

    public void connect(InetAddress remoteIp, int remotePort) throws IOException {
        this.disconnect();
        socket = new DatagramSocket(remotePort, remoteIp);
    }

    public void disconnect() throws IOException {
        if (socket != null)
            socket.close();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        disconnect();
    }

    public void send(AppPacket packet) throws IOException {
        socket.send(packet.getDatagram());
    }

    public void recive(AppPacket storage) throws IOException {
        socket.receive(storage.getDatagram());
    }

}
