import java.util.*;

public class HelloWorld{
    public static void main(String[] args){
        System.out.println("Hello world!");

        // 创建 Scanner 对象，用于读取用户输入
        Scanner scanner = new Scanner(System.in);

        // 读取整数
        System.out.print("Enter an integer: ");
        int intValue = scanner.nextInt();
        System.out.println("You entered: " + intValue);

        // 读取浮点数
        System.out.print("Enter a float: ");
        float floatValue = scanner.nextFloat();
        System.out.println("You entered: " + floatValue);

        // 清空缓冲区
        scanner.nextLine(); // 清除输入缓冲区中的换行符

        // 读取字符串
        System.out.print("Enter a string: ");
        String stringValue = scanner.nextLine();
        System.out.println("You entered: " + stringValue);

        // 关闭 Scanner
        scanner.close();

    }
}