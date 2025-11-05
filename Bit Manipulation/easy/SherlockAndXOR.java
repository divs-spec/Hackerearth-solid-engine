import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SherlockAndXOR {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Read the number of testcases (T)
        int T = Integer.parseInt(br.readLine());

        // Process each testcase
        while (T-- > 0) {
            // Read N
            int N = Integer.parseInt(br.readLine());
            
            // Read the array elements on the next line
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            // Counters for odd and even numbers
            long countOdd = 0;
            long countEven = 0;
            
            // Iterate through all N elements
            for (int i = 0; i < N; i++) {
                // Read the number A_i
                // Since A_i can be up to 10^9, int is fine.
                int A_i = Integer.parseInt(st.nextToken());
                
                // Check if the number is odd or even
                // A number is odd if its least significant bit (LSB) is 1, or A_i % 2 != 0
                if (A_i % 2 != 0) {
                    countOdd++;
                } else {
                    countEven++;
                }
            }
            
            // The number of pairs (i, j) with i < j where XOR is odd
            // is (number of odd elements) * (number of even elements).
            // Since i=j pairs never satisfy the odd XOR condition (XOR is 0, which is even),
            // this product is the final answer for i <= j.
            long result = countOdd * countEven;
            
            // Print the required answer
            System.out.println(result);
        }
    }
}
