import java.util.*;

public class Main {
    static List<List<Integer>> graph = new ArrayList<>();
    static List<Integer> lowlink = new ArrayList<>();
    static List<Integer> pre = new ArrayList<>();
    static List<Integer> cp = new ArrayList<>();
    static List<Boolean> onStack = new ArrayList<>();
    static List<Boolean> visited = new ArrayList<>();
    static int[] dp;
    static Stack<Integer> st = new Stack<>();
    static int timer = 0, componentno = 0;

    static int sccdfs(int node, List<List<Integer>> sccgraph, boolean[] sccvisited) {
        if (sccvisited[node]) return dp[node];
        int maxLen = 0;
        sccvisited[node] = true;
        for (int child : sccgraph.get(node)) {
            maxLen = Math.max(maxLen, 1 + sccdfs(child, sccgraph, sccvisited));
        }
        dp[node] = maxLen;
        return maxLen;
    }

    static void dfs(int node) {
        st.push(node);
        pre.set(node, ++timer);
        lowlink.set(node, timer);
        onStack.set(node, true);
        visited.set(node, true);
        for (int child : graph.get(node)) {
            if (!visited.get(child)) {
                dfs(child);
                lowlink.set(node, Math.min(lowlink.get(node), lowlink.get(child)));
            } else if (onStack.get(child)) {
                lowlink.set(node, Math.min(lowlink.get(node), pre.get(child)));
            }
        }
        if (lowlink.get(node).equals(pre.get(node))) {
            while (true) {
                int x = st.pop();
                cp.set(x, componentno);
                onStack.set(x, false);
                if (x == node) break;
            }
            componentno++;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), M = sc.nextInt();
        for (int i = 0; i < N; i++) graph.add(new ArrayList<>());
        for (int i = 0; i < N; i++) {
            pre.add(0);
            lowlink.add(0);
            cp.add(-1);
            onStack.add(false);
            visited.add(false);
        }

        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int x = sc.nextInt() - 1;
            int y = sc.nextInt() - 1;
            graph.get(x).add(y);
            edges.add(new int[]{x, y});
        }

        for (int i = 0; i < N; i++) {
            if (!visited.get(i)) dfs(i);
        }

        List<List<Integer>> sccgraph = new ArrayList<>();
        for (int i = 0; i < componentno; i++) sccgraph.add(new ArrayList<>());

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            if (cp.get(u) != cp.get(v)) {
                sccgraph.get(cp.get(u)).add(cp.get(v));
            }
        }

        boolean[] sccvisited = new boolean[componentno];
        dp = new int[componentno];
        int ans = 0;
        for (int i = 0; i < componentno; i++) {
            if (!sccvisited[i]) {
                ans = Math.max(ans, sccdfs(i, sccgraph, sccvisited));
            }
        }

        System.out.println(ans);
        sc.close();
    }
}
