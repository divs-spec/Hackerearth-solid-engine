// Approach no 1:
import java.util.*;

public class Main {

    // Function to count set bits using Brian Kernighan’s Algorithm
    public static int diversityInChoice(long n) {
        int count = 0;
        while (n > 0) {
            n = n & (n - 1);
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt(); // number of test cases

        while (t-- > 0) {
            long P = sc.nextLong();
            long M = sc.nextLong();
            long X = P ^ M;
            System.out.println(diversityInChoice(X));
        }

        sc.close();
    }
}



// Approach no 2:
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt(); // number of test cases

        while (t-- > 0) {
            long m = sc.nextLong();
            long p = sc.nextLong();

            long q = m ^ p;

            // Count number of 1s in binary representation
            int count = 0;
            while (q > 0) {
                if ((q & 1) == 1) count++;
                q >>= 1;
            }

            System.out.println(count);
        }

        sc.close();
    }
}
