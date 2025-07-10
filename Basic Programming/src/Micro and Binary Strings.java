import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        while (t-- > 0) {
            int n = sc.nextInt();  // Length of the string (not used)
            String s = sc.next();  // Binary string input

            int count = 0;
            for (char c : s.toCharArray()) {
                if (c == '1') count++;
            }

            System.out.println(count);
        }
    }
}
