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
