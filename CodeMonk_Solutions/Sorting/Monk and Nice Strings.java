import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class TestClass {
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        String[] words = new String[N];

        for (int i = 0; i < N; i++) {
            words[i] = br.readLine();
        }

        for (int i = 0; i < N; i++) {
            int niceness = 0;
            for (int j = 0; j < i; j++) {
                if (words[j].compareTo(words[i]) < 0) {
                    niceness++;
                }
            }
            System.out.println(niceness);
        }
    }
}
