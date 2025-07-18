// Approach no 2: NO TLE

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Fast input setup
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        long[] boxes = new long[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            boxes[i] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(boxes);

        long ans = 1, prev = boxes[0], temp = 0, count = 1, t_count = 0;

        for (int i = 1; i < n; i++) {
            temp += boxes[i];
            t_count++;

            if (temp > prev && t_count > count) {
                prev = temp;
                temp = 0;
                count = t_count;
                t_count = 0;
                ans++;
            }
        }

        System.out.println(ans);
    }
}




// Approach no 1: TLE 
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        long[] boxes = new long[n];
        for (int i = 0; i < n; i++) {
            boxes[i] = sc.nextLong();
        }

        Arrays.sort(boxes);

        long ans = 1;
        long prev = boxes[0];
        long temp = 0;
        long count = 1;
        long t_count = 0;

        for (int i = 1; i < n; i++) {
            temp += boxes[i];
            t_count++;

            if (temp > prev && t_count > count) {
                prev = temp;
                temp = 0;
                count = t_count;
                t_count = 0;
                ans++;
            }
        }

        System.out.println(ans);
    }
}
