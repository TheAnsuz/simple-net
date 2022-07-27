
/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class OverrideTest {

    public static void main(String[] args) {
        Derived obj = new Derived();
        obj.fun();
    }

}

class Base {

    private final void fun() {
        System.out.println("Base fun");
    }
}

class Derived extends Base {

    public void fun() { // overrides the Base's fun()
        System.out.println("Derived fun");
    }

}
