import java.io.*;
import java.util.*;
 
class TestClass {
    public static void main(String args[]) throws Exception {
 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
 
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
 
        int[] A = new int[N+1];
        Map<Integer,Integer> pos = new HashMap<>();
 
        st = new StringTokenizer(br.readLine());
 
        for(int i=1;i<=N;i++){
            A[i] = Integer.parseInt(st.nextToken());
            pos.put(A[i], i);
        }
 
        int Q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
 
        while(Q-- > 0){
 
            st = new StringTokenizer(br.readLine());
            int L = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());
 
            int candidate = K+1;
 
            while(true){
                Integer p = pos.get(candidate);
                if(p == null || p < L || p > R) break;
                candidate++;
            }
 
            sb.append(candidate).append("\n");
        }
 
        System.out.print(sb);
    }
}
