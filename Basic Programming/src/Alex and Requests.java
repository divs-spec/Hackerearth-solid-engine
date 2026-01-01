//Partially accepted
import java.io.*;
import java.util.*;

class TestClass {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine().trim());
        int[] checking = new int[N];   // initialized with 0s by default

        int Q = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();

        for (int qi = 0; qi < Q; qi++) {
            int val = Integer.parseInt(br.readLine().trim());

            if (val <= checking[0]) {
                sb.append("NO\n");
            } 
            else if (val < N) {
                int idx = Arrays.binarySearch(checking, 0, val, val);
                if (idx < 0) idx = -idx - 1; // bisect_left equivalent

                if (idx - 1 == -1) {
                    sb.append("NO\n");
                } else {
                    checking[idx - 1] = val;
                    sb.append("YES\n");
                }
            } 
            else {
                int idx = Arrays.binarySearch(checking, val);
                if (idx < 0) idx = -idx - 1; // bisect_left equivalent

                if (idx - 1 == -1) {
                    sb.append("NO\n");
                } else {
                    checking[idx - 1] = val;
                    sb.append("YES\n");
                }
            }
        }

        System.out.print(sb.toString());
    }
}

//accepted 
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        assert n >= 1 && n <= 100;

        int q = sc.nextInt();
        assert q >= 1 && q <= 10000;

        int[] cur = new int[105];

        while (q-- > 0) {
            int x = sc.nextInt();
            assert x >= 1 && x <= 100000;

            int val = Math.min(x, n);
            boolean found = false;

            for (int i = val; i >= 1; i--) {
                if (cur[i] < x) {
                    cur[i] = x;
                    System.out.println("YES");
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("NO");
            }
        }

        sc.close();
    }
}
