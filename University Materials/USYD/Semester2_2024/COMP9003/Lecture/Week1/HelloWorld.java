package Semester2_2024.COMP9003.Lecture.Week1;

import java.util.Scanner;

public class HelloWorld{
    // 1. only one main method
    // 2. 需要javac先编译，再运行；但是idea帮我们把这两步绑定了
        // javac 2024 S2.COMP9003.Lecture.Week 1.HelloWorld.java
        // java 2024 S2.COMP9003.Lecture.Week 1.HelloWorld
    //  3. main method signature 是固定的吗？是的，别的都不行比如变成int args;int[]不行
    //     如果换了signature，会报什么错？ cannot find main method
    //  7. 括号中的Stringp[] args是指输入的argument

    // 6. static是什么意思
    public static void main(String[] args){
        System.out.println("Hello world!");

        // 5. we use new to create object
        Scanner keyboard = new Scanner(System.in);
        String s = "this is a string!";
        // 参考 Scanner Javadoc
        // next 是下一个，有可能是同行的下一个
        // nextLine 是下一行
        // 重点，这里是non static，所以需要创建object使用
        s = keyboard.next();
        System.out.println(s);

    }
}