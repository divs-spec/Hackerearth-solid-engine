import java.io.*;
import java.util.*;

class TestClass {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tokens = br.readLine().split(" ");
        long L = Long.parseLong(tokens[0]);
        long R = Long.parseLong(tokens[1]);

        long result = xorTill(R) ^ xorTill(L - 1);
        System.out.println(result % 2 == 0 ? "even" : "odd");
    }

    static long xorTill(long n) {
        int mod = (int)(n % 4);
        if (mod == 0) return n;
        if (mod == 1) return 1;
        if (mod == 2) return n + 1;
        return 0;
    }
}
