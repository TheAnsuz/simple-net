package org.amrv.snet;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class DefaultPacketSender implements PacketSender {

    private OutputStream stream;

    @Override
    public void initialize(OutputStream output) throws IOException {
        this.stream = output;
    }

    @Override
    public void closure() throws IOException {
        this.stream.close();
    }

    @Override
    public void send(AppPacket data) throws IOException {
        this.stream.write(data.getData());
    }

    @Override
    public boolean isInitialized() {
        return stream != null;
    }

}
