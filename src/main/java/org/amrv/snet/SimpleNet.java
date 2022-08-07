package org.amrv.snet;

import java.awt.EventQueue;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public abstract class SimpleNet {

    protected static final float MILIS = 1000f;

    private volatile int tps = 20;
    private final Thread internalThread;

    /**
     * Creates the object using the default tickrate (20 TPS).
     */
    public SimpleNet() {
        this(20);
    }

    /**
     * Creates the object using an specified tickrate.
     *
     * @param tps the tickrate at wich the object will recive updates
     */
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

//                    try {
                    updateInternal();
//                    } catch (IOException ex) {
//                        Logger.getLogger(Client.class.getName())
//                                .log(Level.SEVERE, null, ex);
//                    }
                    delta--;
                }
            }
        });
        EventQueue.invokeLater(() -> {
            internalThread.start();
        });
    }

    /**
     * Gets the tickrate at wich the object is updating.
     *
     * @return the tickrate
     */
    public int getTPS() {
        return tps;
    }

    /**
     * Sets the tickrate at wich the object should recive updates, changing the
     * tickrate is thread safe however it may result to unexpected behavior if
     * not controlled correctly.
     *
     * @param tps the new tickrate that will be set in the next update
     */
    public synchronized void setTPS(int tps) {
        this.tps = tps;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.internalThread.join();
    }

    /**
     * The internal update of the object, the implementation must use it to
     * update all the data and functions here as it will run at the specified
     * tickrate but wont handle any errors itself. This method used to throw an
     * exception however now exceptions should be managed by the subclass.
     *
     */
    abstract void updateInternal();
}
