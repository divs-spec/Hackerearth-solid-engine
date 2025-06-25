import java.util.*;

public class BFSHierarchy {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int r = sc.nextInt();

        // Input array: each element is [a, b]
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = sc.nextInt();
            arr[i][1] = sc.nextInt();
        }

        // Queue: each element is [value, count, level]
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, r, 0});

        int i = 0;

        while (!q.isEmpty()) {
            int[] ele = q.poll();  // current node
            int rank = 1;

            while (ele[1] > 0 && i < n) {
                System.out.println(ele[0] + " " + (ele[2] + 1) + " " + rank);

                q.add(new int[]{arr[i][0], arr[i][1], ele[2] + 1});

                ele[1]--;
                i++;
                rank++;
            }
        }

        sc.close();
    }
}
