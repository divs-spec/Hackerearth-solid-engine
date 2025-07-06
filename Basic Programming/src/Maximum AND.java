import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int t = sc.nextInt();

        while (t-- > 0) {
            long i = sc.nextLong();
            long j = sc.nextLong();

            if ((j - i) == 1) {
                System.out.println(i & j);
            } else {
                long a = j & (j - 1);
                long b = (j - 1) & (j - 2);
                System.out.println(Math.max(a, b));
            }
        }
    }
}
