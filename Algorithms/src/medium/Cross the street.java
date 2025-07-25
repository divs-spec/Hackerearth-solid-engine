import java.util.*;

public class Main {
    static int[] X = {0, 0, -1, 1};
    static int[] Y = {-1, 1, 0, 0};
    static int n, m;

    static boolean isValid(int x, int y) {
        return !(x < 0 || x >= n || y < 0 || y >= m);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();

        int[][] A = new int[n][m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                A[i][j] = sc.nextInt();

        int minCost = -1, bestValue = 0;

        for (int val = 1; val <= 100; val++) {
            boolean[][] visited = new boolean[n][m];
            int[][] cost = new int[n][m];
            for (int[] row : cost) Arrays.fill(row, -1);

            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

            pq.add(new int[]{Math.abs(val - A[0][0]), 0, 0});
            cost[0][0] = Math.abs(val - A[0][0]);

            while (!pq.isEmpty()) {
                int[] top = pq.poll();
                int currCost = top[0], x = top[1], y = top[2];

                if (visited[x][y]) continue;
                visited[x][y] = true;

                for (int d = 0; d < 4; d++) {
                    int nx = x + X[d];
                    int ny = y + Y[d];

                    if (isValid(nx, ny)) {
                        int newCost = currCost + Math.abs(A[nx][ny] - val);
                        if (cost[nx][ny] == -1 || newCost < cost[nx][ny]) {
                            cost[nx][ny] = newCost;
                            pq.add(new int[]{newCost, nx, ny});
                        }
                    }
                }
            }

            if (minCost == -1 || cost[n - 1][m - 1] < minCost) {
                minCost = cost[n - 1][m - 1];
                bestValue = val;
            }
        }

        System.out.println(minCost);
    }
}
