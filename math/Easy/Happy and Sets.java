import java.io.*;
import java.util.*;

public class TestClass {
    static final int MOD = 1000000007;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String[] tokens = br.readLine().split(" ");
        long result = 1;
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(tokens[i]);
            result = (result * (num + 1L)) % MOD;
        }
        result = (result - 1 + MOD) % MOD;  // remove empty subset and avoid negative
        System.out.println(result);
    }
}
