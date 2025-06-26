import java.io.*;

public class TestClass {
    public static void main(String[] args) throws IOException {
        String[] answers = {
            "", // dummy for index 0
            "1",
            "105263157894736842",
            "1034482758620689655172413793",
            "102564",
            "142857",
            "1016949152542372881355932203389830508474576271186440677966",
            "1014492753623188405797",
            "1012658227848",
            "10112359550561797752808988764044943820224719"
        };

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine().trim());
            System.out.println(answers[N]);
        }
    }
}
