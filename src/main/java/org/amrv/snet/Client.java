package org.amrv.snet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.amrv.snet.event.ClientStateListener;
import org.amrv.snet.event.ClientTransferListener;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public abstract class Client extends SimpleNet {

    private final List<ClientStateListener> clientStateListeners = new ArrayList<>();
    private final List<ClientTransferListener> clientTransferListeners = new ArrayList<>();
    private final AppPacket packet = new AppPacket();
    private PacketReciver packetReciver = new DefaultPacketReciver();
    private PacketSender packetSender = new DefaultPacketSender();

    public PacketReciver getPacketReciver() {
        return packetReciver;
    }

    public synchronized void setPacketReciver(PacketReciver reciver) {
        this.packetReciver = reciver;
    }

    public PacketSender getPacketSender() {
        return packetSender;
    }

    public synchronized void setPacketSender(PacketSender packetSender) {
        this.packetSender = packetSender;
    }

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

    /**
     * Sends the given byte array between sockets.
     * <p>
     * This method will not throw any exceptions and instead will return the
     * result of the operation as true if successful or false otherwise.
     *
     * @param data the byte array to be sent
     * @return true if the operation was succesful, false otherwise
     */
    public boolean sendSilently(byte[] data) {
        this.packet.setData(data).setAmount(data.length);

        try {
            send(this.packet);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DataSender.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean sendSilently(AppPacket packet) {
        try {
            send(packet);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DataSender.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Sends the given byte array between sockets.
     *
     * @param data the byte array to be sent
     * @throws IOException if any error occurs during the process
     */
    public void send(byte[] data) throws IOException {
        this.packet.setData(data).setAmount(data.length);
        send(this.packet);
    }

    public void send(AppPacket packet) throws IOException {
        dispatchClientTransferEvent((listener) -> {
            listener.onSendPrepare(packet);
        });

        try {
            sendInternal(packet);

        } catch (IOException ex) {
            throw ex;

        } finally {
            dispatchClientTransferEvent((listener) -> {
                listener.onSend(packet);
            });
        }
    }

    protected abstract void sendInternal(AppPacket packet) throws IOException;

    /**
     * Tries to connect to the given host, this can be an IP or a DHCP server,
     * this method will check and use the first free port that could be found.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     *
     * @param hostName the IP or a DHCP server
     * @throws UnknownHostException if the supplied hostName is not valid
     * @throws IOException if the connection was unsuccesful
     */
    public void connect(String hostName) throws
            UnknownHostException, IOException {
        connect(InetAddress.getByName(hostName), 0);
    }

    /**
     * Tries to connect to the given host, this can be an IP or a DHCP server,
     * this method will check and use the first free port that could be found.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     * <p>
     * This method will not throw any exceptions and instead will just return
     * the result of the operation as true or false.
     *
     * @param hostName the IP or a DHCP server
     * @return true if the connection was succesful, false otherwise
     */
    public boolean connectSilently(String hostName) {
        try {
            connect(hostName);
            return true;
        } catch (UnknownHostException ex) {
            Logger.getLogger(Connectable.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Connectable.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Tries to connect to the given host, this can be an IP or a DHCP server,
     * the given port will also be used, keep in mind that if the port is
     * already used the connection wont succeed.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     *
     * @param hostName the IP or a DHCP server
     * @param port the port to be used or 0 to let the socket use anyone
     * @throws UnknownHostException if the supplied hostName is not valid
     * @throws IOException if the connection was unsuccesful
     */
    public void connect(String hostName, int port) throws
            UnknownHostException, IOException {
        connect(InetAddress.getByName(hostName), port);
    }

    /**
     * Tries to connect to the given host, this can be an IP or a DHCP server,
     * the given port will also be used, keep in mind that if the port is
     * already used the connection wont succeed.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     * <p>
     * This method will not throw any exceptions and instead will just return
     * the result of the operation as true or false.
     *
     * @param hostName the IP or a DHCP server
     * @param port the port to be used or 0 to let the socket use anyone
     * @return true if the connection was succesful, false otherwise
     */
    public boolean connectSilently(String hostName, int port) {
        try {
            connect(hostName, port);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Connectable.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Tries to connect to the given host, this can be an IP or a DHCP server
     * and the specified ip made of bytes. This method will check and use the
     * first free port that could be found.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     *
     * @param hostName the IP or a DHCP server
     * @param ipSections an array of bytes composing a literal IP
     * @throws UnknownHostException if the supplied hostName is not valid
     * @throws IOException if the connection was unsuccesful
     */
    public void connect(String hostName, byte... ipSections) throws
            UnknownHostException, IOException {
        connect(InetAddress.getByAddress(hostName, ipSections), 0);
    }

    /**
     * Tries to connect to the given host, this can be an IP or a DHCP server
     * and the specified ip made of bytes. This method will check and use the
     * first free port that could be found.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     * <p>
     * This method will not throw any exceptions and instead will just return
     * the result of the operation as true or false.
     *
     * @param hostName the IP or a DHCP server
     * @param ipSections an array of bytes composing a literal IP
     * @return true if the connection was succesful, false otherwise
     */
    public boolean connectSilently(String hostName, byte... ipSections) {
        try {
            connect(hostName, ipSections);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Connectable.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Tries to connect to the given host, this can be an IP or a DHCP server
     * and the specified ip made of bytes, the given port will also be used,
     * keep in mind that if the port is already used the connection wont
     * succeed.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     *
     * @param hostName the IP or a DHCP server
     * @param ipSections an array of bytes composing a literal IP
     * @param port the port to be used or 0 to let the socket use anyone
     * @throws UnknownHostException if the supplied hostName is not valid
     * @throws IOException if the connection was unsuccesful
     */
    public void connect(String hostName, byte[] ipSections, int port) throws
            UnknownHostException, IOException {
        connect(InetAddress.getByAddress(hostName, ipSections), port);
    }

    /**
     * Tries to connect to the given host, this can be an IP or a DHCP server
     * and the specified ip made of bytes, the given port will also be used,
     * keep in mind that if the port is already used the connection wont
     * succeed.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     * <p>
     * This method will not throw any exceptions and instead will just return
     * the result of the operation as true or false.
     *
     * @param hostName the IP or a DHCP server
     * @param ipSections an array of bytes composing a literal IP
     * @param port the port to be used or 0 to let the socket use anyone
     * @return true if the connection was succesful, false otherwise
     */
    public boolean connectSilently(String hostName, byte[] ipSections, int port) {
        try {
            connect(hostName, ipSections, port);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Connectable.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Tries to connect to the given host provided by the array of bytes of its
     * IP, this must be 4 digits (if IPv4) or 6 digits (if IPv6). This method
     * will check and use the first free port that could be found.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     *
     * @param ipSections an array of bytes that composes the IP address
     * @throws UnknownHostException if the supplied hostName is not valid
     * @throws IOException if the connection was unsuccesful
     */
    public void connect(byte... ipSections) throws UnknownHostException,
            IOException {
        connect(InetAddress.getByAddress(ipSections), 0);
    }

    /**
     * Tries to connect to the given host provided by the array of bytes of its
     * IP, this must be 4 digits (if IPv4) or 6 digits (if IPv6). T his method
     * will check and use the first free port that could be found.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     * <p>
     * This method will not throw any exceptions and instead will just return
     * the result of the operation as true or false.
     *
     * @param ipSections an array of bytes that composes the IP address
     * @return true if the connection was succesful, false otherwise
     */
    public boolean connectSilently(byte... ipSections) {
        try {
            connect(ipSections);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Connectable.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Tries to connect to the given host provided by the array of bytes of its
     * IP, this must be 4 digits (if IPv4) or 6 digits (if IPv6), the given port
     * will also be used, keep in mind that if the port is already used the
     * connection wont succeed.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     *
     * @param ipSections an array of bytes that composes the IP address
     * @param port the port to be used or 0 to let the socket use anyone
     * @throws UnknownHostException if the supplied hostName is not valid
     * @throws IOException if the connection was unsuccesful
     */
    public void connect(byte[] ipSections, int port) throws
            UnknownHostException, IOException {
        connect(InetAddress.getByAddress(ipSections), port);
    }

    /**
     * Tries to connect to the given host provided by the array of bytes of its
     * IP, this must be 4 digits (if IPv4) or 6 digits (if IPv6), the given port
     * will also be used, keep in mind that if the port is already used the
     * connection wont succeed.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     * <p>
     * This method will not throw any exceptions and instead will just return
     * the result of the operation as true or false.
     *
     * @param ipSections an array of bytes that composes the IP address
     * @param port the port to be used or 0 to let the socket use anyone
     * @return true if the connection was succesful, false otherwise
     */
    public boolean connectSilently(byte[] ipSections, int port) {
        try {
            connect(ipSections, port);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Connectable.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Tries to connect to the given {@code InetAddress}. This method will check
     * and use the first free port that could be found.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     *
     * @param address the {@code InetAddress} to be used
     * @throws IOException if the connection was unsuccesful
     */
    public void connect(InetAddress address) throws IOException {
        connect(address, 0);
    }

    /**
     * Tries to connect to the given {@code InetAddress}. This method will check
     * and use the first free port that could be found.
     * <p>
     * This method will not throw any exceptions and instead will just return
     * the result of the operation as true or false.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     *
     * @see java.net.InetAddress
     * @param address the {@code InetAddress} to be used
     * @return true if the connection was succesful, false otherwise
     */
    public boolean connectSilently(InetAddress address) {
        try {
            connect(address);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Connectable.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Tries to connect to the given {@code InetAddress} using the specified
     * port, keep in mind that if a port is already being used the connection
     * wont succeed.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     *
     * @see java.net.InetAddress
     * @param address the {@code InetAddress} to be used
     * @param port the port to be used or 0 to let the socket use anyone
     * @throws IOException if the connection was unsuccesful
     */
    public void connect(InetAddress address, int port) throws IOException {
        dispatchClientStateEvent((listener) -> {
            listener.onPrepareConnect(address, port);
        });

        try {
            connectInternal(address, port);

        } catch (IOException ex) {
            throw ex;

        } finally {
            dispatchClientStateEvent((listener) -> {
                listener.onConnect(address, port);
            });
        }
    }

    protected abstract void connectInternal(InetAddress address, int port) throws
            IOException;

    /**
     * Tries to connect to the given {@code InetAddress} using the specified
     * port, keep in mind that if a port is already being used the connection
     * wont succeed.
     * <p>
     * Using this method will cause the current thread to block until the
     * connection is made or any exception is thrown.
     * <p>
     * This method will not throw any exceptions and instead will just return
     * the result of the operation as true or false.
     *
     * @see java.net.InetAddress
     * @param address the {@code InetAddress} to be used
     * @param port the port to be used or 0 to let the socket use anyone
     * @return true if the connection was succesful, false otherwise
     */
    public boolean connectSilently(InetAddress address, int port) {
        try {
            connect(address, port);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Connectable.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Tries to disconnect the socket so no more data will be transfered between
     * client and server.
     * <p>
     * Using this method will cause the current thread to block until the
     * disconnection is succesful or any exception is thrown.
     * <p>
     * This method will not throw any exceptions and instead will just return
     * the result of the operation as true or false.
     *
     * @return true if the disconnection was succesful, false otherwise
     */
    public boolean disconnectSilently() {
        try {
            disconnect();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Connectable.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Tries to disconnect the socket so no more data will be transfered between
     * client and server.
     * <p>
     * Using this method will cause the current thread to block until the
     * disconnection is succesful or any exception is thrown.
     *
     * @throws IOException if any error occurs during the disconnection process
     */
    public void disconnect() throws IOException {
        dispatchClientStateEvent((listener) -> {
            listener.onPrepareDisconnect();
        });
        disconnectInternal();
        dispatchClientStateEvent((listener) -> {
            listener.onDisconnect();
        });
    }

    protected abstract void disconnectInternal() throws IOException;

    /**
     * Tries to connect using the last given parameters or using default values
     * if not provided.
     *
     * @throws IOException if any error occurs
     */
    @Deprecated
    public void reconnect() throws IOException {
        throw new UnsupportedOperationException("This method is no longer supported");
    }

    /**
     * Tries to connect using the last given parameters or using default values
     * if not provided.
     * <p>
     * This method will not throw any exceptions and instead will just return
     * the result of the operation as true or false.
     *
     * @param log true if the error message should be displayed anyways, false
     * to not send any log message
     * @return true if the connection was succesful, false otherwise
     */
    @Deprecated
    public boolean reconnectSilently(boolean log) {
        try {
            reconnect();
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(Connectable.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    protected void close() throws Throwable {
        disconnect();
    } 

    @Override
    void updateInternal() throws IOException {
        final AppPacket data = readPacket();
        if (data == null)
            return;
        dispatchClientTransferEvent((listener) -> {
            listener.onRecive(data);
        });
    }

    protected abstract AppPacket readPacket() throws IOException;

}
