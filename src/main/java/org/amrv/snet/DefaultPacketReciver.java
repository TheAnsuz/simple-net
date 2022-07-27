package org.amrv.snet;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class DefaultPacketReciver implements PacketReciver {

    private InputStream stream;
    private final AppPacket data = new AppPacket();

    @Override
    public void initialize(InputStream input) {
        stream = input;
        data.setData(new byte[256]);
    }

    @Override
    public void closure() throws IOException {
        stream.close();

    }

    @Override
    public boolean mustRead() throws IOException {
        return stream.available() > 0;
    }

    @Override
    public AppPacket read() throws IOException {
        data.setAmount(stream.read(data.getData()));

        return data;
    }

    @Override
    public boolean isInitialized() {
        return stream != null;
    }

}
