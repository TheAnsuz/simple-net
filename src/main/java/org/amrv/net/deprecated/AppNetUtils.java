package org.amrv.net.deprecated;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public abstract class AppNetUtils implements Runnable {

    private final HashSet<PacketReciver> listeners = new HashSet<>();
    private boolean running = false;
    private final Thread thread;

    public AppNetUtils() {
        System.out.println("Init");
        this.thread = new Thread(this);
        start();
    }

    private boolean start() {
        if (running)
            return false;

        running = true;
        thread.start();
        return true;
    }

    private boolean stop() {
        synchronized (this) {
            if (running)
                return false;
            try {
                running = false;
                thread.join();
                return true;
            } catch (InterruptedException ex) {
                Logger.getLogger(AppClient.class.getName())
                        .log(Level.SEVERE, null, ex);
                return true;
            }
        }
    }

    protected InetAddress secureAddress(String host, byte[] bytes) {
        try {
            return InetAddress.getByAddress(host, bytes);
        } catch (UnknownHostException ex) {
            Logger.getLogger(AppClient.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }
    }

    protected InetSocketAddress secureSocketAddress(String host, byte[] bytes, int port) {
        return new InetSocketAddress(secureAddress(host, bytes), port);
    }

    protected InetSocketAddress secureSocketAddress(InetAddress address, int port) {
        return new InetSocketAddress(address, port);
    }

    public boolean disconnect() {
        return disconnect(false);
    }

    public abstract boolean disconnect(boolean clear);

    public boolean connect(byte[] address) {
        return connect(address, 0);
    }

    public boolean connect(byte[] address, int port) {
        return connect(secureAddress(null, address), port);
    }

    public boolean connect(String address) {
        return connect(address, 0);
    }

    public boolean connect(String address, int port) {
        return connect(secureAddress(address, new byte[]{0, 0, 0, 0}), port);
    }

    public boolean connect(InetAddress address) {
        return connect(address, 0);
    }

    public abstract boolean connect(InetAddress address, int port);

    public abstract boolean isConnected();

    public abstract boolean canSend();

    public abstract boolean canRecive();

    public abstract boolean send(byte[] data);

    public void forEachListener(Consumer<? super PacketReciver> action) {
        listeners.forEach(action);
    }

    public HashSet<PacketReciver> getPacketListeners() {
        return listeners;
    }

    public boolean addPacketListener(PacketReciver reciver) {
        return listeners.add(reciver);
    }

    public boolean removePacketListener(PacketReciver reciver) {
        return listeners.remove(reciver);
    }

    public void clearListeners() {
        listeners.clear();
    }

    @Override
    protected void finalize() throws Throwable {
        disconnect(true);
        stop();
        super.finalize();
    }

    @Override
    public void run() {
        while (running) {
            reciveData();
        }
    }

    protected abstract void reciveData();

}
