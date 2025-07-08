import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            String[] arr = new String[N];

            for (int i = 0; i < N; i++) {
                arr[i] = br.readLine();
            }

            Arrays.sort(arr, new Comparator<String>() {
                public int compare(String a, String b) {
                    int len = Math.min(a.length(), b.length());

                    for (int i = 0; i < len; i++) {
                        char ca = a.charAt(i);
                        char cb = b.charAt(i);

                        if (ca == cb) continue;

                        // Space has highest priority
                        if (ca == ' ' && cb != ' ') return -1;
                        if (cb == ' ' && ca != ' ') return 1;

                        // Compare lowercase values
                        char lca = Character.toLowerCase(ca);
                        char lcb = Character.toLowerCase(cb);

                        if (lca != lcb) return lca - lcb;

                        // If same character but different case, lowercase is preferred
                        if (ca != cb) {
                            if (Character.isLowerCase(ca)) return -1;
                            else return 1;
                        }
                    }

                    return a.length() - b.length();
                }
            });

            for (String str : arr) {
                System.out.println(str);
            }
        }
    }
}
