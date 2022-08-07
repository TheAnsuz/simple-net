package org.amrv.snet;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public abstract class Client extends SimpleNet {

    protected static enum ConnectionResult {
        CONNECTED,
        REFUSED,
        FAILURE,
        TIMEOUT,
        UNKNOWN_HOST
    }

    protected static enum SentResult {
        SENT,
        FAILURE
    }

    private volatile AppPacket internalPacket = new AppPacket();

    @Override
    void updateInternal() {
        try {
            internalPacket.clear();
            reciveInternal(internalPacket);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect(InetAddress address, int port) throws IOException {
        new Socket(remoteAddress, remotePort, localAddress, localPort)
    }

    protected abstract ConnectionResult connectInternal() throws IOException;

    protected abstract void disconnectInternal() throws
            IOException;

    protected abstract void sendInternal(AppPacket packet) throws IOException;

    protected abstract void reciveInternal(AppPacket storage) throws
            IOException;

}
