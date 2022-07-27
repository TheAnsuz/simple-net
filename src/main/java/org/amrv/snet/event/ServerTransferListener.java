package org.amrv.snet.event;

import org.amrv.snet.AppPacket;
import org.amrv.snet.Client;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public interface ServerTransferListener {

    boolean onPrepareClientConnect(final Client client, final int index);

    void onClientConnect(final Client client, final int index);

    boolean onPrepareClientKick(final Client client);

    void onClientKick(final Client client);

    void onPrepareSend(final Client client, AppPacket packet);

    void onSend(final Client client, AppPacket packet);

    void onRecive(final Client client, AppPacket packet);

}
