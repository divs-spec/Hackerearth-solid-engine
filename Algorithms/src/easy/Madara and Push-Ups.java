import java.io.*;
import java.util.*;

class TestClass {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int A = Integer.parseInt(st.nextToken());
            int D = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            int total = 0;
            int sets = 0;
            int current = A;

            while (total < N) {
                if (current < M) {
                    current = M;
                }
                total += current;
                sets++;
                current -= D;
            }

            sb.append(sets).append('\n');
        }

        System.out.print(sb.toString());
    }
}
