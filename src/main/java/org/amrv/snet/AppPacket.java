package org.amrv.snet;

import java.util.Arrays;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class AppPacket {

    private byte[] data;
    private int amount;

    public AppPacket setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public AppPacket setData(byte[] data) {
        this.data = data;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public byte[] getData() {
        return data;
    }

    public byte[] getReadData() {
        return Arrays.copyOf(data, amount);
    }

}
