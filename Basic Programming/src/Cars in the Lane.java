import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();  // Number of test cases

        while (t-- > 0) {
            long n = sc.nextLong();

            // Count number of set bits in n
            int count = Long.bitCount(n);

            // Output 2^count
            System.out.println(1L << count);
        }

        sc.close();
    }
}
