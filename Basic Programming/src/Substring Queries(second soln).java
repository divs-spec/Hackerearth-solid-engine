import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        while (T-- > 0) {
            String s = sc.next();
            int n = sc.nextInt();

            while (n-- > 0) {
                String t = sc.next();

                int[] a = new int[130]; // character count for query string
                int[] b = new int[130]; // character count for sliding window

                for (int i = 0; i < t.length(); i++) {
                    a[t.charAt(i)]++;
                }

                int j = -1, x = 0;
                long ans = 0;

                for (int i = 0; i < s.length(); i++) {
                    char ch = s.charAt(i);
                    b[ch]++;

                    if (b[ch] == a[ch]) {
                        x++;
                    }

                    if (x == t.length()) {
                        while (true) {
                            j++;
                            char chj = s.charAt(j);
                            b[chj]--;
                            ans += s.length() - i;

                            if (b[chj] < a[chj]) {
                                x--;
                                break;
                            }
                        }
                    }
                }

                System.out.println(ans);
            }
        }

        sc.close();
    }
}
