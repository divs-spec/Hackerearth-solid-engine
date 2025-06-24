import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        while (T-- > 0) {
            String[] parts = br.readLine().split(" ");
            long L = Long.parseLong(parts[0]);
            long R = Long.parseLong(parts[1]);
            
            long answer = -1;
            int minSetBits = 64;
            
            for (int b = 0; b <= 60; b++) {
                long num = 1L << b;
                if (num >= L && num <= R) {
                    answer = num;
                    break; // single set bit => optimal
                }
            }

            if (answer == -1) {
                for (long num = L; num <= Math.min(R, L + 100); num++) {
                    int bits = Long.bitCount(num);
                    if (bits < minSetBits || (bits == minSetBits && num < answer)) {
                        minSetBits = bits;
                        answer = num;
                    }
                }
            }
            
            sb.append(answer).append('\n');
        }
        
        System.out.print(sb);
    }
}
