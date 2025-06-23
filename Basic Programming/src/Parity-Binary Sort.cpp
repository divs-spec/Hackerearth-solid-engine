import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            String[] input = br.readLine().split(" ");
            List<Integer> evenBits = new ArrayList<>();
            List<Integer> oddBits = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                int num = Integer.parseInt(input[i]);
                int setBits = Integer.bitCount(num); // Java built-in popcount
                if (setBits % 2 == 0)
                    evenBits.add(num);
                else
                    oddBits.add(num);
            }

            Collections.sort(evenBits);
            Collections.sort(oddBits);

            StringBuilder result = new StringBuilder();
            for (int num : evenBits)
                result.append(num).append(" ");
            for (int num : oddBits)
                result.append(num).append(" ");
            System.out.println(result.toString().trim());
        }
    }
}
