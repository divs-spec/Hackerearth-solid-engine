import java.util.*;

public class InversionXOR {

    public static int fun(String m) {
        int len = m.length();
        int arrLen = 1 << len;
        int mVal = Integer.parseInt(m, 2);

        int[] arr = new int[arrLen];

        for (int i = 0; i < arrLen; i++) {
            arr[i] = i ^ mVal;
        }

        int count = 0;
        for (int i = 0; i < arrLen; i++) {
            for (int j = i + 1; j < arrLen; j++) {
                if (arr[i] > arr[j]) {
                    count++;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = Integer.parseInt(sc.nextLine());

        while (t-- > 0) {
            String m = sc.nextLine().trim();
            int ans = fun(m);
            System.out.println(ans);
        }

        sc.close();
    }
}
