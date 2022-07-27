package org.amrv.snet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.amrv.snet.event.ServerStateListener;
import org.amrv.snet.event.ServerTransferListener;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public abstract class Server<C extends Client> extends SimpleNet {

    private final List<C> clients = new ArrayList<>();
    private final List<ServerStateListener> serverStateListener = new ArrayList<>();
    private final List<ServerTransferListener> serverTransferListener = new ArrayList<>();
    private final AppPacket packet = new AppPacket();

    public boolean hasClient(C client) {
        return clients.contains(client);
    }

    public boolean hasClient(int index) {
        return clients.get(index) != null;
    }

    public Client[] getClients() {
        return clients.toArray(new Client[0]);
    }

    public C getClient(int index) {
        return clients.get(index);
    }

    protected void dispatchClientEvent(Consumer<? super C> action) {
        for (int i = 0; i < clients.size(); i++)
            action.accept(clients.get(i));
    }

    public void addServerStateListener(ServerStateListener listener) {
        serverStateListener.add(listener);
    }

    public void removeServerStateListener(ServerStateListener listener) {
        serverStateListener.remove(listener);
    }

    public ServerStateListener[] getServerStateListeners() {
        return serverStateListener.toArray(new ServerStateListener[0]);
    }

    protected void dispatchServerStateEvent(Consumer<? super ServerStateListener> action) {
        for (int i = 0; i < serverStateListener.size(); i++)
            action.accept(serverStateListener.get(i));
    }

    public void addServerTransferListener(ServerTransferListener listener) {
        serverTransferListener.add(listener);
    }

    public void removeServerTransferListener(ServerTransferListener listener) {
        serverTransferListener.remove(listener);
    }

    public ServerTransferListener[] getServerTransferListeners() {
        return serverTransferListener.toArray(new ServerTransferListener[0]);
    }

    protected void dispatchServerTransferEvent(Consumer<? super ServerTransferListener> action) {
        for (int i = 0; i < serverTransferListener.size(); i++)
            action.accept(serverTransferListener.get(i));
    }

    public boolean broadcastSilently(byte[] data) {
        return this.broadcastSilently(data, true);
    }

    public boolean broadcastSilently(byte[] data, boolean log) {
        try {
            this.broadcast(data);
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean broadcastSilently(AppPacket packet) {
        return this.broadcastSilently(packet, true);
    }

    public boolean broadcastSilently(AppPacket packet, boolean log) {
        try {
            this.broadcast(packet);
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void broadcast(byte[] data) throws IOException {
        this.broadcast(this.packet.setData(data).setAmount(data.length));
    }

    public void broadcast(AppPacket packet) throws IOException {
        for (int i = 0; i < clients.size(); i++)
            if (hasClient(i))
                this.send(getClient(i), packet);
    }

    public boolean sendSilently(int index, byte[] data) {
        return this.sendSilently(index, data, true);
    }

    public boolean sendSilently(int index, byte[] data, boolean log) {
        try {
            this.send(index, data);
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean sendSilently(int index, AppPacket packet) {
        return this.sendSilently(index, packet, true);
    }

    public boolean sendSilently(int index, AppPacket packet, boolean log) {
        try {
            this.send(index, packet);
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean sendSilently(C client, byte[] data) {
        return sendSilently(client, data, true);
    }

    public boolean sendSilently(C client, byte[] data, boolean log) {
        try {
            this.send(client, data);
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean sendSilently(C client, AppPacket packet) {
        return this.sendSilently(client, packet, true);
    }

    public boolean sendSilently(C client, AppPacket packet, boolean log) {
        try {
            this.send(client, packet);
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void send(int index, byte[] data) throws IOException {
        if (!hasClient(index))
            throw new IOException("There is no client with id " + index);

        send(getClient(index), this.packet.setData(data).setAmount(data.length));
    }

    public void send(int index, AppPacket packet) throws IOException {
        if (!hasClient(index))
            throw new IOException("There is no client with id " + index);

        send(getClient(index), packet);
    }

    public void send(C client, byte[] data) throws IOException {
        send(client, this.packet.setData(data).setAmount(data.length));
    }

    public void send(C client, AppPacket packet) throws IOException {
        dispatchServerTransferEvent((listener) -> {
            listener.onPrepareSend(client, packet);
        });
        sendInternal(client, packet);

        dispatchServerTransferEvent((listener) -> {
            listener.onSend(client, packet);
        });
    }

    protected abstract void sendInternal(C client, AppPacket packet) throws
            IOException;

    public boolean startSilently() {
        return startSilently(true);
    }

    public boolean startSilently(boolean log) {
        try {
            this.start(0, null);
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean startSilently(int port) {
        return startSilently(port, true);
    }

    public boolean startSilently(int port, boolean log) {
        try {
            this.start(port, null);
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean startSilently(int port, InetAddress address) {
        return this.startSilently(port, address, true);
    }

    public boolean startSilently(int port, InetAddress address, boolean log) {
        try {
            this.start(port, address);
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void start() throws IOException {
        this.start(0, null);
    }

    public void start(int port) throws IOException {
        this.start(port, null);
    }

    public void start(int port, InetAddress address) throws IOException {
        dispatchServerStateEvent((listener) -> {
            listener.onPrepareStart(port, address);
        });
        this.startInternal(port, address);
        dispatchServerStateEvent((listener) -> {
            listener.onStart(port, address);
        });
    }

    protected abstract void startInternal(int port, InetAddress address) throws
            IOException;

    public boolean kickSilently(int index) {
        return this.kickSilently(index, true);
    }

    public boolean kickSilently(int index, boolean log) {
        try {
            this.kick(index);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean kickSilently(C client) {
        return this.kickSilently(client, true);
    }

    public boolean kickSilently(C client, boolean log) {
        try {
            this.kick(client);
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void kick(int index) throws IOException {
        if (!hasClient(index))
            throw new IOException("There is no client with id " + index);
        this.kick(getClient(index));
    }

    public void kick(C client) throws IOException {
        dispatchServerTransferEvent((listener) -> {
            listener.onPrepareClientKick(client);
        });

        this.kickInternal(client);

        dispatchServerTransferEvent((listener) -> {
            listener.onClientKick(client);
        });
    }

    protected abstract void kickInternal(C client) throws IOException;

    public boolean shutdownSilently() {
        return shutdownSilently(true);
    }

    public boolean shutdownSilently(boolean log) {
        try {
            this.shutdown();
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void shutdown() throws IOException {
        dispatchServerStateEvent((listener) -> {
            listener.onPrepareShutdown();
        });
        this.shutdownInternal();
        dispatchServerStateEvent((listener) -> {
            listener.onShutdown();
        });
    }

    protected abstract void shutdownInternal() throws IOException;

    @Override
    void updateInternal() throws IOException {
        final C client = reciveClient();
        int index = -1;

        if (client == null)
            return;

        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i) != null)
                continue;

            index = i;
            clients.set(i, client);
        }

        if (index < 0)
            index = clients.size();

        final int finalIndex = index;

        dispatchServerTransferEvent((listener) -> {
            listener.onPrepareClientConnect(client, finalIndex);
        });
        clients.add(client);
        dispatchServerTransferEvent((listener) -> {
            listener.onClientConnect(client, finalIndex);
        });

    }

    protected abstract C reciveClient() throws IOException;

    @Override
    protected void close() throws Throwable {
        this.shutdown();
    }
}
