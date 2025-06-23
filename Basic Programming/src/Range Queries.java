import java.io.*;
import java.util.*;

public class Main {
    static final int MAX = 100005;
    static int[][] count = new int[21][MAX]; // count[bit][i]

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int Q = Integer.parseInt(br.readLine());

        // Precompute count of numbers having bit X set from 1 to i
        for (int x = 1; x <= 20; x++) {
            for (int i = 1; i < MAX; i++) {
                count[x][i] = count[x][i - 1];
                if (((i >> (x - 1)) & 1) == 1) {
                    count[x][i]++;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int q = 0; q < Q; q++) {
            String[] parts = br.readLine().split(" ");
            int L = Integer.parseInt(parts[0]);
            int R = Integer.parseInt(parts[1]);
            int X = Integer.parseInt(parts[2]);
            int res = count[X][R] - count[X][L - 1];
            sb.append(res).append("\n");
        }

        System.out.print(sb);
    }
}
