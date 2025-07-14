import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Fast input and output
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine());
        TreeMap<Long, Integer> map = new TreeMap<>();

        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            String[] parts = line.split(" ");

            String command = parts[0];

            if (command.equals("Push")) {
                long x = Long.parseLong(parts[1]);
                map.put(x, map.getOrDefault(x, 0) + 1);
            } else if (map.isEmpty()) {
                sb.append("-1\n");
                continue;
            } else {
                if (command.equals("CountHigh")) {
                    long max = map.lastKey();
                    sb.append(map.get(max)).append("\n");
                } else if (command.equals("CountLow")) {
                    long min = map.firstKey();
                    sb.append(map.get(min)).append("\n");
                } else if (command.equals("Diff")) {
                    if (map.size() == 1 && map.firstEntry().getValue() == 1) {
                        sb.append("0\n");
                        map.pollFirstEntry();  // remove the only element
                    } else {
                        // Get max
                        long max = map.lastKey();
                        int maxCount = map.get(max);
                        if (maxCount == 1) {
                            map.remove(max);
                        } else {
                            map.put(max, maxCount - 1);
                        }

                        // Get min
                        long min = map.firstKey();
                        int minCount = map.get(min);
                        if (minCount == 1) {
                            map.remove(min);
                        } else {
                            map.put(min, minCount - 1);
                        }

                        sb.append(max - min).append("\n");
                    }
                }
            }
        }

        System.out.print(sb);
    }
}
