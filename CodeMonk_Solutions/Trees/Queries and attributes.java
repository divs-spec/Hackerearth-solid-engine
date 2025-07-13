import java.io.*;
import java.util.*;

public class GridToggle {
    static boolean stand = true;
    static long n, m, q, ans = 0;
    static Set<String> st = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stz = new StringTokenizer(br.readLine());
        n = Long.parseLong(stz.nextToken());
        m = Long.parseLong(stz.nextToken());
        q = Long.parseLong(stz.nextToken());

        for (int i = 0; i < q; i++) {
            stz = new StringTokenizer(br.readLine());
            int mode = Integer.parseInt(stz.nextToken());
            if (mode == 1) {
                int x = Integer.parseInt(stz.nextToken());
                int y = Integer.parseInt(stz.nextToken());
                String key = x + "," + y;

                if (st.contains(key)) {
                    st.remove(key);
                    if (stand) ans--;
                    else ans++;
                } else {
                    st.add(key);
                    if (stand) ans++;
                    else ans--;
                }
            } else {
                ans = n * m - ans;
                stand = !stand;
            }
        }

        System.out.println(ans);
    }
}
