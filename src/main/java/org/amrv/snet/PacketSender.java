package org.amrv.snet;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public interface PacketSender {

    void initialize(OutputStream output) throws IOException;

    boolean isInitialized();
    
    void closure() throws IOException;
    
    default boolean mustRead() throws IOException {
        return true;
    }

    void send(AppPacket data) throws IOException;

}
