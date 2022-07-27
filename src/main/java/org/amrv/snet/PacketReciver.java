package org.amrv.snet;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public interface PacketReciver {

    void initialize(InputStream input) throws IOException;

    boolean isInitialized();
    
    void closure() throws IOException;

    boolean mustRead() throws IOException;

    AppPacket read() throws IOException;

}
