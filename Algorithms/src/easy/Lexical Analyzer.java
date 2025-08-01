import java.util.*;

public class UniqueKeyCounter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = Integer.parseInt(sc.nextLine());

        Set<String> uniqueKeys = new HashSet<>();

        for (int i = 0; i < k; i++) {
            String line = sc.nextLine().trim();
            String[] parts = line.split("=");

            if (parts.length > 0) {
                uniqueKeys.add(parts[0].trim());
            }
        }

        System.out.println(uniqueKeys.size());
    }
}
