import java.util.*;

public class Main {
    static boolean[] jhooli = new boolean[26];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Set the letters from "jhoolite"
        jhooli['l' - 'a'] = true;
        jhooli['i' - 'a'] = true;
        jhooli['t' - 'a'] = true;
        jhooli['e' - 'a'] = true;
        jhooli['j' - 'a'] = true;
        jhooli['h' - 'a'] = true;
        jhooli['o' - 'a'] = true;

        int t = sc.nextInt();
        sc.nextLine();  // Consume newline

        while (t-- > 0) {
            int n = sc.nextInt();
            int k = sc.nextInt();
            sc.nextLine();  // Consume newline

            String[] s = sc.nextLine().split(" ");
            List<Pair> v = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                int matchCount = matchString(s[i]);
                v.add(new Pair(matchCount, i));
            }

            // Sort by matchCount ascending
            v.sort(Comparator.comparingInt(p -> p.match));

            // Build and print result
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < k; i++) {
                result.append(s[v.get(i).index]).append(" ");
            }
            System.out.println(result.toString().trim());
        }

        sc.close();
    }

    static int matchString(String a) {
        boolean[] match = new boolean[26];
        for (char ch : a.toCharArray()) {
            if ('a' <= ch && ch <= 'z') {
                match[ch - 'a'] = true;
            } else if ('A' <= ch && ch <= 'Z') {
                match[ch - 'A'] = true;
            }
        }
        int count = 0;
        for (int i = 0; i < 26; i++) {
            if (match[i] && jhooli[i]) {
                count++;
            }
        }
        return count;
    }

    static class Pair {
        int match;
        int index;

        Pair(int match, int index) {
            this.match = match;
            this.index = index;
        }
    }
}
