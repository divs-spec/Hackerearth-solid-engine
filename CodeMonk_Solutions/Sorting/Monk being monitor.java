import java.util.*;
import java.io.*;

class TestClass {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int test_case = sc.nextInt();
        
        while (test_case-- > 0) {
            int N = sc.nextInt();
            Map<Integer, Integer> freq = new HashMap<>();
            
            for (int i = 0; i < N; i++) {
                int num = sc.nextInt();
                freq.put(num, freq.getOrDefault(num, 0) + 1);
            }
            
            if (freq.size() < 2) {
                // No valid pair if only one unique height
                System.out.println(-1);
                continue;
            }
            
            Collection<Integer> values = freq.values();
            int maxCount = Collections.max(values);
            int minCount = Collections.min(values);
            int answer = maxCount - minCount;

            if (answer > 0) {
                System.out.println(answer);
            } else {
                System.out.println(-1);
            }
        }
        sc.close();
    }
}
