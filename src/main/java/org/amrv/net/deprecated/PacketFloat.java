package org.amrv.net.deprecated;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class PacketFloat implements Packet<Float> {

    @Override
    public byte[] disassemble(Float data) {
        final int temp = Float.floatToIntBits(data);
        return new byte[]{
            (byte) (temp >> 24),
            (byte) (temp >> 16),
            (byte) (temp >> 8),
            (byte) temp
        };
    }

    @Override
    public Float assemble(byte[] bytes) {
        return Float.intBitsToFloat(
                ((bytes[0] & 0xFF) << 24)
                | ((bytes[1] & 0xFF) << 16)
                | ((bytes[2] & 0xFF) << 8)
                | ((bytes[3] & 0xFF)));
    }

}
