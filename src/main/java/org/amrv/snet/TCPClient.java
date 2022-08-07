package org.amrv.snet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class TCPClient {

    private Socket socket;
    private InputStream input;
    private OutputStream output;

    public TCPClient() {

    }

    public void connect(InetAddress remoteIp, int remotePort, InetAddress localIp, int localPort) throws
            IOException {
        this.disconnect();
        socket = new Socket(remoteIp, remotePort, localIp, localPort);
        
        if (!socket.isInputShutdown())
            input = socket.getInputStream();
        
        if (!socket.isOutputShutdown())
            output = socket.getOutputStream();
    }

    public void disconnect() throws IOException {
        if (input != null)
            input.close();
        if (output != null)
            output.close();
        if (socket != null)
            socket.close();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        disconnect();
    }

    public void send(AppPacket packet) throws IOException {
        if (output != null)
            output.write(packet.getData(), 0, packet.getAmount());
    }

    public void recive(AppPacket storage) throws IOException {
        if (input != null && input.available() > 0)
            storage.setAmount(input.read(storage.getData()));
    }

}
