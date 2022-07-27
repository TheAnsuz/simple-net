package org.amrv.snet;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public abstract class SimpleNet {

    protected static final float MILIS = 1000f;

    private volatile int tps = 20;
    private boolean paused = false;
    private final Thread internalThread;

    /**
     * Creates the object using the default tickrate, packet reciver and packet
     * sender.
     */
    public SimpleNet() {
        this(20);
    }

    public SimpleNet(int tps) {
        this.tps = tps;
        internalThread = new Thread(() -> {
            long last = System.currentTimeMillis();
            long now;
            float delta = 0f;

            while (true) {
                now = System.currentTimeMillis();
                delta += (now - last) / (MILIS / tps);
                last = now;

                if (delta >= 1) {

                    if (paused)
                        continue;

                    try {
                        updateInternal();
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                    delta--;
                }
            }
        });
        EventQueue.invokeLater(() -> {
            internalThread.start();
        });
    }

    /**
     * Sets the paused state of the object's internal thread so it will no
     * longer be updated automatically using the tickrate.
     *
     * @param paused the paused state of the object.
     */
    public synchronized void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public int getTPS() {
        return tps;
    }

    public synchronized void setTPS(int tps) {
        this.tps = tps;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.internalThread.join();
        this.close();
    }

    protected abstract void close() throws Throwable;

    abstract void updateInternal() throws IOException;
}
