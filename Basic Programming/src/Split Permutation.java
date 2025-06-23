import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        StringBuilder out = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            int[] A = new int[n];
            int[] B = new int[n];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                A[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                B[i] = Integer.parseInt(st.nextToken());
            }

            Map<Integer, Integer> mapA = new HashMap<>();
            Map<Integer, Integer> mapB = new HashMap<>();
            int segments = 0;

            for (int i = 0; i < n; i++) {
                mapA.put(A[i], mapA.getOrDefault(A[i], 0) + 1);
                mapB.put(B[i], mapB.getOrDefault(B[i], 0) + 1);

                if (mapA.equals(mapB)) {
                    segments++;
                    mapA.clear();
                    mapB.clear();
                }
            }

            out.append(segments).append("\n");
        }

        System.out.print(out);
    }
}
