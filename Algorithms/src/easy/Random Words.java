import java.util.*;

public class TestClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Input
        int n = sc.nextInt();
        String[] words = new String[n];
        
        for (int i = 0; i < n; i++) {
            words[i] = sc.next();
        }

        // Custom sort: first by length, then by lexicographic order
        Arrays.sort(words, (a, b) -> {
            if (a.length() != b.length()) {
                return Integer.compare(a.length(), b.length());
            } else {
                return a.compareTo(b); // lexicographic comparison
            }
        });

        // Output
        for (String word : words) {
            System.out.println(word);
        }

        sc.close();
    }
}
