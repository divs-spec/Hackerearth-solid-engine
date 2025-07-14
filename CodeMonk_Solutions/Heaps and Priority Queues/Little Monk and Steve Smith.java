import java.io.*;
import java.util.*;

public class Main {
    static class Pair {
        int value;
        int index;

        Pair(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    public static void main(String[] args) throws IOException {
        // Fast I/O
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // Max-heap with custom comparator
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> {
            if (b.value != a.value)
                return Integer.compare(b.value, a.value); // Max value first
            return Integer.compare(a.index, b.index);     // Min index tie-breaker
        });

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            int val = Integer.parseInt(st.nextToken());
            pq.add(new Pair(val, i + 1));  // i+1 for 1-based index
        }

        for (int i = 0; i < n; i++) {
            Pair top = pq.poll();
            sb.append(top.index).append(" ");
            top.value -= 1;
            pq.add(top);
        }

        System.out.println(sb);
    }
}
