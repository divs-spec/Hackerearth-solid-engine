import java.io.*;
import java.util.*;

public class Main {

    public static void solve(BufferedReader br) throws Exception {
        String line = br.readLine();
        if (line == null) return;

        String[] nm = line.trim().split(" ");
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        int[][] v = new int[n][m];

        line = br.readLine();
        if (line == null) return;
        int q = Integer.parseInt(line.trim());

        for (int k = 0; k < q; k++) {
            line = br.readLine();
            if (line == null) break;

            String[] ij = line.trim().split(" ");
            int i = Integer.parseInt(ij[0]);
            int j = Integer.parseInt(ij[1]);

            if (i >= 0 && i < n && j >= 0 && j < m) {
                v[i][j] = 1;
            }
        }

        int c1 = 0;
        int c2 = 0;

        for (int i = 0; i < n; i++) {
            int length = 0;

            for (int j = 0; j < m; j++) {
                if (v[i][j] == 0) {
                    length++;
                } else {
                    c1 += (length + 1) / 2;
                    c2 += (length + 2) / 3;
                    length = 0;
                }
            }

            // Handle remaining segment in row
            c1 += (length + 1) / 2;
            c2 += (length + 2) / 3;
        }

        System.out.println(c1 + " " + c2);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        solve(br);
    }
}
