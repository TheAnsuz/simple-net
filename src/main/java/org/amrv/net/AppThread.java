package org.amrv.net;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
abstract class AppThread {

    private static final int MILIS = 1000;

    public AppThread() {
        EventQueue.invokeLater(() -> this.start());
    }

    private final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            long last = System.currentTimeMillis();
            long now = last;
            float delta = 0;
            while (running) {
                delta += (now - last) / (float) (MILIS / ups);
                last = now;

                if (delta > 1) {

                    if (paused)
                        continue;

                    execute();
                    delta--;
                }
            }
        }

    });
    private int ups = 20;
    private boolean paused = false;
    private boolean running = false;

    /**
     * Stablish the speed at wich the internal thread will check if there was an
     * update on the connection state.
     *
     * @param tps the goal ticks per second
     */
    public synchronized void setTickRate(int tps) {
        this.ups = tps;
    }

    /**
     * Retrieves the current ticks per second or updates that the connection
     * tries to achieve.
     *
     * @return the goal ticks per second
     */
    public int getTickRate() {
        return this.ups;
    }

    /**
     * Sets the paused state of the connection, while the connetion is paused it
     * wont be able to send or recive any data, however it could be disconnected
     * or reconnected while is paused.
     *
     * @param paused the paused state of the connection
     */
    public synchronized void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Retrieves the current paused state of the thread at the current execution
     * time.
     *
     * @return true if the thread is paused, false if it isnt
     */
    public boolean isPaused() {
        return this.paused;
    }

    protected synchronized void start() {
        if (running)
            return;

        running = true;
        thread.start();
    }

    @Override
    protected void finalize() throws Throwable {
        stop();
        super.finalize();
    }

    protected synchronized void stop() {
        if (!running)
            return;

        try {
            running = false;
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(AppThread.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    protected abstract void execute();
}
