package org.amrv.snet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class TCPServer<TCPInternalClient> extends Server {

    private ServerSocket server = null;

    @Override
    protected void sendInternal(Client client, AppPacket packet) throws
            IOException {
        client.send(packet);
    }

    @Override
    protected void startInternal(int port, InetAddress address) throws
            IOException {
        if (server != null)
            this.shutdown();

        server = new ServerSocket(port, 0, address);
    }

    @Override
    protected void kickInternal(Client client) throws IOException {
        client.disconnect();
    }

    @Override
    protected void shutdownInternal() throws IOException {
        for (Client c : this.getClients())
            c.disconnect();

        if (server != null)
            server.close();

        server = null;
    }

    @Override
    protected TCPInternalClient reciveClient() throws IOException {
        if (server == null)
            return null;

        return new TCPInternalClient(server.accept());
    }

    public static class TCPInternalClient extends Client {

        private final Socket socket;

        public TCPInternalClient(Socket socket) throws IOException {
            this.socket = socket;

            getPacketSender().initialize(socket.getOutputStream());
            getPacketReciver().initialize(socket.getInputStream());

        }

        @Override
        public void sendInternal(AppPacket packet) throws IOException {
            if (getPacketSender().isInitialized())
                getPacketSender().send(packet);
        }

        @Override
        protected void connectInternal(InetAddress address, int port) throws
                IOException {
            throw new UnsupportedOperationException("This type of client cannot be reconnected and are managed via server");
        }

        @Override
        protected void disconnectInternal() throws IOException {
            if (getPacketReciver().isInitialized())
                getPacketReciver().closure();

            if (getPacketSender().isInitialized())
                getPacketSender().closure();

            if (socket != null)
                socket.close();

        }

        @Override
        protected AppPacket readPacket() throws IOException {
            if (getPacketReciver().isInitialized() && getPacketReciver()
                    .mustRead())
                return getPacketReciver().read();
            else
                return null;
        }

    }

}
