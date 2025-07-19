import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        while (T-- > 0) {
            long P = sc.nextLong();
            System.out.println(Long.bitCount(P));
        }

        sc.close();
    }
}
