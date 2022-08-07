package org.amrv.snet.event;

import java.util.EventListener;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public interface ClientConnectListener extends EventListener {

    void onConnectPrepare(final ClientConnectPrepareEvent event);

    void onConnectComplete(final ClientConnectCompleteEvent event);

    void onConnectRefuse(final ClientConnectRefuseEvent event);

    void onConnectFailure(final ClientConnectFailureEvent event);

    void onConnectTimeout(final ClientConnectTimeoutEvent event);
    
    void onDisconnect(final ClientDisconnectEvent event);

}
