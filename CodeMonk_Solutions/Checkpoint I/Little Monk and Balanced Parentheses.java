import java.util.*;

public class Main {

    static class Pair {
        int value, status;
        Pair(int value, int status) {
            this.value = value;
            this.status = status;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int ans = 0, tmp = 0;

        Stack<Pair> stack = new Stack<>();
        ArrayList<Pair> v = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int c = sc.nextInt();
            v.add(new Pair(c, -1));
            if (stack.isEmpty()) {
                if (c > 0)
                    stack.push(new Pair(c, i));
            } else {
                if (c < 0 && stack.peek().value == -c) {
                    v.set(i, new Pair(c, 1));
                    int idx = stack.peek().status;
                    v.set(idx, new Pair(v.get(idx).value, 1));
                    stack.pop();
                } else {
                    stack.push(new Pair(c, i));
                }
            }
        }

        for (int i = 0; i < v.size(); i++) {
            if (i != 0 && v.get(i).status == 1 && v.get(i - 1).status == 1) {
                if (v.get(i).value < 0) {
                    tmp += 2;
                }
            } else {
                tmp = 0;
            }
            ans = Math.max(ans, tmp);
        }

        System.out.println(ans);
    }
}
