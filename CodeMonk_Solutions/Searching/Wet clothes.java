import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); // size of rain array
        int m = sc.nextInt(); // size of dry array
        int g = sc.nextInt(); // unused, but taken as input

        int[] arrRain = new int[n];
        for (int i = 0; i < n; i++) {
            arrRain[i] = sc.nextInt();
        }

        int[] arrDry = new int[m];
        for (int i = 0; i < m; i++) {
            arrDry[i] = sc.nextInt();
        }

        int maxDiff = 0;

        for (int i = 0; i < n - 1; i++) {
            int diff = arrRain[i + 1] - arrRain[i];
            maxDiff = Math.max(maxDiff, diff);
        }

        int count = 0;
        for (int i = 0; i < m; i++) {
            if (arrDry[i] <= maxDiff) {
                count++;
            }
        }

        System.out.println(count);
    }
}
