import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] m1 = new int[26];
        int[] m2 = new int[26];

        int n = sc.nextInt();
        String str1 = sc.next();
        for (int i = 0; i < str1.length(); i++) {
            m1[str1.charAt(i) - 'a']++;
        }

        String str2 = sc.next();
        for (int i = 0; i < str2.length(); i++) {
            m2[str2.charAt(i) - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if (m1[i] != m2[i]) {
                System.out.println("NO");
                return;
            }
        }

        System.out.println("YES");
    }
}
