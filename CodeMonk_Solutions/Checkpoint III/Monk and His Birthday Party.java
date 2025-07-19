import java.util.*;

public class Main {

    static class Worker implements Comparable<Worker> {
        int cost, capacity;

        Worker(int cost, int capacity) {
            this.cost = cost;
            this.capacity = capacity;
        }

        @Override
        public int compareTo(Worker other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();  // number of test cases
        while (t-- > 0) {
            int n = sc.nextInt();  // number of workers
            int m = sc.nextInt();  // number of load requirements

            int[] l = new int[m];
            for (int i = 0; i < m; i++) {
                l[i] = sc.nextInt();
            }

            Worker[] wc = new Worker[n];
            for (int i = 0; i < n; i++) {
                int cost = sc.nextInt();
                wc[i] = new Worker(cost, 0);
            }

            long hi = 0;
            for (int i = 0; i < n; i++) {
                wc[i].capacity = sc.nextInt();
                hi += wc[i].capacity;
            }

            Arrays.sort(l);
            Arrays.sort(wc);

            long ans = -1;
            long lo = 1;

            while (lo <= hi) {
                long mid = lo + (hi - lo) / 2;
                int j = n - 1;
                long x = wc[j].capacity;

                boolean possible = true;

                for (int i = m - 1; i >= 0 && j >= 0; i--) {
                    long left = mid;

                    while (j >= 0 && left > 0 && l[i] >= wc[j].cost) {
                        long y = Math.min(x, left);
                        x -= y;
                        left -= y;

                        if (x == 0) {
                            j--;
                            if (j >= 0)
                                x = wc[j].capacity;
                        }
                    }

                    if (j >= 0 && l[i] < wc[j].cost) {
                        possible = false;
                        break;
                    }
                }

                if (j < 0) {
                    ans = mid;
                    hi = mid - 1;
                } else if (possible) {
                    ans = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }

            System.out.println(ans);
        }
    }
}
