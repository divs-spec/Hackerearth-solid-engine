import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Fast input/output
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        int n = Integer.parseInt(br.readLine());
        long[] a = new long[n + 1];
        String[] input = br.readLine().split(" ");
        for (int i = 1; i <= n; i++) {
            a[i] = Long.parseLong(input[i - 1]);
        }

        int[] prev = new int[n + 1];
        int[] next = new int[n + 1];
        Deque<Integer> stack = new ArrayDeque<>();

        // Previous greater
        for (int i = 1; i <= n; i++) {
            while (!stack.isEmpty() && a[stack.peek()] <= a[i]) {
                stack.pop();
            }
            prev[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();

        // Next greater
        for (int i = n; i >= 1; i--) {
            while (!stack.isEmpty() && a[stack.peek()] <= a[i]) {
                stack.pop();
            }
            next[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        // Build output
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(prev[i] + next[i]).append(" ");
        }

        bw.write(sb.toString().trim());
        bw.newLine();
        bw.flush();
    }
}
