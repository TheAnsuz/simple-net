package newchat;

import java.net.InetAddress;
import org.amrv.snet.Client;
import org.amrv.snet.TCPServer;
import org.amrv.snet.event.ServerStateListener;
import org.amrv.snet.event.ServerTransferListener;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class Server {

    public static void main(String[] args) {
        TCPServer server = new TCPServer();
        Screen chat = new Screen("Server");
        server.addServerStateListener(new ServerStateListener() {
            @Override
            public void onPrepareStart(int port, InetAddress address) {
                chat
                        .log(Screen.Action.INFO, "Starting server on " + port + "...");
            }

            @Override
            public void onStart(int port, InetAddress address) {
                chat.log(Screen.Action.INFO, "Server started on " + port);
            }

            @Override
            public void onPrepareShutdown() {

            }

            @Override
            public void onShutdown() {
                chat.log(Screen.Action.INFO, "Server closed");
            }
        });
        server.addServerTransferListener(new ServerTransferListener() {
            @Override
            public boolean onPrepareClientConnect(Client client, int index) {
                chat
                        .log(Screen.Action.INFO, "Client " + index + " is connecting");
                return true;
            }

            @Override
            public void onClientConnect(Client client, int index) {
                chat
                        .log(Screen.Action.INFO, "Client " + index + " has connected succesfuly");
            }

            @Override
            public boolean onPrepareClientKick(Client client) {
//                chat.log(Screen.Action.INFO, "Client " + client.toString() + " has been kicked");
                return true;
            }

            @Override
            public void onClientKick(Client client) {
                chat
                        .log(Screen.Action.INFO, "Client " + client.toString() + " has been kicked");
            }
        });
        chat.onSendEvents.add((string) -> {
            server.broadcastSilently(string.getBytes());
        });
        server.startSilently(42069);
    }

}
