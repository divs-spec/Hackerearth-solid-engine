import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Fast I/O
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        pw.flush(); // flush before reading input
        int N = Integer.parseInt(br.readLine());

        long[] num = new long[N];
        long sum = 0;

        pw.flush(); // ensure prompt appears before input

        String[] parts = br.readLine().trim().split("\\s+");
        for (int i = 0; i < N; i++) {
            num[i] = Long.parseLong(parts[i]);
            sum += num[i];
        }

        pw.println(sum);
        pw.flush();
    }
}
