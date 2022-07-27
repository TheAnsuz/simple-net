
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;



/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class Accessing {

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(0,100,null);
        System.out.println(socket.getLocalSocketAddress().toString());
        System.out.println(socket.getInetAddress().getCanonicalHostName());
        socket.close();
        System.out.println(InetAddress.getLocalHost());
    }
}
