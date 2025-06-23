import java.io.*;
import java.util.*;

public class Main {
    static class SegmentTree {
        int[] tree, lazy;
        int n;

        SegmentTree(int[] arr) {
            n = arr.length;
            tree = new int[4 * n];
            lazy = new int[4 * n];
            Arrays.fill(lazy, ~0);  // Identity for AND operation (all 1s)
            build(arr, 1, 0, n - 1);
        }

        void build(int[] arr, int node, int start, int end) {
            if (start == end) {
                tree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                build(arr, 2 * node, start, mid);
                build(arr, 2 * node + 1, mid + 1, end);
                tree[node] = tree[2 * node] & tree[2 * node + 1];
            }
        }

        void push(int node, int start, int end) {
            if (lazy[node] != ~0) {
                tree[node] &= lazy[node];
                if (start != end) {
                    lazy[2 * node] &= lazy[node];
                    lazy[2 * node + 1] &= lazy[node];
                }
                lazy[node] = ~0;
            }
        }

        void update(int node, int start, int end, int l, int r, int val) {
            push(node, start, end);
            if (r < start || end < l) return;
            if (l <= start && end <= r) {
                lazy[node] &= val;
                push(node, start, end);
                return;
            }
            int mid = (start + end) / 2;
            update(2 * node, start, mid, l, r, val);
            update(2 * node + 1, mid + 1, end, l, r, val);
            tree[node] = tree[2 * node] & tree[2 * node + 1];
        }

        void fillResult(int[] res, int node, int start, int end) {
            push(node, start, end);
            if (start == end) {
                res[start] = tree[node];
                return;
            }
            int mid = (start + end) / 2;
            fillResult(res, 2 * node, start, mid);
            fillResult(res, 2 * node + 1, mid + 1, end);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        int N = Integer.parseInt(line[0]);
        int Q = Integer.parseInt(line[1]);

        int[] A = new int[N];
        String[] vals = br.readLine().split(" ");
        for (int i = 0; i < N; i++) A[i] = Integer.parseInt(vals[i]);

        SegmentTree seg = new SegmentTree(A);

        for (int q = 0; q < Q; q++) {
            String[] parts = br.readLine().split(" ");
            int L = Integer.parseInt(parts[0]) - 1;
            int R = Integer.parseInt(parts[1]) - 1;
            int V = Integer.parseInt(parts[2]);
            seg.update(1, 0, N - 1, L, R, V);
        }

        int[] result = new int[N];
        seg.fillResult(result, 1, 0, N - 1);

        StringBuilder sb = new StringBuilder();
        for (int x : result) sb.append(x).append(" ");
        System.out.println(sb.toString().trim());
    }
}
