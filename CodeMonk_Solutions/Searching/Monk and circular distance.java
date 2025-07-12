import java.util.*;
import java.io.*;

public class DistanceQuery {
    public static void main(String[] args) throws IOException {
        // Fast I/O
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        double[] distances = new double[n];
        for (int i = 0; i < n; i++) {
            String[] point = br.readLine().split(" ");
            int x = Integer.parseInt(point[0]);
            int y = Integer.parseInt(point[1]);
            distances[i] = Math.sqrt(x * x + y * y);
        }

        Arrays.sort(distances);

        int q = Integer.parseInt(br.readLine());

        for (int i = 0; i < q; i++) {
            int r = Integer.parseInt(br.readLine());
            int count = upperBound(distances, r);
            System.out.println(count);
        }
    }

    // Custom upper_bound implementation (similar to C++ std::upper_bound)
    static int upperBound(double[] arr, double target) {
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
}
