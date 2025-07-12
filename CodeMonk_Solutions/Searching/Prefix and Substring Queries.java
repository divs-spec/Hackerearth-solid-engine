import java.io.*;
import java.util.*;

public class PrefixSubstringQuery {
    static final int MOD = 1_000_000_007;
    static final int BASE = 31;
    static final int MAX = 300005;

    static long[] pow = new long[MAX];
    static long[] hash = new long[MAX];
    static char[] s = new char[MAX];
    static int length = 0;

    static Map<Integer, Map<Long, TreeSet<Integer>>> prefixLengthToHashIndices = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] init = br.readLine().split(" ");
        int n = Integer.parseInt(init[0]);
        int q = Integer.parseInt(init[1]);

        String str = br.readLine();
        for (int i = 0; i < str.length(); i++) {
            s[i + 1] = str.charAt(i);
        }
        length = str.length();

        precomputePowers();
        buildHash();

        while (q-- > 0) {
            String[] parts = br.readLine().split(" ");
            int type = Integer.parseInt(parts[0]);

            if (type == 1) {
                char c = parts[1].charAt(0);
                append(c);
            } else if (type == 2) {
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                System.out.println(handleType2(x, y));
            } else {
                int p = Integer.parseInt(parts[1]);
                int l = Integer.parseInt(parts[2]);
                int r = Integer.parseInt(parts[3]);
                System.out.println(handleType3(p, l, r));
            }
        }
    }

    static void precomputePowers() {
        pow[0] = 1;
        for (int i = 1; i < MAX; i++) {
            pow[i] = (pow[i - 1] * BASE) % MOD;
        }
    }

    static void buildHash() {
        for (int i = 1; i <= length; i++) {
            hash[i] = (hash[i - 1] * BASE + s[i] - 'a' + 1) % MOD;
        }
    }

    static void append(char c) {
        length++;
        s[length] = c;
        hash[length] = (hash[length - 1] * BASE + c - 'a' + 1) % MOD;

        // Update hash indices for all pending prefix lengths
        for (int p : prefixLengthToHashIndices.keySet()) {
            if (length >= p) {
                long h = getHash(length - p + 1, length);
                prefixLengthToHashIndices.get(p).computeIfAbsent(h, k -> new TreeSet<>()).add(length - p + 1);
            }
        }
    }

    static long getHash(int l, int r) {
        long res = (hash[r] - (hash[l - 1] * pow[r - l + 1]) % MOD + MOD) % MOD;
        return res;
    }

    static int handleType2(int x, int y) {
        int lo = 1, hi = Math.min(x, y), ans = 0;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            long h1 = getHash(x - mid + 1, x);
            long h2 = getHash(y - mid + 1, y);
            long h0 = getHash(1, mid);

            if (h1 == h0 && h2 == h0) {
                ans = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return ans;
    }

    static int handleType3(int p, int l, int r) {
        if (length < p) return 0;

        long prefixHash = getHash(1, p);
        int from = l;
        int to = r - p + 1;

        if (from > to) return 0;

        // Cache rolling hashes for substrings of length p
        if (!prefixLengthToHashIndices.containsKey(p)) {
            prefixLengthToHashIndices.put(p, new HashMap<>());
            Map<Long, TreeSet<Integer>> hashMap = prefixLengthToHashIndices.get(p);
            for (int i = 1; i + p - 1 <= length; i++) {
                long h = getHash(i, i + p - 1);
                hashMap.computeIfAbsent(h, k -> new TreeSet<>()).add(i);
            }
        }

        TreeSet<Integer> indices = prefixLengthToHashIndices.get(p).getOrDefault(prefixHash, new TreeSet<>());
        return indices.subSet(from, true, to, true).size();
    }
}
