import java.util.*;

public class Main {
    static final int MAXN = 500005;
    static final int MOD = 1_000_000_007;
    static long[] ara = new long[MAXN];
    static long[] h1 = new long[MAXN];
    static long[] h2 = new long[MAXN];
    static long[] zr = new long[MAXN];
    static long[] neg = new long[MAXN];
    static int[] cnt = new int[100005];
    static List<Integer>[] G = new ArrayList[100005];
    static boolean[] isComposite = new boolean[100005];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int q = sc.nextInt();
        long base = 101;
        long hsq = 0, hcb = 0;

        // Sieve and preprocessing G[]
        int sq = (int) Math.sqrt(100000);
        for (int i = 0; i < 100005; i++) {
            G[i] = new ArrayList<>();
        }
        for (int i = 4; i <= 100000; i += 2)
            isComposite[i] = true;
        for (int i = 3; i <= sq; i += 2)
            if (!isComposite[i])
                for (int j = i * i; j <= 100000; j += 2 * i)
                    isComposite[j] = true;

        for (int i = 2; i <= 100000; i++) {
            if (!isComposite[i]) {
                for (int j = i; j <= 100000; j += i) {
                    int temp = j, c = 0;
                    while (temp % i == 0) {
                        temp /= i;
                        c++;
                    }
                    c %= 6;
                    for (int k = 0; k < c; k++) {
                        G[j].add(i);
                    }
                }
            }
        }

        // Input and prefix processing
        for (int i = 1; i <= n; i++) {
            ara[i] = sc.nextLong();
            zr[i] = zr[i - 1] + (ara[i] == 0 ? 1 : 0);
            neg[i] = neg[i - 1] + (ara[i] < 0 ? 1 : 0);
            long absVal = Math.abs(ara[i]);

            for (int p : G[(int) absVal]) {
                long val = bigMod(base, p, MOD);
                if (cnt[p] % 2 == 0)
                    hsq = (hsq + val) % MOD;
                else
                    hsq = (hsq - val + MOD) % MOD;

                if (cnt[p] % 3 != 2)
                    hcb = (hcb + val) % MOD;
                else
                    hcb = (hcb - 2 * val + MOD) % MOD;

                cnt[p]++;
            }

            h1[i] = hsq;
            h2[i] = hcb;
        }

        // Process queries
        for (int i = 0; i < q; i++) {
            int l = sc.nextInt();
            int r = sc.nextInt();

            boolean issq = false, iscb = false;
            long sqhash = (h1[r] - h1[l - 1] + MOD) % MOD;
            long cbhash = (h2[r] - h2[l - 1] + MOD) % MOD;

            if (zr[r] - zr[l - 1] > 0) {
                issq = true;
                iscb = true;
            }
            if ((neg[r] - neg[l - 1]) % 2 == 0 && sqhash == 0)
                issq = true;
            if (cbhash == 0)
                iscb = true;

            if (issq && iscb) System.out.println("Both");
            else if (issq) System.out.println("Square");
            else if (iscb) System.out.println("Cube");
            else System.out.println("None");
        }

        sc.close();
    }

    // Modular Exponentiation
    static long bigMod(long B, long P, long M) {
        long R = 1;
        B %= M;
        while (P > 0) {
            if ((P & 1) == 1)
                R = (R * B) % M;
            B = (B * B) % M;
            P >>= 1;
        }
        return R;
    }
}
