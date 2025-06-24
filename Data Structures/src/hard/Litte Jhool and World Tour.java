import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long t = sc.nextLong();

        while (t-- > 0) {
            long n = sc.nextLong();
            long q = sc.nextLong();
            boolean flag = false;

            for (long i = 0; i < q; i++) {
                long x = sc.nextLong();
                long y = sc.nextLong();

                if (x == y) {
                    flag = true;
                    break;
                } else if (x > n && x > y) {
                    if ((x - y) > n) {
                        flag = true;
                        break;
                    }
                }

                // Optional additional case from commented part in C++
                /*
                else if (y >= n && y > x) {
                    if ((y - x) > n) {
                        flag = true;
                        break;
                    }
                }
                */
            }

            if (!flag) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }

        sc.close();
    }
}
