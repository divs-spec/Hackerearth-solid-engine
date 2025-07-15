import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();  // Number of test cases
        while (T-- > 0) {
            int N = sc.nextInt();
            long sum = 0, sum1 = 0;

            for (int i = 0; i < N; i++) {
                sum += sc.nextLong();
            }

            for (int i = 0; i < N; i++) {
                sum1 += sc.nextLong();
            }

            if (sum == sum1) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }

        sc.close();
    }
}
