import java.io.*;
import java.util.*;

public class Main {

    static int n, m, x, y;
    static int[][][][][] dp = new int[26][7][7][64][64];

    static int calcIntro(int pos, int introMask, int extroMask) {
        int r = pos / m;
        int c = pos % m;
        int ans = 0;

        if (r > 0) {
            if ((introMask & (1 << (m - 1))) != 0) ans -= 180;
            if ((extroMask & (1 << (m - 1))) != 0) ans -= 30;
        }

        if (c > 0) {
            if ((introMask & 1) != 0) ans -= 180;
            if ((extroMask & 1) != 0) ans -= 30;
        }

        return ans;
    }

    static int calcExtro(int pos, int introMask, int extroMask) {
        int r = pos / m;
        int c = pos % m;
        int ans = 0;

        if (r > 0) {
            if ((introMask & (1 << (m - 1))) != 0) ans -= 30;
            if ((extroMask & (1 << (m - 1))) != 0) ans += 120;
        }

        if (c > 0) {
            if ((introMask & 1) != 0) ans -= 30;
            if ((extroMask & 1) != 0) ans += 120;
        }

        return ans;
    }

    static int checker(int pos, int introLeft, int extroLeft, int introMask, int extroMask) {
        if (pos >= n * m) return 0;

        if (dp[pos][introLeft][extroLeft][introMask][extroMask] != -1) {
            return dp[pos][introLeft][extroLeft][introMask][extroMask];
        }

        int newIntroMask = (introMask << 1) & 63;
        int newExtroMask = (extroMask << 1) & 63;

        int ans = checker(pos + 1, introLeft, extroLeft, newIntroMask, newExtroMask);

        if (introLeft > 0) {
            ans = Math.max(ans,
                    360 + checker(pos + 1, introLeft - 1, extroLeft,
                            newIntroMask + 1, newExtroMask)
                            + calcIntro(pos, introMask, extroMask));
        }

        if (extroLeft > 0) {
            ans = Math.max(ans,
                    120 + checker(pos + 1, introLeft, extroLeft - 1,
                            newIntroMask, newExtroMask + 1)
                            + calcExtro(pos, introMask, extroMask));
        }

        return dp[pos][introLeft][extroLeft][introMask][extroMask] = ans;
    }

    static void solve(BufferedReader br) throws Exception {
        String[] input = br.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        m = Integer.parseInt(input[1]);
        x = Integer.parseInt(input[2]);
        y = Integer.parseInt(input[3]);

        for (int i = 0; i < 26; i++)
            for (int j = 0; j < 7; j++)
                for (int k = 0; k < 7; k++)
                    for (int l = 0; l < 64; l++)
                        Arrays.fill(dp[i][j][k][l], -1);

        int ans = checker(0, x, y, 0, 0);
        System.out.println(ans);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        solve(br);
    }
}
