import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Fast input/output
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());

            int[] A = new int[N];
            int[] B = new int[N];

            // Read A
            String[] partsA = br.readLine().split(" ");
            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(partsA[i]);
            }

            // Read B
            String[] partsB = br.readLine().split(" ");
            for (int i = 0; i < N; i++) {
                B[i] = Integer.parseInt(partsB[i]);
            }

            int minIdx = -1;
            int maxIdx = -1;

            // Find min and max index where A[i] != B[i]
            for (int i = 0; i < N; i++) {
                if (A[i] != B[i]) {
                    if (minIdx == -1) {
                        minIdx = i;
                    }
                    maxIdx = i;
                }
            }

            // Output the length of the differing subarray
            System.out.println(maxIdx - minIdx + 1);
        }
    }
}
