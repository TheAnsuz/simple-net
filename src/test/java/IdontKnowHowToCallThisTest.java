
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.amrv.snet.event.ServerTransferListener;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class IdontKnowHowToCallThisTest {

    private static final List<ServerTransferListener> serverTransferListener = new ArrayList<>();

    public static void addServerTransferListener(ServerTransferListener listener) {
        serverTransferListener.add(listener);
    }

    protected static void dispatchServerTransferEvent(Consumer<? super ServerTransferListener> action) {
        for (int i = 0; i < serverTransferListener.size(); i++)
            action.accept(serverTransferListener.get(i));
    }

    public static void main(String[] args) {

        addServerTransferListener(new ServerTransferListener() {
            @Override
            public boolean onPrepareClientConnect() {
                System.out.println("Prepare client connect");
                return true;
            }

            @Override
            public void onClientConnect() {
                System.out.println("Client connect");
            }

            @Override
            public boolean onPrepareClientKick() {
                System.out.println("Prepare client kick");
                return false;
            }

            @Override
            public void onClientKick() {
                System.out.println("Client kick");
            }
        });

//        boolean accepted = dispatchServerTransferEvent((listener) -> {
//            if (!listener.onPrepareClientKick())
//                return false;
//        });
//
//        System.out.println("> " + accepted);
    }
}
