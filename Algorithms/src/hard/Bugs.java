import java.util.*;

public class Main {
    static class BIT {
        private int maxIdx;
        private int size;
        private TreeMap<Integer, Integer> bit;

        public BIT(int maxP) {
            this.maxIdx = maxP;
            this.size = 0;
            this.bit = new TreeMap<>();
        }

        public String processInput(int operation, int P) {
            if (operation == 2) {
                int k = size / 3 - 1;
                if (k < 0) {
                    return "Not enough enemies";
                } else {
                    return String.valueOf(getKthP(k));
                }
            } else {
                insert(P);
                return "";
            }
        }

        private void add(int idx, int val) {
            int i = idx;
            while (i <= maxIdx) {
                bit.put(i, bit.getOrDefault(i, 0) + val);
                i += i & -i;
            }
        }

        private int sumUpTo(int idx) {
            int i = idx;
            int sum = 0;
            while (i > 0) {
                sum += bit.getOrDefault(i, 0);
                i -= i & -i;
            }
            return sum;
        }

        private int binarySumSearch(int target) {
            if (size == 0) return -1;
            int start = 1;
            int end = maxIdx;
            while (start < end) {
                int mid = (start + end) / 2;
                int s = sumUpTo(mid);
                if (s < target) {
                    start = mid + 1;
                } else {
                    end = mid;
                }
            }
            int s = sumUpTo(start);
            return (s < target) ? -1 : start;
        }

        private void insert(int P) {
            int idx = maxIdx - P + 1;
            add(idx, 1);
            size += 1;
        }

        private int getKthP(int k) {
            int target = k + 1;
            int idx = binarySumSearch(target);
            if (idx < 0) return -1;
            return maxIdx - idx + 1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BIT bit = new BIT(1_000_000_000);

        int lines = sc.nextInt();
        while (lines-- > 0) {
            int op = sc.nextInt();
            if (op == 1) {
                int P = sc.nextInt();
                bit.processInput(op, P);
            } else {
                System.out.println(bit.processInput(op, 0));
            }
        }
    }
}
