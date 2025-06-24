import java.util.*;
import java.io.*;

public class Codechef {

    // Function to count set bits in a number
    static long countSetBits(long num) {
        long c = 0;
        for (int i = 0; i <= (int)(Math.log(num) / Math.log(2)); i++) {
            if (((1L << i) & num) != 0) c++;
        }
        return c;
    }

    // Function to set the bit at position 'pos' in number 'num'
    static long setBit(long num, int pos) {
        long a = 1L << pos;
        return num | a;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long[] v = new long[n];
        for (int i = 0; i < n; i++) {
            v[i] = sc.nextLong();
        }

        for (int i = 0; i < n; i++) {
            long num = v[i];
            long set = countSetBits(num);
            long number = 0;
            for (int j = (int)(Math.log(num) / Math.log(2)); set > 0 && j >= 0; j--) {
                number = setBit(number, j);
                set--;
            }
            System.out.print(number + " ");
        }
    }
}
