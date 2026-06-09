import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class TestClass {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter wr = new PrintWriter(System.out);
        int T = Integer.parseInt(br.readLine().trim());
        for(int t_i = 0; t_i < T; t_i++)
        {
            String x = br.readLine();

            String out_ = Solve(x);
            System.out.println(out_);
            
         }

         wr.close();
         br.close();
    }
    public static String Solve(String str) {

    if (str == null) {
        return "false";
    }

    String regex =
            "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}";

    return Pattern.matches(regex, str) ? "true" : "false";
}
}
