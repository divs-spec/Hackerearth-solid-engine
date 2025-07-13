import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Fast I/O
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        String[] initial = br.readLine().split(" ");
        int levels = Integer.parseInt(initial[0]);
        long s = Long.parseLong(initial[1]);
        
        assert levels >= 1 && levels <= 30;
        assert s >= 1 && s <= 1000000000000000000L;

        int q = Integer.parseInt(br.readLine());

        long[] pow = new long[100];
        long pro = 1;
        pow[0] = 1;

        for (int i = 1; i <= levels - 1; i++) {
            pro *= 2;
            pow[i] = pro;
        }

        long x;
        if (levels > 1)
            x = (s - ((pro - 1) * (pro - 1))) / (pro * 2 - 1) + 1;
        else
            x = s + 1;

        long root = (levels < 2) ? 0 : pow[levels - 2] - 1;

        while (q-- > 0) {
            String[] parts = br.readLine().split(" ");
            int type = Integer.parseInt(parts[0]);

            if (type == 0) {
                long val = Long.parseLong(parts[1]);
                long cnt = levels - 3;
                val -= x;

                if (levels > 1 && val == pow[levels - 1] - 1) {
                    for (int i = 1; i <= levels - 1; i++)
                        out.print("r");
                    out.println();
                } else {
                    long temp = root;
                    if (val == temp) {
                        out.println("root");
                    } else {
                        while (temp != val) {
                            if (temp > val) {
                                out.print("l");
                                temp -= pow[(int)cnt--];
                            } else {
                                out.print("r");
                                temp += pow[(int)cnt--];
                            }
                        }
                        out.println();
                    }
                }
            } else {
                long k = Long.parseLong(parts[1]);
                int l = (int)(Math.log(k) / Math.log(2)) + 1;
                long a;

                if (l == levels)
                    a = x;
                else
                    a = x + pow[levels - l - 1] - 1;

                long n = k - pow[l - 1] + 1;
                long d = pow[levels - l];
                out.println(a + (n - 1) * d);
            }
        }
        out.flush();
    }
}
