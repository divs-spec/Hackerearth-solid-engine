import java.io.*;
import java.util.*;

public class BisectQueryFastIO {

    public static int bisectLeft(int[] arr, int target) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (arr[mid] < target)
                low = mid + 1;
            else
                high = mid;
        }
        return low;
    }

    public static int bisectRight(int[] arr, int target) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (arr[mid] <= target)
                low = mid + 1;
            else
                high = mid;
        }
        return low;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        String[] parts = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }

        Arrays.sort(arr); // O(n log n)

        int q = Integer.parseInt(br.readLine());

        while (q-- > 0) {
            parts = br.readLine().split(" ");
            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);

            int count;
            if (a == 0) {
                count = arr.length - bisectLeft(arr, b);
            } else {
                count = arr.length - bisectRight(arr, b);
            }

            bw.write(count + "\n");
        }

        bw.flush(); // Flush buffered output
    }
}
