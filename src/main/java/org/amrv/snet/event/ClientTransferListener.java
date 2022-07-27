package org.amrv.snet.event;

import org.amrv.snet.AppPacket;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public interface ClientTransferListener {
    
    boolean onSendPrepare(AppPacket data);
    
    void onSend(AppPacket data);
    
    void onRecive(AppPacket data);
}
