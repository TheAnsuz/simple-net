
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class ArrayCreationPolicy {

    private static byte[][] buffer = new byte[10][1024];
    private static final Random rnd = new Random();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < buffer.length; i++)
                rnd.nextBytes(buffer[i]);
            final int amount = rnd.nextInt(buffer.length);
            Arrays.copyOf(buffer[amount], rnd.nextInt(buffer[amount].length));
        }
    }
}
