import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        SayHello hi = ()-> {
            System.out.println("Hello!");
            dosomething d = ()->System.out.println("hey");
            d.hey();
            SayHello hi2 = ()->System.out.println("Hello2!");
            hi2.howAreYou();
        };
        hi.hello();

        String[] x = {"123"};
        ArrayList<String> Y = new ArrayList<>();
        Y.add("456");
        System.out.println(x[0]);
        System.out.println(Y.get(0));

        boolean b = false;
        if (b) {
            System.out.println("This will be printed.");
        } else {
            System.out.println("x is false.");
        }

    }

}
interface SayHello {
    public default void howAreYou(){
        System.out.println("How are you today?");
    }
    public void hello();
}

interface dosomething {
    public void hey();
}
