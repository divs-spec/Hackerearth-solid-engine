import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt(); // number of test cases

        while (t-- > 0) {
            int n = sc.nextInt(); // size of array
            int k = sc.nextInt(); // number of queries
            int[] a = new int[n];
            int[] q = new int[k];

            for (int i = 0; i < n; i++)
                a[i] = sc.nextInt();

            for (int i = 0; i < k; i++)
                q[i] = sc.nextInt();

            Stack<Integer> st = new Stack<>();
            Stack<Integer> st1 = new Stack<>();
            int[] pge = new int[n];
            int[] nge = new int[n];

            Arrays.fill(pge, -1);
            Arrays.fill(nge, n);

            // Compute PGE and NGE
            for (int i = 0, j = n - 1; i < n; i++, j--) {
                while (!st.isEmpty() && a[st.peek()] <= a[i])
                    st.pop();
                while (!st1.isEmpty() && a[st1.peek()] <= a[j])
                    st1.pop();
                if (!st.isEmpty())
                    pge[i] = st.peek();
                if (!st1.isEmpty())
                    nge[j] = st1.peek();
                st.push(i);
                st1.push(j);
            }

            // Process queries
            for (int i = 0; i < k; i++) {
                int x = q[i] - 1; // 0-based index
                int seg = nge[x] - pge[x] - 1;
                System.out.println(seg);
            }
        }

        sc.close();
    }
}
