import java.io.*;
import java.util.*;

public class Main {
    static int[][] prefixCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            String S = br.readLine();
            int n = S.length();

            prefixCount = new int[n + 1][62];
            buildPrefixCounts(S);

            int Q = Integer.parseInt(br.readLine());

            while (Q-- > 0) {
                String qstr = br.readLine();
                int res = countSubstringsWithAnagramAsSubsequence(S, qstr);
                System.out.println(res);
            }
        }
    }

    static void buildPrefixCounts(String s) {
        int n = s.length();
        for (int i = 0; i < n; i++) {
            System.arraycopy(prefixCount[i], 0, prefixCount[i + 1], 0, 62);
            int idx = charToIndex(s.charAt(i));
            prefixCount[i + 1][idx]++;
        }
    }

    static int countSubstringsWithAnagramAsSubsequence(String s, String qstr) {
        int n = s.length();
        int result = 0;
        int[] qCount = new int[62];

        for (char c : qstr.toCharArray()) {
            qCount[charToIndex(c)]++;
        }

        // Use sliding window to find how many substrings have qstr as subsequence
        for (int start = 0; start < n; start++) {
            int[] currCount = new int[62];
            for (int end = start; end < n; end++) {
                int idx = charToIndex(s.charAt(end));
                currCount[idx]++;

                if (containsAll(currCount, qCount)) {
                    result += (n - end); // every extension of this substring is valid
                    break;
                }
            }
        }

        return result;
    }

    static boolean containsAll(int[] curr, int[] need) {
        for (int i = 0; i < 62; i++) {
            if (need[i] > curr[i])
                return false;
        }
        return true;
    }

    static int charToIndex(char c) {
        if (c >= 'A' && c <= 'Z') return c - 'A';
        if (c >= 'a' && c <= 'z') return 26 + (c - 'a');
        return 52 + (c - '0'); // 0-9
    }
}
