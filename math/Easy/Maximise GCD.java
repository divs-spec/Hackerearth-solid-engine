import java.io.*;
import java.util.*;

class TestClass {

    static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine().trim());
        int[] A = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        int[] suffixGCD = new int[N];
        suffixGCD[N - 1] = A[N - 1];

        for (int i = N - 2; i >= 0; i--) {
            suffixGCD[i] = gcd(A[i], suffixGCD[i + 1]);
        }

        int maxG = 0;
        int answer = 0;

        for (int i = 0; i < N; i++) {
            if (suffixGCD[i] > maxG) {
                maxG = suffixGCD[i];
                answer = i;
            }
        }

        System.out.println(answer);
    }
}
