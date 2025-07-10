import java.util.*;

public class Main {
    // Function to count number of 1s in binary (Hamming weight)
    public static int countSetBits(int num) {
        return Integer.bitCount(num); // uses built-in bit count
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // number of test cases

        for (int t = 0; t < n; t++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            int[] d = new int[a];
            for (int i = 0; i < a; i++) {
                int val = sc.nextInt();
                d[i] = countSetBits(val);
            }

            Arrays.sort(d); // sort in ascending order

            int sum = 0;
            for (int i = a - 1; i >= a - b; i--) {
                sum += d[i]; // sum top b values
            }

            System.out.println(sum);
        }

        sc.close();
    }
}
