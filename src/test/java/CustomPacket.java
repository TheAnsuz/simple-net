
import org.amrv.net.deprecated.PacketTransform;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class CustomPacket implements PacketTransform<String,Integer> {

    @Override
    public byte[] disassemble(String data) {
        final int size = data.length();
        return new byte[]{
            (byte) (size >> 24),
            (byte) (size >> 16),
            (byte) (size >> 8),
            (byte) (size /*>> 0*/)};
    }

    @Override
    public Integer assemble(byte[] data) {
        return data[0] << 24 & data[1] << 16 & data[2] << 8 & data[3];
    }

}
