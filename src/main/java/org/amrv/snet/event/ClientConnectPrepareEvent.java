package org.amrv.snet.event;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class ClientConnectPrepareEvent {

    private boolean cancelled = false;

    public ClientConnectPrepareEvent() {
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return this.cancelled; 
    }
}
