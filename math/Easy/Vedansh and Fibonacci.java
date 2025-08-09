import java.util.*;
import java.math.*;

class Main {

    public static BigInteger sqrt(BigInteger x) {
        BigInteger y = BigInteger.ZERO;
        for (int i = (x.bitLength() - 1) / 2; i >= 0; i--) {
            y = y.setBit(i);
            if (y.multiply(y).compareTo(x) > 0) {
                y = y.clearBit(i);
            }
        }
        return y;
    }

    public static boolean perfect(BigInteger bg) {
        BigInteger root = sqrt(bg);
        return root.multiply(root).equals(bg);
    }

    public static boolean fib(BigInteger bg) {
        BigInteger fiveN2 = bg.multiply(bg).multiply(BigInteger.valueOf(5));
        return perfect(fiveN2.add(BigInteger.valueOf(4))) ||
               perfect(fiveN2.subtract(BigInteger.valueOf(4)));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        while (n-- > 0) {
            BigInteger bg = new BigInteger(sc.next());
            if (fib(bg)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
        sc.close();
    }
}
