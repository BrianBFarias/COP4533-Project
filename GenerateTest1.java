import java.util.Random;
import java.util.Scanner; 

public class GenerateTest1 {
    public static void main (String[] args){

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of paintings (n): ");
        int n = scanner.nextInt(); 
        final int W = 500;

        int[] h = new int[n];
        int[] w = new int[n];

        for (int i = 0; i < n; i++){
            h[i] = n-i;
        }

        Random rand = new Random(); 

        for(int i = 0; i < n; i++){
            w[i] = rand.nextInt(W) + 1;
        }

        System.out.println("Heights");
        for(int height: h){
            System.out.print(height + " ");
        }

        System.out.println();

        System.out.println("Widths");
        for(int width: w){
            System.out.print(width + " ");
        }


    }
}
