package Semester2_2024.COMP9003.Lecture.Week1;

public class IfElse {
    public static void main(String[] args){
        // indentation has no impact on java
        double x= Double.parseDouble(args[0]);
        // 只把下一行算入body内
    if (x<50)
            System.out.println("F");
        else if (x <65)
        System.out.println("Credit");
        else
            System.out.println("P");
        System.out.println("Finished");

    }
}
