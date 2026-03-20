import java.io.*;
import java.util.*;

public class Main {

    static long checker1(long n) {
        String s = String.valueOf(n);
        long ans = 0;
        int index = -1;

        for (int i = 0; i < s.length(); i++) {
            int digit = s.charAt(i) - '0';
            if (((digit % 2) & 1) == 0) {
                index = i;
                break;
            }
        }

        if (index == -1) return n;

        for (int i = 0; i < index; i++) {
            ans = ans * 10 + (s.charAt(i) - '0');
        }

        ans = ans * 10 + (s.charAt(index) - '0' - 1);

        for (int i = index + 1; i < s.length(); i++) {
            ans = ans * 10 + 9;
        }

        return ans;
    }

    static long checker2(long n) {
        String s = String.valueOf(n);
        long ans = 0;
        int index = -1;

        for (int i = 0; i < s.length(); i++) {
            int digit = s.charAt(i) - '0';
            if ((digit & 1) == 0) {
                index = i;
                break;
            }
        }

        if (index == -1) return n;

        for (int i = 0; i < index; i++) {
            ans = ans * 10 + (s.charAt(i) - '0');
        }

        ans = ans * 10 + (s.charAt(index) - '0' + 1);

        for (int i = index + 1; i < s.length(); i++) {
            ans = ans * 10 + 1;
        }

        return ans;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());

        long minBest = checker1(n);
        long maxBest = checker2(n);

        if (Math.abs(n - minBest) <= Math.abs(n - maxBest)) {
            System.out.println(minBest);
        } else {
            System.out.println(maxBest);
        }
    }
}
