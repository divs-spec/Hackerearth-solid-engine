import java.util.*;

public class Main {
    static long k;

    // Parent of node x in a k-ary tree
    static long parent(long x) {
        return x / k;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        k = sc.nextLong();
        long q = sc.nextLong();

        // Edge weight map
        Map<Long, Long> inw = new HashMap<>();

        while (q-- > 0) {
            int op = sc.nextInt();
            if (op == 1) {
                long a = sc.nextLong();
                long b = sc.nextLong();

                if (a == b) {
                    System.out.println(0);
                    continue;
                }

                long ans = 0;

                while (a != b) {
                    if (a > b) {
                        ans += inw.getOrDefault(a, 0L) + 1;
                        a = parent(a);
                    } else {
                        ans += inw.getOrDefault(b, 0L) + 1;
                        b = parent(b);
                    }
                }

                System.out.println(ans);

            } else if (op == 2) {
                long a = sc.nextLong();
                long b = sc.nextLong();
                long w = sc.nextLong();

                if (a == b) continue;

                while (a != b) {
                    if (a > b) {
                        inw.put(a, inw.getOrDefault(a, 0L) + w);
                        a = parent(a);
                    } else {
                        inw.put(b, inw.getOrDefault(b, 0L) + w);
                        b = parent(b);
                    }
                }
            }
        }

        sc.close();
    }
}
