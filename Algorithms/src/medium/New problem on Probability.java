import java.io.*;
import java.util.*;

class TestClass {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int L = Integer.parseInt(st.nextToken());
        long T = Long.parseLong(st.nextToken());

        long[] arr = new long[L];

        for (int i = 0; i < L; i++)
            arr[i] = Long.parseLong(br.readLine().trim());

        // Sliding window
        long sum = 0;
        long valid = 0;
        int left = 0;

        for (int right = 0; right < L; right++) {

            sum += arr[right];

            // shrink window if sum exceeds T
            while (sum > T && left <= right) {
                sum -= arr[left++];
            }

            // all subarrays ending at right
            valid += (right - left + 1);
        }

        // total subarrays
        long total = (long)L * (L + 1) / 2;

        double probability = (double) valid / (double)total;

        // print up to 8 decimal places
        System.out.println(probability);
    }
}
