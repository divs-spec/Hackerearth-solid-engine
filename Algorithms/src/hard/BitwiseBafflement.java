import java.io.*;
import java.util.*;
 
public class Main {
    // Function to count set bits in an integer
    static int countSetBits(int n) {
        return Integer.bitCount(n);
    }
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
 
        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            int[] A = new int[N];
            String[] parts = br.readLine().split(" ");
            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(parts[i]);
            }
 
            long count = 0;
            for (int i = 0; i < N; i++) {
                int xor = 0;
                int and = A[i];
                for (int j = i; j < N; j++) {
                    xor ^= A[j];
                    and &= A[j];
                    int andBits = countSetBits(and);
                    int xorBits = countSetBits(xor);
                    if (andBits % 2 == 1 && xorBits % 2 == 0) {
                        count++;
                    }
 
                    // Optimization: if AND becomes 0, all further ANDs will be 0
                    if (and == 0) break;
                }
            }
 
            System.out.println(count);
        }
    }
}
