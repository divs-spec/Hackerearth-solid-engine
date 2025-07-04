import java.io.*;

public class Main {
    static int[] fib = new int[50];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");
        long L = Long.parseLong(parts[0]);
        long R = Long.parseLong(parts[1]);

        buildFibonacci();

        long pos = 1;
        int num = 1;
        int[] count = new int[4];  // 00, 01, 10, 11

        int prev = -1;

        outer:
        while (pos <= R) {
            int n = num++;

            boolean started = false;

            for (int i = 44; i >= 0; i--) {
                if (fib[i] <= n) {
                    n -= fib[i];
                    if (!started) started = true;
                    if (pos >= L) {
                        if (prev != -1) count[(prev << 1) | 1]++;
                        prev = 1;
                    } else {
                        prev = 1;
                    }
                    pos++;
                    if (pos > R) break outer;
                } else if (started) {
                    if (pos >= L) {
                        if (prev != -1) count[(prev << 1) | 0]++;
                        prev = 0;
                    } else {
                        prev = 0;
                    }
                    pos++;
                    if (pos > R) break outer;
                }
            }
        }

        System.out.println(count[0] + " " + count[1] + " " + count[2] + " " + count[3]);
    }

    static void buildFibonacci() {
        fib[0] = 1;
        fib[1] = 2;
        for (int i = 2; i < 50; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
            if (fib[i] > 1_000_000_000) break;
        }
    }
}
