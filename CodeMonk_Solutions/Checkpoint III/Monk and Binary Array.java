import java.util.Scanner;

public class Main {
    static int[] arr = new int[2000];
    static int[] x0 = new int[2001];
    static int[] x1 = new int[2001];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int tot = 0, res = 0;

        for (int i = 0; i < n; ++i) {
            arr[i] = sc.nextInt();
        }

        for (int i = 0; i < n; ++i) {
            int test = 0;
            for (int j = i; j < n; ++j) {
                test ^= arr[j];
                if (test != 0) {
                    tot++;
                    x1[i]++;
                    x1[j + 1]--;
                } else {
                    x0[i]++;
                    x0[j + 1]--;
                }
            }
        }

        // Prefix sum to accumulate x1 and x0
        for (int i = 1; i < n; ++i) {
            x1[i] += x1[i - 1];
            x0[i] += x0[i - 1];
        }

        for (int i = 0; i < n; ++i) {
            res = Math.max(res, tot - x1[i] + x0[i]);
        }

        System.out.println(res);
    }
}
