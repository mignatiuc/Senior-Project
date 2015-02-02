import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrimeFactorization {

   public static List<Integer> primeFactors(int n) {
   
      int factor = n;
      List<Integer> primeFactors = new ArrayList<Integer>();
      
      for (int i = 2; i<(factor+1); i++) {
         while (factor % i == 0) {
            primeFactors.add(i);
            factor = factor/i;
         }
      }
      return primeFactors;
   }
   public static void main(String[] args) {
      Scanner keyboard = new Scanner(System.in);
      
      System.out.println("Enter a number");
      int number = keyboard.nextInt();
      System.out.println(primeFactors(number));
   }
}
