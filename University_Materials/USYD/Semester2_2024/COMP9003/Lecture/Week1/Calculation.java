
public class Calculation {
    // 主方法简写是什么
    public static void main(String[] args){
        // 1. 、
        int x = -9;
        double y = 1.0;
        System.out.println(x/2);
        System.out.println(y/2);
        System.out.println(x/y);

        // refer to google "integer javadoc"
        // 重点，这里是static method， we can use it without create object
        int n1 = Integer.parseInt(args[0]);
        int n2 = Integer.parseInt(args[1]);
        System.out.println(n1+n2);
    }
}
