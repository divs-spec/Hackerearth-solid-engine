import java.io.*;

public class TestClass {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine().trim());
            int highestPower = Integer.highestOneBit(N);
            System.out.println(highestPower);
        }
    }
}
