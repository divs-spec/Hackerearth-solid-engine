import java.io.*;
import java.util.*;

public class Main {
    static class Crew {
        int height, start, end;

        Crew(int height, int start, int end) {
            this.height = height;
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) throws IOException {
        // Fast input/output
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int HH = Integer.parseInt(st.nextToken()); // Unused
        int c = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        List<Crew> crewList = new ArrayList<>();
        int maxHeight = 0;

        for (int i = 0; i < c; i++) {
            st = new StringTokenizer(br.readLine());
            int height = Integer.parseInt(st.nextToken());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            crewList.add(new Crew(height, start, end));
            maxHeight = Math.max(maxHeight, height);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int height = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            if (height > maxHeight) {
                sb.append("YES\n");
            } else {
                String ans = "YES";
                for (Crew m : crewList) {
                    if (m.start <= time && time <= m.end && m.height >= height) {
                        ans = "NO";
                        break;
                    }
                }
                sb.append(ans).append("\n");
            }
        }

        System.out.print(sb);
    }
}
