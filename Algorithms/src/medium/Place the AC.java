import java.io.*;
import java.util.*;

public class TestClass {

    static class Interval {
        int l, r;
        Interval(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    static int R, P;
    static int[] A;

    static boolean canCover(int g) {
        List<Interval> intervals = new ArrayList<>();

        for (int x : A) {
            int L = Math.max(1, x - (g - 1));
            int Rr = Math.min(R, x + (g - 1));
            intervals.add(new Interval(L, Rr));
        }

        intervals.sort((a, b) -> a.l - b.l);

        int idx = 0;
        int coveredTill = 1;

        while (coveredTill <= R) {
            int farthest = coveredTill;

            while (idx < intervals.size() && intervals.get(idx).l <= coveredTill) {
                farthest = Math.max(farthest, intervals.get(idx).r + 1);
                idx++;
            }

            if (farthest == coveredTill) return false;
            coveredTill = farthest;
        }

        return true;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        R = Integer.parseInt(br.readLine().trim());
        P = Integer.parseInt(br.readLine().trim());

        A = new int[P];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < P; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(A);

        int lo = 1, hi = R, ans = R;

        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (canCover(mid)) {
                ans = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }

        System.out.println(ans);
    }
}
