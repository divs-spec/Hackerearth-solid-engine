import java.io.*;
import java.util.*;

public class MaxSubarrayLength {

    public static void main(String[] args) throws IOException {
        // Fast input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input1 = br.readLine().split(" ");
        int n = Integer.parseInt(input1[0]);
        int x = Integer.parseInt(input1[1]);

        int[] arr = new int[n];
        String[] input2 = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(input2[i]);
        }

        int start = 0, end = n;
        int ans = 0;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (mid > n) {
                end = mid - 1;
                continue;
            }

            long s = 0;
            boolean valid = true;

            // Initial window sum of size `mid`
            for (int i = 0; i < mid; i++) {
                s += arr[i];
            }
            if (s > x) valid = false;

            // Sliding window
            if (valid) {
                for (int i = mid; i < n; i++) {
                    s += arr[i] - arr[i - mid];
                    if (s > x) {
                        valid = false;
                        break;
                    }
                }
            }

            if (valid) {
                ans = mid;
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        System.out.println(ans);
    }
}
