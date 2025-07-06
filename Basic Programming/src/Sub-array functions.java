//APPROACH NO 1: (TLE) brute-force solution

/*
✅ Logic Recap
For each subarray [i, j]:

Extract subarray a[i..j]

Sort it

Compute:

res1 = XOR of m smallest

res2 = XOR of p largest

Update answer if (res1 & res2) is greater or gives a better [l, r]

*/

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        if (t < 1 || t > 5) throw new AssertionError();

        while (t-- > 0) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int p = sc.nextInt();

            if (m < 1 || m > 10 || p < 1 || p > 10 || Math.max(m, p) > n || n > 2000) {
                throw new AssertionError();
            }

            long[] a = new long[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextLong();
                if (a[i] < 0 || a[i] > 1e9) throw new AssertionError();
            }

            long bestVal = Long.MIN_VALUE;
            int bestL = -1, bestR = -1;

            for (int i = 0; i < n; i++) {
                for (int j = i + Math.max(m, p) - 1; j < n; j++) {
                    List<Long> temp = new ArrayList<>();
                    for (int k = i; k <= j; k++) {
                        temp.add(a[k]);
                    }

                    Collections.sort(temp);

                    // XOR of m smallest
                    long res1 = 0;
                    for (int k = 0; k < m; k++) {
                        res1 ^= temp.get(k);
                    }

                    // XOR of p largest
                    long res2 = 0;
                    for (int k = temp.size() - p; k < temp.size(); k++) {
                        res2 ^= temp.get(k);
                    }

                    long value = res1 & res2;

                    if (value > bestVal || (value == bestVal && (j - i > bestR - bestL))) {
                        bestVal = value;
                        bestL = i;
                        bestR = j;
                    }
                }
            }

            // Convert to 1-based index
            System.out.println((bestL + 1) + " " + (bestR + 1) + " " + bestVal);
        }
    }
}


//APPROACH NO 2: NO TLE (OPTIMIZED VERSION) Heap + XOR + Fast

/*
✅ Key Insight for Optimization
We don't need to try every [i, j] subarray explicitly. Instead:

💡 Instead of checking all subarrays, we fix:
A starting index i

Then greedily expand j to build valid [i, j] windows only when the number of elements is at least max(m, p)

✅ Optimized Algorithm (Correct + Fast):
For each i:

Use heaps to keep:

Top m smallest values (min-XOR)

Top p largest values (max-XOR)

Expand j = i to n-1

Insert a[j] into heaps

If we now have enough elements, compute:
value = XOR(m smallest) & XOR(p largest)
Track the best [l, r] and value

🔧 To maintain heaps:
Use 2 PriorityQueue<Long>s:

minHeap → keep m smallest

maxHeap → keep p largest

Also track current XOR of heap elements

✅ Why This Works
Total complexity: O(n^2 log(max(m,p))) → feasible due to m, p ≤ 10

Avoids sorting full subarrays

Only maintains m and p sized heaps

*/

import java.util.*;

public class Main {

    static int m, p;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        while (T-- > 0) {
            int n = sc.nextInt();
            m = sc.nextInt();
            p = sc.nextInt();
            long[] a = new long[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextLong();

            long bestVal = Long.MIN_VALUE;
            int bestL = -1, bestR = -1;

            for (int i = 0; i < n; i++) {
                PriorityQueue<Long> minHeap = new PriorityQueue<>((x, y) -> Long.compare(y, x)); // MaxHeap for m smallest
                PriorityQueue<Long> maxHeap = new PriorityQueue<>(); // MinHeap for p largest
                long xorMin = 0, xorMax = 0;

                for (int j = i; j < n; j++) {
                    long val = a[j];

                    // Maintain minHeap for m smallest
                    if (minHeap.size() < m) {
                        minHeap.add(val);
                        xorMin ^= val;
                    } else if (minHeap.peek() > val) {
                        long removed = minHeap.poll();
                        xorMin ^= removed;
                        minHeap.add(val);
                        xorMin ^= val;
                    }

                    // Maintain maxHeap for p largest
                    if (maxHeap.size() < p) {
                        maxHeap.add(val);
                        xorMax ^= val;
                    } else if (maxHeap.peek() < val) {
                        long removed = maxHeap.poll();
                        xorMax ^= removed;
                        maxHeap.add(val);
                        xorMax ^= val;
                    }

                    if (j - i + 1 >= Math.max(m, p)) {
                        long value = xorMin & xorMax;

                        if (value > bestVal || (value == bestVal && (j - i > bestR - bestL))) {
                            bestVal = value;
                            bestL = i;
                            bestR = j;
                        }
                    }
                }
            }

            System.out.println((bestL + 1) + " " + (bestR + 1) + " " + bestVal);
        }
    }
}
