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
public class AppServerClient extends AppNetUtils {

    private final Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private final ByteBuffer buffer;

    protected AppServerClient(Socket socket) {
        this.socket = socket;
        this.createStreams();
        buffer = ByteBuffer.allocate(4098);
    }

    @Override
    public boolean disconnect(boolean clear) {
        try {
            socket.close();

            if (output != null)
                output.close();

            if (input != null)
                input.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppServerClient.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private boolean createStreams() {
        try {
            if (input == null && canRecive())
                input = new DataInputStream(socket.getInputStream());
            if (output == null && canSend())
                output = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppServerClient.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean connect(InetAddress address, int port) {
        return createStreams();

    }

    @Override
    public boolean isConnected() {
        return socket.isConnected();
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
            Logger.getLogger(AppServerClient.class.getName())
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
