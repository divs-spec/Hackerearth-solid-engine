import java.io.*;
import java.util.*;

public class Main {

    static class Trie {
        Trie[] bitTrie = new Trie[2];
        long node_cnt = 0;

        void add(long r, int pos) {
            boolean bit = (r & (1L << pos)) != 0;
            node_cnt++;

            if (bitTrie[bit ? 1 : 0] == null) {
                bitTrie[bit ? 1 : 0] = new Trie();
            }
            if (pos >= 0) {
                bitTrie[bit ? 1 : 0].add(r, pos - 1);
            }
        }

        long checker(long r, long y, int pos) {
            if (pos < 0) return 0;

            boolean bit = (r & (1L << pos)) != 0;
            boolean bitRange = (y & (1L << pos)) != 0;
            int xors = ((r ^ y) & (1L << pos)) != 0 ? 1 : 0;

            long ans = 0;

            if (bitRange && bitTrie[bit ? 1 : 0] != null) {
                ans += bitTrie[bit ? 1 : 0].node_cnt;
            }

            if (bitTrie[xors] != null) {
                ans += bitTrie[xors].checker(r, y, pos - 1);
            }

            return ans;
        }
    }

    public static void solve(BufferedReader br) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long u = Long.parseLong(st.nextToken());
        long v = Long.parseLong(st.nextToken());

        Trie bitTrie = new Trie();
        long ans = 0;

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            long x = Long.parseLong(st.nextToken());

            ans += bitTrie.checker(x, v + 1, 31) - bitTrie.checker(x, u, 31);
            bitTrie.add(x, 31);
        }

        System.out.println(ans);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            solve(br);
        }
    }
}
