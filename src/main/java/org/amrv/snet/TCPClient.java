package org.amrv.snet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class TCPClient extends Client {

    private Socket socket;

    @Override
    public void sendInternal(AppPacket packet) throws IOException {
        if (getPacketSender().isInitialized())
            getPacketSender().send(packet);
    }

    @Override
    protected void connectInternal(InetAddress address, int port) throws
            IOException {
        super.disconnect();

        socket = new Socket(address, port);

        getPacketSender().initialize(socket.getOutputStream());

        getPacketReciver().initialize(socket.getInputStream());

    }

    @Override
    protected void disconnectInternal() throws IOException {
        if (getPacketReciver().isInitialized())
            getPacketReciver().closure();

        if (getPacketSender().isInitialized())
            getPacketSender().closure();

        if (socket != null)
            socket.close();
        
        socket = null;
    }

    @Override
    protected AppPacket readPacket() throws IOException {
        if (getPacketReciver().isInitialized() && getPacketReciver().mustRead())
            return getPacketReciver().read();
        else
            return null;
    }

}
