
import java.util.Arrays;
import org.amrv.net.deprecated.AppClient;
import org.amrv.net.deprecated.AppServer;
import org.amrv.net.deprecated.PacketFloat;
import org.amrv.net.deprecated.PacketInteger;
import org.amrv.net.deprecated.PacketString;
import org.amrv.snet.AppPacket;
import org.amrv.snet.event.ClientTransferListener;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class PacketCreationTest {

    private static final String STR = "Hola que tal estas";
    private static final int INT = 1209318273;
    private static final float FLOAT = 12345678912.1234567890f;

    public static void main(String[] args) {
        AppServer server = new AppServer(42069);
        AppClient client = new AppClient("192.168.1.45", 42069);

        PacketString packetstr = new PacketString();
        PacketInteger packetint = new PacketInteger();
        PacketFloat packetfloat = new PacketFloat();

        System.out.println(Arrays.toString(packetstr.disassemble(STR)));
        System.out.println(packetstr.assemble(packetstr.disassemble(STR)));

        System.out.println(Arrays.toString(packetint.disassemble(INT)));
        System.out.println(packetint.assemble(packetint.disassemble(INT)));

        // The problem is because of how floats are made so as the integer part 
        // increases in size the decimals are reduced 
        // until the float converts to a exponent number
        System.out.println("raw float: " + FLOAT);
        System.out.println(Arrays.toString(packetfloat.disassemble(FLOAT)));
        System.out.println(packetfloat.assemble(packetfloat.disassemble(FLOAT)));

    }

}
