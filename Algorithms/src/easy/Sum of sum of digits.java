import java.util.*;

public class Main {
    static long getSum(long n) {
        if (n / 10 < 1) return n;
        long sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return getSum(sum);
    }

    static void mergeSort(long[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    static void merge(long[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        long[] L = new long[n1];
        long[] R = new long[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[l + i];
        for (int j = 0; j < n2; j++) R[j] = arr[m + 1 + j];

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int q = sc.nextInt();

        long[] v = new long[n];
        long[] u = new long[n];

        for (int i = 0; i < n; i++) {
            v[i] = sc.nextLong();
        }

        // Replace v[i] with sum of its digits reduced recursively
        for (int i = 0; i < n; i++) {
            v[i] = getSum(v[i]);
        }

        // Sort v[]
        mergeSort(v, 0, n - 1);

        // Fill u[] with reversed v[]
        for (int i = 0; i < n; i++) {
            u[i] = v[n - 1 - i];
        }

        // Prefix sum for v[]
        for (int i = 1; i < n; i++) {
            v[i] += v[i - 1];
        }

        // Prefix sum for u[]
        for (int i = 1; i < n; i++) {
            u[i] += u[i - 1];
        }

        // Answer the queries
        for (int i = 0; i < q; i++) {
            int t = sc.nextInt();
            int k = sc.nextInt();
            if (t == 1) {
                System.out.println(u[k - 1]);
            } else {
                System.out.println(v[k - 1]);
            }
        }
    }
}
