import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.nextLine(); // Read the input string

        boolean isPalindrome = true;
        int n = S.length();

        for (int i = 0; i < n / 2; i++) {
            if (S.charAt(i) != S.charAt(n - 1 - i)) {
                isPalindrome = false;
                break;
            }
        }

        if (isPalindrome)
            System.out.println("YES");
        else
            System.out.println("NO");
    }
}
