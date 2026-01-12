import java.io.*;
import java.util.*;

class TestClass {

    static class Node {
        int pos, steps;
        Node(int pos, int steps) {
            this.pos = pos;
            this.steps = steps;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int X = Integer.parseInt(st.nextToken()) - 1; // 0-based
            int Y = Integer.parseInt(st.nextToken()) - 1; // 0-based

            int[] num = new int[N];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                num[i] = Integer.parseInt(st.nextToken()) % N;
            }

            out.append(minJumps(N, X, Y, num)).append('\n');
        }

        System.out.print(out.toString());
    }

    static int minJumps(int N, int start, int target, int[] num) {
        if (start == target) return 0;

        boolean[] visited = new boolean[N];
        Queue<Node> q = new LinkedList<>();

        q.add(new Node(start, 0));
        visited[start] = true;

        while (!q.isEmpty()) {
            Node cur = q.poll();
            int pos = cur.pos;
            int steps = cur.steps;

            int right = (pos + num[pos]) % N;
            int left = (pos - num[pos] + N) % N;

            if (right == target || left == target) {
                return steps + 1;
            }

            if (!visited[right]) {
                visited[right] = true;
                q.add(new Node(right, steps + 1));
            }

            if (!visited[left]) {
                visited[left] = true;
                q.add(new Node(left, steps + 1));
            }
        }

        return -1;
    }
}
