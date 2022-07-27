package org.amrv.net.deprecated;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
// servers must BIND
/*
TODO:
Hacer que se pueda crear el servidor con todas sus caracteristicas
Hacer que se creen los sockets de su conexion y que se eliminen cuando se cierre
la conexion con el servidor
 */
public class AppServer extends AppNetUtils {

    private ServerSocket server;
    private final HashSet<AppServerClient> clients = new HashSet<>();

    public AppServer() {
        connect((InetAddress) null, 0);
    }
    
    public AppServer(int port) {
        connect((InetAddress) null, port);
    }

    @Override
    public boolean connect(InetAddress address, int port) {
        try {
            if (server == null)
                server = new ServerSocket();
            else
                throw new UnsupportedOperationException("Host migration is not yet implemented");

            server.bind(new InetSocketAddress(address, 0));

            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppServer.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean disconnect(boolean clear) {
        try {
            // may want to clear all listeners upon disconnect???
            if (clear)
                clearListeners();
            server.close();

            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppClient.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean isConnected() {
        return server != null && server.isBound();
    }

    @Override
    public boolean canSend() {
        return !clients.isEmpty();
    }

    @Override
    public boolean canRecive() {
        // must be able to send data to the accepted sockets
        return !server.isClosed();
    }

    @Override
    public boolean send(byte[] data) {
        int accepts = 0;
        for (AppServerClient client : clients)
            accepts += client.send(data) ? 1 : 0;
        return accepts > 0;
    }

    public boolean sendAll(byte[] data) {
        int fails = 0;
        for (AppServerClient client : clients)
            fails += client.send(data) ? 0 : 1;
        return fails == 0;
    }

    @Override
    protected void reciveData() {
        if (server != null)
            try {
            clients.add(new AppServerClient(server.accept()));
        } catch (IOException ex) {
            Logger.getLogger(AppServer.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

}
