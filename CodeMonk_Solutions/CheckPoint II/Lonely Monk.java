import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] A = new int[N + 1];
        int[] p = new int[N + 1];
        long[] cnt = new long[2]; // cnt[0] for even prefix sum parity, cnt[1] for odd
        long ans = 0;

        cnt[0] = 1; // base case: empty prefix has even parity

        for (int i = 1; i <= N; i++) {
            A[i] = sc.nextInt();
            p[i] = (p[i - 1] + A[i]) % 2;
            ans += cnt[p[i]];
            cnt[p[i]]++;
        }

        System.out.println(ans);
    }
}
