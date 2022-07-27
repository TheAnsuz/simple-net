package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.amrv.net.deprecated.EasyThread;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class ChatServer {

    private final ServerSocket server;
    private final HashSet<ChatUser> clients = new HashSet<>();
    private final EasyThread reciver;

    public ChatServer() throws IOException {
        server = new ServerSocket(42069);
        reciver = new EasyThread(() -> {
            try {
                final Socket socket = server.accept();
                System.out.println(socket.getInetAddress().getHostAddress() + " joined");
                clients.add(new ChatUser(socket));
            } catch (IOException ex) {
                Logger.getLogger(ChatServer.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }, false, 20);
    }

    public void send(byte[] data) throws IOException {
        for (ChatUser user : clients)
            user.send(data);
    }

    public void recive() throws IOException {
        for (ChatUser user : clients)
            System.out.println(ChatMain.FORMER.assemble(user.read()));
    }

}
