package org.amrv.net.deprecated;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class PacketInteger implements Packet<Integer> {

    @Override
    public Integer assemble(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24)
                | ((bytes[1] & 0xFF) << 16)
                | ((bytes[2] & 0xFF) << 8)
                | ((bytes[3] & 0xFF));
    }

    @Override
    public byte[] disassemble(Integer data) {
        return new byte[]{
            (byte) (data >> 24),
            (byte) (data >> 16),
            (byte) (data >> 8),
            data.byteValue()
        };
    }

}
