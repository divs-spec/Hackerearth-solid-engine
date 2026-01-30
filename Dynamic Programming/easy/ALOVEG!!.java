import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestClass {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim()); // number of test cases

        while (T-- > 0) {
            String s = br.readLine().trim();
            int n = s.length();

            int initialA = 0;

            // Count initial number of 'A'
            for (char c : s.toCharArray()) {
                if (c == 'A') {
                    initialA++;
                }
            }

            // Kadane's algorithm variables
            int maxEndingHere = Integer.MIN_VALUE;
            int maxSoFar = Integer.MIN_VALUE;

            for (int i = 0; i < n; i++) {
                int value;

                // Convert character to value
                if (s.charAt(i) == 'G') {
                    value = 1;   // flipping G -> A gives profit
                } else {
                    value = -1;  // flipping A -> G gives loss
                }

                if (maxEndingHere < 0) {
                    maxEndingHere = value;
                } else {
                    maxEndingHere += value;
                }

                maxSoFar = Math.max(maxSoFar, maxEndingHere);
            }

            // Since choosing a segment is mandatory
            int result = initialA + maxSoFar;
            System.out.println(result);
        }
    }
}
