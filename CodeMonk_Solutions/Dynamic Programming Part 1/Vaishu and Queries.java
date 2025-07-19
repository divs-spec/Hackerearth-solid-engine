import java.util.*;

public class Main {
    static int[][][] DP = new int[105][105][105];
    static int[] RED = new int[105];
    static int[] BLUE = new int[105];
    static int N;

    static int solve(int cur, int rb, int bb) {
        if (rb < 0 || bb < 0)
            return Integer.MIN_VALUE;
        if (cur == N)
            return 0;
        if (DP[cur][rb][bb] != -1)
            return DP[cur][rb][bb];

        int curRedCount = RED[cur];
        int curBlueCount = BLUE[cur];

        int skip = solve(cur + 1, rb, bb);
        int take = 1 + solve(cur + 1, rb - curRedCount, bb - curBlueCount);

        DP[cur][rb][bb] = Math.max(skip, take);
        return DP[cur][rb][bb];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        for (int[][] d2 : DP)
            for (int[] d1 : d2)
                Arrays.fill(d1, -1);

        N = sc.nextInt();
        sc.nextLine(); // consume newline
        for (int i = 0; i < N; i++) {
            String s = sc.nextLine();
            for (char c : s.toCharArray()) {
                if (c == 'R') RED[i]++;
                if (c == 'B') BLUE[i]++;
            }
        }

        int Q = sc.nextInt();
        while (Q-- > 0) {
            int X = sc.nextInt();
            int Y = sc.nextInt();
            System.out.println(solve(0, X, Y));
        }

        sc.close();
    }
}
