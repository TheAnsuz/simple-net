package org.amrv.net;

import org.amrv.snet.AppPacket;
import org.amrv.snet.PacketSender;
import org.amrv.snet.DefaultPacketReciver;
import org.amrv.snet.PacketReciver;
import org.amrv.snet.DefaultPacketSender;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.amrv.snet.event.ClientStateListener;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class AppClient extends AppClientDispatcher implements AppConnectable, DataSender {

    private Socket socket;
    private PacketReciver packetReciver;
    private PacketSender packetSender;
    private InetAddress lastAddress;
    private int lastPort = 0;

    public AppClient() {
        super();
        packetReciver = new DefaultPacketReciver();
        packetSender = new DefaultPacketSender();
        this.socket = null;
    }

    public PacketReciver getPacketReciver() {
        return packetReciver;
    }

    public void setPacketReciver(PacketReciver packetReciver) {
        this.packetReciver = packetReciver;
    }

    public PacketSender getPacketSender() {
        return packetSender;
    }

    public void setPacketSender(PacketSender packetSender) {
        this.packetSender = packetSender;
    }

    @Override
    public void connect(InetAddress address, int port) throws IOException {
        if (socket != null)
            this.disconnect();

        super.dispatchClientStateEvent(listener -> {
            listener.onPrepareConnect(address, port);
        });

        try {
            this.socket = new Socket(address, port);
            this.lastAddress = address;
            this.lastPort = port;
            packetSender.initialize(this.socket.getOutputStream());
            packetReciver.initialize(this.socket.getInputStream());
        } catch (IOException ex) {
            super.dispatchClientStateEvent(listener -> {
                listener
                        .onFailure(ClientStateListener.FailureReason.UNKNOWN, address, port);
            });
            throw ex;
        }
        Server

        super.dispatchClientStateEvent(listener -> {
            listener.onConnect(address, port);
        });
    }

    @Override
    public void disconnect() throws IOException {
        packetSender.closure();
        packetReciver.closure();
        this.socket.close();
        this.socket = null;
    }

    @Override
    public void reconnect() throws IOException {
        connect(lastAddress, lastPort);
    }

    @Override
    public void send(AppPacket data) throws IOException {
        super.dispatchClientTransferEvent(listener -> {listener.onSendPrepare(data);});
        packetSender.send(data);

        super.dispatchClientTransferEvent(listener -> {listener.onSend(data);});
    }

    @Override
    protected void execute() {
        if (socket == null)
            reconnectSilently(false);

        try {

            while (packetReciver.mustRead()) {
                AppPacket data = packetReciver.read();

                super.dispatchDataReciveEvent(data);

            }
        } catch (IOException ex) {
            Logger.getLogger(AppClient.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        this.disconnect();
        super.finalize();
    }

}
