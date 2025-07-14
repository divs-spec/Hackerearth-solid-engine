// TLE : 
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] v = new int[n];
        for (int i = 0; i < n; i++) {
            v[i] = sc.nextInt();
        }

        Arrays.sort(v);

        int q = sc.nextInt();
        while (q-- > 0) {
            int k = sc.nextInt();
            char c = sc.next().charAt(0);

            if (c == 'S') {
                System.out.println(v[k - 1]);  // k-th smallest
            } else {
                System.out.println(v[n - k]);  // k-th largest
            }
        }

        sc.close();
    }
}

// NO TLE :

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Fast I/O setup
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        StringTokenizer st;

        // Read n
        int n = Integer.parseInt(br.readLine());
        int[] v = new int[n];

        // Read array
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            v[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(v);  // Sort the array

        // Read q
        int q = Integer.parseInt(br.readLine());

        // Process queries
        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            char c = st.nextToken().charAt(0);

            if (c == 'S') {
                out.println(v[k - 1]);  // k-th smallest
            } else {
                out.println(v[n - k]);  // k-th largest
            }
        }

        out.flush();
    }
}
