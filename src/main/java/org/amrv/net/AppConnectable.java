package org.amrv.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public interface AppConnectable {

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
    default void connect(String hostName) throws
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
    default boolean connectSilently(String hostName) {
        try {
            connect(hostName);
            return true;
        } catch (UnknownHostException ex) {
            Logger.getLogger(AppConnectable.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(AppConnectable.class.getName())
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
    default void connect(String hostName, int port) throws
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
    default boolean connectSilently(String hostName, int port) {
        try {
            connect(hostName, port);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppConnectable.class.getName())
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
    default void connect(String hostName, byte... ipSections) throws
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
    default boolean connectSilently(String hostName, byte... ipSections) {
        try {
            connect(hostName, ipSections);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppConnectable.class.getName())
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
    default void connect(String hostName, byte[] ipSections, int port) throws
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
    default boolean connectSilently(String hostName, byte[] ipSections, int port) {
        try {
            connect(hostName, ipSections, port);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppConnectable.class.getName())
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
    default void connect(byte... ipSections) throws UnknownHostException,
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
    default boolean connectSilently(byte... ipSections) {
        try {
            connect(ipSections);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppConnectable.class.getName())
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
    default void connect(byte[] ipSections, int port) throws
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
    default boolean connectSilently(byte[] ipSections, int port) {
        try {
            connect(ipSections, port);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppConnectable.class.getName())
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
    default void connect(InetAddress address) throws IOException {
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
    default boolean connectSilently(InetAddress address) {
        try {
            connect(address);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppConnectable.class.getName())
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
    void connect(InetAddress address, int port) throws IOException;

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
    default boolean connectSilently(InetAddress address, int port) {
        try {
            connect(address, port);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppConnectable.class.getName())
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
    void disconnect() throws IOException;

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
    @Deprecated
    default boolean disconnectSilently() {
        try {
            disconnect();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AppConnectable.class.getName())
                    .log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Tries to connect using the last given parameters or using default values
     * if not provided.
     *
     * @throws IOException if any error occurs
     */
    @Deprecated
    void reconnect() throws IOException;

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
    default boolean reconnectSilently(boolean log) {
        try {
            reconnect();
            return true;
        } catch (IOException ex) {
            if (log)
                Logger.getLogger(AppConnectable.class.getName())
                        .log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
