import java.io.*;
import java.util.*;

public class Main {
    static final int MAX = 100005;
    static long[] mal = new long[MAX];
    static long[] mar = new long[MAX];
    static long[] mil = new long[MAX];
    static long[] mir = new long[MAX];
    static long[] a = new long[MAX];
    static long[] mp = new long[MAX];
    static int[] freq = new int[1000005];
    static int n, x, k;

    static Stack<Integer> st = new Stack<>();

    static void great(boolean f) {
        if (f) {
            for (int i = 1; i <= n; i++) {
                while (!st.isEmpty() && a[i] > a[st.peek()]) {
                    mar[st.pop()] = i;
                }
                st.push(i);
            }
            while (!st.isEmpty()) {
                mar[st.pop()] = n + 1;
            }
        } else {
            for (int i = n; i >= 1; i--) {
                while (!st.isEmpty() && a[i] > a[st.peek()]) {
                    mal[st.pop()] = i;
                }
                st.push(i);
            }
            while (!st.isEmpty()) {
                mal[st.pop()] = 0;
            }
        }
    }

    static void smal(boolean f) {
        if (f) {
            for (int i = 1; i <= n; i++) {
                while (!st.isEmpty() && a[i] < a[st.peek()]) {
                    mir[st.pop()] = i;
                }
                st.push(i);
            }
            while (!st.isEmpty()) {
                mir[st.pop()] = n + 1;
            }
        } else {
            for (int i = n; i >= 1; i--) {
                while (!st.isEmpty() && a[i] < a[st.peek()]) {
                    mil[st.pop()] = i;
                }
                st.push(i);
            }
            while (!st.isEmpty()) {
                mil[st.pop()] = 0;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] first = br.readLine().split(" ");
        n = Integer.parseInt(first[0]);
        x = Integer.parseInt(first[1]);
        k = Integer.parseInt(first[2]);

        String[] data = br.readLine().split(" ");
        for (int i = 1; i <= n; i++) {
            a[i] = Long.parseLong(data[i - 1]);
            freq[(int)a[i]]++;
        }

        great(true);
        great(false);
        smal(true);
        smal(false);

        long mas = 0, mis = 0;
        for (int i = 1; i <= n; i++) {
            long leftMax = mar[i] - i;
            long rightMax = i - mal[i];
            long leftMin = mir[i] - i;
            long rightMin = i - mil[i];

            mp[i] = (leftMax * rightMax) - (leftMin * rightMin);

            mas += leftMax * rightMax * a[i];
            mis += leftMin * rightMin * a[i];
        }

        Arrays.sort(mp, 1, n + 1); // sort from 1 to n
        long sum = mas - mis;

        for (int i = n; i >= n - x + 1; i--) {
            if (mp[i] > 0) sum += mp[i];
        }

        System.out.println(sum);
    }
}
