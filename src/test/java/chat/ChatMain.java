package chat;

import java.io.IOException;
import org.amrv.net.deprecated.PacketString;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class ChatMain {

    protected static final PacketString FORMER = new PacketString();

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();
        ChatUser user = new ChatUser();
        user.send(FORMER.disassemble("Hola que tal estas"));
        server.recive();
    }

}
