package org.amrv.net;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public interface DataSender {

    void send(byte[] data) throws IOException;

    default boolean sendSilently(byte[] data) {
        try {
            send(data);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DataSender.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
