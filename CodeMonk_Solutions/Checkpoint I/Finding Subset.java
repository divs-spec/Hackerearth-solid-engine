import java.util.*;

public class CubeFactorizationMatcher {
    static final int MX = 2155;
    static final int MAXN = 1 << 17;
    static final int INF = (int) 2e9;

    static boolean[] ispr = new boolean[MAXN];
    static List<Integer> pr = new ArrayList<>();
    static Map<Long, Integer> oo = new HashMap<>();

    public static boolean isPrime(int x) {
        if (x < 2) return false;
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) return false;
        }
        return true;
    }

    public static int Sqrt(long x) {
        int y = (int) Math.sqrt(x);
        while (1L * y * y < x) y++;
        while (1L * y * y > x) y--;
        return (1L * y * y == x) ? y : -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for (int i = 2; i <= MX; i++) {
            if (isPrime(i)) pr.add(i);
        }

        for (int i = 2; i < MAXN; i++) {
            if (isPrime(i)) {
                ispr[i] = true;
                oo.put(1L * i * i, 1);
            }
        }

        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            long[] a = new long[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextLong();
            }

            int ans = 0;
            @SuppressWarnings("unchecked")
            List<Pair>[] mem = new List[n];
            Map<List<Pair>, Integer> mp = new HashMap<>();
            Set<List<Pair>> used = new HashSet<>();

            for (int i = 0; i < n; i++) {
                int y = (int) Math.cbrt(a[i]);
                while (1L * y * y * y < a[i]) y++;
                while (1L * y * y * y > a[i]) y--;
                if (1L * y * y * y == a[i]) {
                    if (ans == 0) ans++;
                    continue;
                }

                long x = a[i];
                List<Pair> cur = new ArrayList<>();
                for (int j = 0; j < pr.size(); j++) {
                    int p = pr.get(j);
                    if (x % p == 0) {
                        int cnt = 0;
                        while (x % p == 0) {
                            x /= p;
                            cnt++;
                        }
                        if (cnt % 3 != 0) cur.add(new Pair(p, cnt % 3));
                    }
                }

                if (x < 1e5 && x < MAXN && ispr[(int)x]) {
                    cur.add(new Pair((int)x, 1));
                    x = 1;
                }

                y = Sqrt(x);
                if (y != -1 && y < MAXN && ispr[y]) {
                    cur.add(new Pair(y, 2));
                    x = 1;
                }

                if (x != 1) {
                    cur.add(new Pair((int)(2e5 + i), 1));
                }

                assert !cur.isEmpty();
                mem[i] = cur;
                mp.put(cur, mp.getOrDefault(cur, 0) + 1);
            }

            for (int i = 0; i < n; i++) {
                if (mem[i] == null) continue;
                List<Pair> cur = mem[i];
                if (used.contains(cur)) continue;

                List<Pair> oth = new ArrayList<>();
                for (Pair p : cur) {
                    oth.add(new Pair(p.first, 3 - p.second));
                }

                int count1 = mp.getOrDefault(cur, 0);
                int count2 = mp.getOrDefault(oth, 0);
                ans += Math.max(count1, count2);
                used.add(cur);
                used.add(oth);
            }

            System.out.println(ans);
        }

        sc.close();
    }

    static class Pair {
        int first, second;

        Pair(int f, int s) {
            this.first = f;
            this.second = s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair pair = (Pair) o;
            return first == pair.first && second == pair.second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        @Override
        public String toString() {
            return "(" + first + "," + second + ")";
        }
    }
}
