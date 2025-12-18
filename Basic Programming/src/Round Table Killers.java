import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        int X = sc.nextInt();

        List<Integer> people = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            people.add(i);
        }

        int idx = X - 1; // 0-based index

        while (people.size() > 1) {
            int currentPerson = people.get(idx);
            int kills = currentPerson % K;

            // If remaining people <= kills, current person wins
            if (people.size() <= kills) {
                System.out.println(currentPerson);
                return;
            }

            // Kill next `kills` people clockwise
            for (int i = 0; i < kills; i++) {
                int killIdx = (idx + 1) % people.size();
                people.remove(killIdx);
                if (killIdx < idx) idx--; // adjust index
            }

            // Move gun to next person
            idx = (idx + 1) % people.size();
        }

        // Only one person left
        System.out.println(people.get(0));
    }
}
