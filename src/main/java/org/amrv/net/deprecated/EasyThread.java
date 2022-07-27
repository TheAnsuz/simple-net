package org.amrv.net.deprecated;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class EasyThread extends Thread {

    private static final long MILIS = 1000;

    private boolean running = false;
    private final Runnable runnable;
    private int ups;

    public EasyThread(Runnable runnable) {
        this(runnable, false, 20);
    }

    public EasyThread(Runnable runnable, int ups) {
        this(runnable, false, ups);
    }

    public EasyThread(Runnable runnable, boolean autoStart) {
        this(runnable, autoStart, 20);
    }

    public EasyThread(Runnable runnable, boolean autoStart, int ups) {
        this.runnable = runnable;
        this.ups = ups;
        EventQueue.invokeLater(() -> start());
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getUps() {
        return ups;
    }

    @Override
    public void start() {
        if (running)
            return;

        running = true;
        super.start();
    }

    public synchronized void end() {
        if (!running)
            return;

        end0();
    }

    private synchronized void end0() {
        try {
            this.join();
            running = false;
        } catch (InterruptedException ex) {
            Logger.getLogger(EasyThread.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        long last = System.currentTimeMillis();
        double delta = 0d;
        int tick = 0;
        while (running) {
            long now = System.currentTimeMillis();
            delta += (now - last) / (double) (MILIS / ups);
            last = now;

            if (now % MILIS == 0)
                tick = 0;

            if (delta >= 1) {
                tick++;
                runnable.run();
                delta--;
//                System.out.println("Tick: " + tick);

            }

        }

        end0();
    }

}
