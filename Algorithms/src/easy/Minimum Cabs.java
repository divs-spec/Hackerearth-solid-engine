import java.io.*;
import java.util.*;

class TestClass {
    static class Event {
        int time, delta;
        Event(int time, int delta) {
            this.time = time;
            this.delta = delta;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());

        List<Event> events = new ArrayList<>(2 * N);

        for (int i = 0; i < N; i++) {
            String[] parts = br.readLine().trim().split(" ");
            int h1 = Integer.parseInt(parts[0]);
            int m1 = Integer.parseInt(parts[1]);
            int h2 = Integer.parseInt(parts[2]);
            int m2 = Integer.parseInt(parts[3]);

            int start = h1 * 60 + m1;
            int end = h2 * 60 + m2;

            events.add(new Event(start, +1));
            events.add(new Event(end + 1, -1)); // inclusive end
        }

        events.sort((a, b) -> 
            a.time != b.time ? a.time - b.time : a.delta - b.delta
        );

        int curr = 0, ans = 0;
        for (Event e : events) {
            curr += e.delta;
            ans = Math.max(ans, curr);
        }

        System.out.println(ans);
    }
}
