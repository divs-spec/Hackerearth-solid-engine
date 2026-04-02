import java.io.*;
import java.util.*;

class TestClass {
    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine().trim());
        StringBuilder out = new StringBuilder();

        while (T-- > 0) {
            long N = Long.parseLong(br.readLine().trim());

            if (N >= 4 && N % 2 == 0)
                out.append("YES\n");
            else
                out.append("NO\n");
        }

        System.out.print(out);
    }
}
