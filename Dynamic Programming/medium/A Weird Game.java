import java.io.*;
import java.util.*;

public class Main {

    static final int MAX = 100005;

    static ArrayList<Integer> F = new ArrayList<>();
    static ArrayList<Integer> B = new ArrayList<>();
    static int[] tree = new int[MAX];

    static void validate(String s) {
        for (char c : s.toCharArray()) {
            if (c < 'a' || c > 'z')
                throw new AssertionError();
        }
    }

    static boolean chk(String s, String t) {
        int p = 0;
        for (int i = 0; i < s.length() && p < t.length(); i++) {
            if (s.charAt(i) == t.charAt(p)) p++;
        }
        return p == t.length();
    }

    // Fenwick update
    static void update(int idx) {
        while (idx <= MAX - 4) {
            tree[idx] += 1;
            idx += idx & -idx;
        }
    }

    // Fenwick query
    static int query(int idx) {
        int ans = 0;
        while (idx > 0) {
            ans += tree[idx];
            idx -= idx & -idx;
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int tc = Integer.parseInt(br.readLine().trim());
        if (tc < 1 || tc > 10) throw new AssertionError();

        StringBuilder out = new StringBuilder();

        while (tc-- > 0) {

            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            String t = br.readLine().trim();

            if (s.length() < 1 || s.length() > 100000) throw new AssertionError();
            if (t.length() < 1 || t.length() > s.length()) throw new AssertionError();

            validate(s);
            validate(t);
            if (!chk(s, t)) throw new AssertionError();

            Arrays.fill(tree, 0);
            F.clear();
            B.clear();

            int p = 0;

            // Forward matches
            for (int i = 0; i < s.length(); i++) {
                if (p < t.length() && s.charAt(i) == t.charAt(p)) p++;
                F.add(p);
            }

            p = 0;

            // Backward matches
            for (int i = s.length() - 1; i >= 0; i--) {
                if (p < t.length()
                        && s.charAt(i) == t.charAt(t.length() - 1 - p)) {
                    p++;
                }
                B.add(p);
            }

            Collections.reverse(B);

            long ans = 0;
            int idx = s.length() - 1;

            for (int i = s.length() - 1; i >= 0; i--) {

                while (idx > i + 1) {
                    if (B.get(idx) > 0)
                        update(B.get(idx));
                    idx--;
                }

                if (F.get(i) == t.length()) {
                    ans += s.length() - i - 1;
                } else {
                    ans += query(t.length())
                            - query(t.length() - F.get(i) - 1);
                }
            }

            for (int i = 1; i < s.length(); i++) {
                if (B.get(i) == t.length())
                    ans++;
            }

            out.append(ans).append('\n');
        }

        System.out.print(out);
    }
}
