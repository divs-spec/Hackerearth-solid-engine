import java.io.*;
import java.util.*;
 
class TestClass {
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
 
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
 
        int[] A = new int[N + 1];
        st = new StringTokenizer(br.readLine());
 
        Map<Integer, List<Integer>> map = new HashMap<>();
 
        for (int i = 1; i <= N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
            map.computeIfAbsent(A[i], x -> new ArrayList<>()).add(i);
        }
 
        int Q = Integer.parseInt(br.readLine());
 
        StringBuilder sb = new StringBuilder();
 
        while (Q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int L = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());
 
            int candidate = K + 1;
 
            while (true) {
                List<Integer> list = map.get(candidate);
                if (list == null) break;
 
                int pos = Collections.binarySearch(list, L);
                if (pos < 0) pos = -pos - 1;
 
                if (pos < list.size() && list.get(pos) <= R) {
                    candidate++;
                } else {
                    break;
                }
            }
 
            sb.append(candidate).append("\n");
        }
 
        System.out.print(sb);
    }
}
