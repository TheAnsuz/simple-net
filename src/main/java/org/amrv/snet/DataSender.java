package org.amrv.snet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public interface DataSender {

    /**
     * Sends the given byte array between sockets.
     * <p>
     * This method will not throw any exceptions and instead will return the
     * result of the operation as true if successful or false otherwise.
     *
     * @param data the byte array to be sent
     * @return true if the operation was succesful, false otherwise
     */
    public default boolean sendSilently(byte[] data) {
        try {
            send(data);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DataSender.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public default boolean sendSilently(AppPacket packet) {
        try {
            send(packet);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DataSender.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Sends the given byte array between sockets.
     *
     * @param data the byte array to be sent
     * @throws IOException if any error occurs during the process
     */
    public default void send(byte[] data) throws IOException {
        send(new AppPacket().setData(data).setAmount(data.length));
    }

    public void send(AppPacket packet) throws IOException;
}
