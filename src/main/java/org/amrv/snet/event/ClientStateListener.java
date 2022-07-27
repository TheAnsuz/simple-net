package org.amrv.snet.event;

import java.net.InetAddress;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public interface ClientStateListener {

    boolean onPrepareConnect(final InetAddress address, final int port);

    void onConnect(final InetAddress address, final int port);

    boolean onPrepareDisconnect();

    void onDisconnect();

}
