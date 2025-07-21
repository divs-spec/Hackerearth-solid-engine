import java.util.*;

public class TestClass {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            String s1 = sc.next();
            String s2 = sc.next();

            if (s1.equals(s2)) {
                System.out.println("Yes");
                continue;
            }

            // Use a HashSet for faster lookup (O(1) time)
            Set<Character> set = new HashSet<>();
            for (char c : s1.toCharArray()) {
                set.add(c);
            }

            boolean foundCommon = false;
            for (char c : s2.toCharArray()) {
                if (set.contains(c)) {
                    foundCommon = true;
                    break;
                }
            }

            System.out.println(foundCommon ? "Yes" : "No");
        }
        sc.close();
    }
}
