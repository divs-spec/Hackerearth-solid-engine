import java.io.*;
import java.util.*;

public class Main {

    static final int MAXN = 1000010;

    static int n, q, k;
    static int[] arr = new int[MAXN];
    static Node[] root = new Node[MAXN];

    static class Node {
        int cnt;
        Node left, right;

        Node(int cnt, Node left, Node right) {
            this.cnt = cnt;
            this.left = left;
            this.right = right;
        }

        Node insert(int s, int e, int v) {
            if (v < s || v > e) return this;

            if (s == e) {
                return new Node(this.cnt + 1, nullNode, nullNode);
            }

            int mid = (s + e) >> 1;

            return new Node(
                this.cnt + 1,
                this.left.insert(s, mid, v),
                this.right.insert(mid + 1, e, v)
            );
        }
    }

    static Node nullNode = new Node(0, null, null);

    static int query(Node a, Node b, int s, int e) {
        int chk1 = b.cnt - a.cnt;
        int chk2 = e - s + 1;

        if (chk1 == chk2 || e <= k) return -1;

        if (s == e) return s;

        int mid = (s + e) >> 1;

        int ret = query(a.left, b.left, s, mid);
        if (ret == -1) {
            ret = query(a.right, b.right, mid + 1, e);
        }

        return ret;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        n = fs.nextInt();
        k = fs.nextInt();

        for (int i = 0; i < n; i++) {
            arr[i] = fs.nextInt();
        }

        nullNode.left = nullNode;
        nullNode.right = nullNode;

        root[0] = nullNode.insert(1, 1000001, arr[0]);

        for (int i = 1; i < n; i++) {
            root[i] = root[i - 1].insert(1, 1000001, arr[i]);
        }

        q = fs.nextInt();

        StringBuilder sb = new StringBuilder();

        while (q-- > 0) {
            int l = fs.nextInt();
            int r = fs.nextInt();

            l--; r--;

            int res = query(
                (l == 0 ? nullNode : root[l - 1]),
                root[r],
                1,
                1000001
            );

            sb.append(res).append('\n');
        }

        System.out.print(sb);
    }

    // Fast I/O
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }

        int nextInt() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                st = new StringTokenizer(br.readLine());
            }
            return Integer.parseInt(st.nextToken());
        }
    }
}
