import java.io.*;
import java.util.*;

public class Main {
    static final int MOD = 1000000007;

    static String binary(long n) {
        if (n == 0) return "0";
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            sb.append(n % 2);
            n /= 2;
        }
        return sb.reverse().toString();
    }

    static long fact(int n) {
        long res = 1;
        for (int i = 2; i <= n; i++) res *= i;
        return res;
    }

    static long nCr(int n, int r) {
        return fact(n) / (fact(r) * fact(n - r));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            String[] parts = br.readLine().split(" ");
            int n = Integer.parseInt(parts[0]);
            int l = Integer.parseInt(parts[1]);

            int[] a = new int[n];
            String[] tokens = br.readLine().split(" ");
            int m = 0;
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(tokens[i]);
                m = Math.max(m, a[i]);
            }

            int k = binary(m).length();
            int[] b = new int[k];

            for (int i = 0; i < n; i++) {
                int j = 0;
                int h = a[i];
                while (j < k) {
                    b[j] += h % 2;
                    h /= 2;
                    j++;
                }
            }

            int p = 1;
            Map<Integer, Integer> tp = new TreeMap<>(Collections.reverseOrder());
            int count = 0;
            for (int i = 0; i < k; i++) {
                b[i] *= p;
                p *= 2;
                if (b[i] > 0) count++;
                tp.put(b[i], tp.getOrDefault(b[i], 0) + 1);
            }

            long ans = 1;
            if (l == count) {
                ans = 1;
            } else if (l > count) {
                ans = -1;
            } else {
                for (Map.Entry<Integer, Integer> entry : tp.entrySet()) {
                    if (entry.getValue() >= l) {
                        ans *= nCr(entry.getValue(), l);
                        break;
                    } else {
                        l -= entry.getValue();
                    }
                }
            }

            System.out.println(ans);
        }
    }
}
