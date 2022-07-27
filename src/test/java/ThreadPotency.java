
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
class A extends Thread {

    public void run() {
//        System.out.println("**************started***************");
        for (double i = 0.0; i < 500000000000000000.0; i++) {
            try {
                //            System.gc();
//            System.out.println(Thread.currentThread().getName());

                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(A.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out
                .println("************************finished********************************");
    }
}

public class ThreadPotency {

    public static void main(String[] args) {
        for (double j = 0.0; j < 50000000000.0; j++) {
            A a = new A();
            System.out.println("Thread > " + j);
            a.start();
        }
    }
}
