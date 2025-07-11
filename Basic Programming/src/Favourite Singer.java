import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // Number of songs

        Map<Long, Long> singerCount = new HashMap<>();
        long maxFreq = 0;

        for (int i = 0; i < n; i++) {
            long singer = sc.nextLong(); // Use long for singer ID
            long freq = singerCount.getOrDefault(singer, 0L) + 1;
            singerCount.put(singer, freq);
            maxFreq = Math.max(maxFreq, freq);
        }

        // Count how many singers have maxFreq
        long favouriteSingers = 0;
        for (long freq : singerCount.values()) {
            if (freq == maxFreq) {
                favouriteSingers++;
            }
        }

        System.out.println(favouriteSingers);
    }
}
