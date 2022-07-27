package org.amrv.net;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.amrv.snet.event.ClientStateListener;
import org.amrv.snet.event.ClientTransferListener;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
abstract class AppClientDispatcher extends AppThread {

    private final List<ClientStateListener> clientStateListeners = new ArrayList<>();
    private final List<ClientTransferListener> clientTransferListeners = new ArrayList<>();

    public void addClientStateListener(ClientStateListener listener) {
        clientStateListeners.add(listener);
    }

    public void removeClientStateListener(ClientStateListener listener) {
        clientStateListeners.remove(listener);
    }

    public ClientStateListener[] getClientStateListeners() {
        return clientStateListeners.toArray(new ClientStateListener[0]);
    }

    protected void dispatchClientStateEvent(Consumer<? super ClientStateListener> action) {
        for (int i = 0; i < clientStateListeners.size(); i++)
            action.accept(clientStateListeners.get(i));
    }
    
    public void addClientTransferListener(ClientTransferListener listener) {
        clientTransferListeners.add(listener);
    }

    public void removeClientTransferListener(ClientTransferListener listener) {
        clientTransferListeners.remove(listener);
    }

    public ClientTransferListener[] getClientTransferListeners() {
        return clientTransferListeners.toArray(new ClientTransferListener[0]);
    }

    protected void dispatchClientTransferEvent(Consumer<? super ClientTransferListener> action) {
        for (int i = 0; i < clientTransferListeners.size(); i++)
            action.accept(clientTransferListeners.get(i));
    }

}
