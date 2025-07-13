import java.util.*;

public class Main {
    static final int MAX = 1001000;
    static final long[] pw = new long[MAX];
    static final ArrayList<Long>[] hsh = new ArrayList[MAX];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong();
        sc.nextLine(); // consume remaining newline

        for (int i = 0; i < MAX; i++) {
            hsh[i] = new ArrayList<>();
        }

        // Precompute powers of 31
        pw[0] = 1;
        for (int i = 1; i <= 1_000_000; i++) {
            pw[i] = pw[i - 1] * 31;
        }

        int right = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            String str = sc.nextLine();
            right = Math.min(right, str.length());
            hsh[i].add(0L); // first hash is 0
            for (int j = 0; j < str.length(); j++) {
                long prevHash = hsh[i].get(hsh[i].size() - 1);
                hsh[i].add(prevHash * 31 + (str.charAt(j) - 'a' + 1));
            }
        }

        long left = 0, ans = 0;

        while (left <= right) {
            long mid = (left + right) / 2;
            int cur = 0, prev = 1;
            Set<Long>[] s = new HashSet[2];
            s[0] = new HashSet<>();
            s[1] = new HashSet<>();

            for (int i = 1; i <= n; i++) {
                for (int j = (int) mid; j < hsh[i].size(); j++) {
                    int k = j - (int) mid;
                    long hash = hsh[i].get(j) - pw[(int) mid] * hsh[i].get(k);
                    if (i == 1) {
                        s[cur].add(hash);
                    } else if (s[prev].contains(hash)) {
                        s[cur].add(hash);
                    }
                }
                s[prev].clear();
                int temp = cur;
                cur = prev;
                prev = temp;
            }

            if (!s[prev].isEmpty()) {
                ans = mid;
                left = mid + 1;
            } else {
                right = (int) mid - 1;
            }
        }

        System.out.println(ans);
    }
}
