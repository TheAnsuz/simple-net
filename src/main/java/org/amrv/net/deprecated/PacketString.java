package org.amrv.net.deprecated;

import java.nio.charset.Charset;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class PacketString implements Packet<CharSequence> {

    @Override
    public String assemble(byte[] data) {
        return new String(data, Charset.forName("UTF-8"));
    }

    @Override
    public byte[] disassemble(CharSequence object) {
        return object.toString().getBytes(Charset.forName("UTF-8"));
    }

}
