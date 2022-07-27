package org.amrv.snet.event;

import java.net.InetAddress;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public interface ServerStateListener {

    void onPrepareStart(final int port, final InetAddress address);

    void onStart(final int port, final InetAddress address);

    void onPrepareShutdown();

    void onShutdown();

}
