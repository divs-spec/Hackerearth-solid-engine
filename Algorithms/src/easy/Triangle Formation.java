import java.io.*;
import java.util.*;

class TestClass {

    static boolean isRight(long x, long y, long z) {
        long[] arr = {x, y, z};
        Arrays.sort(arr);
        return arr[0] * arr[0] + arr[1] * arr[1] == arr[2] * arr[2];
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());
            long c = Long.parseLong(st.nextToken());
            long x = Long.parseLong(st.nextToken());

            boolean ok =
                isRight(a + x, b + x, c) ||
                isRight(a + x, b, c + x) ||
                isRight(a, b + x, c + x);

            System.out.println(ok ? "YES" : "NO");
        }
    }
}
