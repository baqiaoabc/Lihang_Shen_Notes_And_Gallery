import java.util.Arrays;

public class arrays{
    public static void main(String[] args){
        int[][] x = new int[2][3];
        for(int i = 0; i<x.length;i++){
            for(int j = 0; j<x[i].length;j++) {
                System.out.print(x[i][j] + "");
            }
            System.out.println();
        }
    }
}