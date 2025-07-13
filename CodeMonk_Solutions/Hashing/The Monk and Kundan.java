import java.util.*;

public class Main {
    static final String hashing = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int test = Integer.parseInt(sc.nextLine());

        while (test-- > 0) {
            String[] l = sc.nextLine().split(" ");
            int length = l.length;
            int add = 0;

            for (String word : l) {
                for (int j = 0; j < word.length(); j++) {
                    int index = hashing.indexOf(word.charAt(j));
                    add += (j + index);
                }
            }

            System.out.println(add * length);
        }

        sc.close();
    }
}
