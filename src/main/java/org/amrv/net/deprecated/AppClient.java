package org.amrv.net.deprecated;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
// using streams means that it uses TCP protocol
// so there will be a check and wait to recibe all packets
// clients must CONNECT
public class AppClient extends AppNetUtils {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private final ByteBuffer buffer;

    public AppClient() {
        this.buffer = ByteBuffer.allocate(4098);
        socket = new Socket();
    }

    public AppClient(byte[] ipDigits) {
        this();
        connect(ipDigits, 0);
    }

    public AppClient(String address) {
        this();
        connect(address, 0);
    }

    public AppClient(InetAddress address) {
        this();
        connect(address, 0);
    }

    public AppClient(byte[] ipDigits, int port) {
        this();
        connect(ipDigits, port);
    }

    public AppClient(String address, int port) {
        this();
        connect(address, port);
    }

    public AppClient(InetAddress address, int port) {
        this();
        connect(address, port);
    }

    @Override
    public boolean connect(InetAddress address, int port) {
        try {
            if (socket.isBound()) {
                disconnect();
                socket = new Socket();
            }

            socket.connect(secureSocketAddress(address, port));

            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppClient.class.getName())
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
            socket.close();

            if (output != null)
                output.close();

            if (input != null)
                input.close();

            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppClient.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    @Override
    public boolean canSend() {
        return isConnected() && !socket.isOutputShutdown();
    }

    @Override
    public boolean canRecive() {
        return isConnected() && !socket.isInputShutdown();
    }

    @Override
    public boolean send(byte[] data) {
        try {
            output.write(data);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppClient.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    protected void reciveData() {
        if (input == null)
            return;
        try {
//                    System.out.println("Read...");
            int read = input.read(buffer.array());
            if (read > 0) {
                byte[] data = Arrays.copyOf(buffer.array(), read);
                for (PacketReciver reciver : getPacketListeners())
                    reciver.onDataRecived(data);
            }
            buffer.clear();
//                    System.out.println("Done " + read);
        } catch (IOException ex) {
            Logger.getLogger(AppClient.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

}
