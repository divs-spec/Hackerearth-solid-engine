import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            sc.nextLine(); // consume leftover newline
            int[] a = new int[n];

            for (int i = 0; i < n; i++) {
                String x = sc.nextLine();
                int mask = 0;
                if (x.contains("a")) mask |= 1;
                if (x.contains("e")) mask |= 2;
                if (x.contains("i")) mask |= 4;
                if (x.contains("o")) mask |= 8;
                if (x.contains("u")) mask |= 16;
                a[i] = mask;
            }

            long[][] arr = new long[32][4];

            for (int i = n; i >= 0; i--) {
                for (int j = 31; j >= 0; j--) {
                    for (int z = 3; z >= 0; z--) {
                        if (z == 0 && j == 0) {
                            arr[j][z] = 0;
                        } else if (z == 0) {
                            arr[j][z] = 1;
                        } else if (i == n) {
                            arr[j][z] = 0;
                        } else {
                            arr[j][z] = arr[j & a[i]][z - 1] + arr[j][z];
                        }
                    }
                }
            }

            System.out.println(arr[31][3]);
        }

        sc.close();
    }
}
