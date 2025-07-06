import java.util.*;

public class Main {

    static int[][] dp;
    static int[] milk, apples;
    static int n;

    public static int solve(int ind, int p) {
        if (ind == n || p <= 0) return 0;
        if (dp[ind][p] != -1) return dp[ind][p];

        int ans1 = 0, ans2 = 0;

        if (p <= n - ind) {
            ans1 = apples[ind] + solve(ind + 1, p - 1);
            ans2 = solve(ind + 1, p + milk[ind] - 1);
        } else {
            ans1 = apples[ind] + solve(ind + 1, p - 1);
        }

        return dp[ind][p] = Math.max(ans1, ans2);
    }

    public static int solution(Scanner sc) {
        n = sc.nextInt();
        int p = sc.nextInt();

        milk = new int[n];
        apples = new int[n];

        for (int i = 0; i < n; i++) milk[i] = sc.nextInt();
        for (int i = 0; i < n; i++) apples[i] = sc.nextInt();

        long sum = 0;
        for (int val : apples) sum += val;

        if (p >= n) return (int) sum;

        dp = new int[n][2000];
        for (int[] row : dp) Arrays.fill(row, -1);

        return solve(0, p);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();
        while (t-- > 0) {
            System.out.println(solution(sc));
        }
    }
}

/*
Explanation :

🧠 Problem Overview
You have two arrays:
milk[i]: how much power you gain if you skip item i
apples[i]: how many apples you get if you take item i

You start with some power p.
You can either:
Take the apples: costs 1 power, and you get apples[i]
Skip the item: costs 1 power, but you gain milk[i] power

The goal is to get the most apples by making smart choices with limited power.

🧩 How the Code Works
The function solve(ind, p) tries to find the maximum apples you can collect starting from index ind with current power p.
We use recursion + memoization (DP) to avoid repeating calculations.

Three main cases:
If you have no power or reached the end, return 0.
  
If the answer is already stored in dp[ind][p], reuse it.

Else, try both choices:

Pick apples → gain apples[ind], lose 1 power
Skip → gain milk[ind] power, still lose 1 power
We choose the maximum of the two options.

Before starting, we check:

If your power p is already greater than or equal to n, you can take all items. So just return the sum of all apples.

⚙️ What dp[ind][p] Stores
It stores the best number of apples you can get starting at position ind with power p.

📦 Summary
The code uses recursion and dynamic programming to make the best choices at every step and avoid redoing work. 
It tries all valid paths (take or skip) and chooses the one that gives the maximum apples using the available power.
*/
