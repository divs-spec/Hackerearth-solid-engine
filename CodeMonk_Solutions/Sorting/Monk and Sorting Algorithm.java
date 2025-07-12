import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Input handling
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String[] tokens = br.readLine().split(" ");
        int[] arr = new int[N];
        int maxVal = 0;

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(tokens[i]);
            maxVal = Math.max(maxVal, arr[i]);
        }

        int i = 1;
        int j = 100000;
        while (maxVal > 0) {
            int finalI = i;
            int finalJ = j;

            // Convert int[] to Integer[] to use comparator
            Integer[] sortedArr = Arrays.stream(arr).boxed().toArray(Integer[]::new);

            Arrays.sort(sortedArr, Comparator.comparingInt(x -> (x / finalI) % finalJ));

            // Update original array and print
            for (int k = 0; k < N; k++) {
                arr[k] = sortedArr[k];
                System.out.print(arr[k] + " ");
            }
            System.out.println();

            i *= j;
            maxVal /= j;
        }
    }
}
