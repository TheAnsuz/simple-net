package newchat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import org.amrv.snet.AppPacket;
import org.amrv.snet.TCPClient;
import org.amrv.snet.event.ClientStateListener;
import org.amrv.snet.event.ClientTransferListener;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class Client {

    public static void main(String[] args) throws UnknownHostException,
            IOException {
        TCPClient client = new TCPClient();
        Screen chat = new Screen("Client");
        client.addClientStateListener(new ClientStateListener() {
            @Override
            public boolean onPrepareConnect(InetAddress address, int port) {
                chat.log(Screen.Action.INFO, "Conectandose a " + address
                        .getHostAddress() + ":" + port + "...");
                return true;
            }

            @Override
            public void onConnect(InetAddress address, int port) {
                chat.log(Screen.Action.INFO, "Conectado a " + address
                        .getHostAddress() + ":" + port + "...");
            }

            @Override
            public boolean onPrepareDisconnect() {
                return true;
            }

            @Override
            public void onDisconnect() {
                chat.log(Screen.Action.INFO, "Te has desconectado");
            }
        });
        client.addClientTransferListener(new ClientTransferListener() {
            @Override
            public boolean onSendPrepare(AppPacket data) {
                return true;
            }

            @Override
            public void onSend(AppPacket data) {
                chat.log(Screen.Action.MESSAGE_SENT, new String(data
                        .getReadData(), Charset.forName("UTF-8")));
            }

            @Override
            public void onRecive(AppPacket data) {
                chat.log(Screen.Action.MESSAGE_RECIVED, new String(data
                        .getReadData(), Charset.forName("UTF-8")));
            }
        });
        chat.onSendEvents.add((string) -> {
            client.sendSilently(string.getBytes());
        });
        client.connect(InetAddress.getLocalHost(), 42069);
    }

}
