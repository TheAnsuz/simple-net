package chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class ChatUser {

    private Socket client;
    private InputStream input;
    private OutputStream output;
    private ByteBuffer buffer = ByteBuffer.allocate(4098);

    public ChatUser() throws IOException {
        this(new Socket("192.168.1.45", 42069));
    }

    public ChatUser(Socket socket) throws IOException {
        client = socket;
        input = client.getInputStream();
        output = client.getOutputStream();
    }

    public void send(byte[] data) throws IOException {
        output.write(data);
    }

    public byte[] read() throws IOException {
        input.read(buffer.array());
        byte[] data = Arrays.copyOf(buffer.array(), buffer.limit());
        buffer.clear();
        return data;
    }

}
