import java.io.*;
import java.util.*;

class TestClass {

    static class Fenwick {
        int[] tree;
        int n;

        Fenwick(int n){
            this.n = n;
            tree = new int[n+1];
        }

        void update(int i, int v){
            for(; i<=n; i+=i&-i)
                tree[i] += v;
        }

        int query(int i){
            int s=0;
            for(; i>0; i-=i&-i)
                s += tree[i];
            return s;
        }

        int range(int l,int r){
            return query(r)-query(l-1);
        }
    }

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] pos = new int[1000001];

        st = new StringTokenizer(br.readLine());

        for(int i=1;i<=N;i++){
            int v = Integer.parseInt(st.nextToken());
            pos[v] = i;
        }

        Fenwick ft = new Fenwick(1000000);

        for(int v=1; v<=1000000; v++){
            if(pos[v] > 0)
                ft.update(v,1);
        }

        int Q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while(Q-- > 0){

            st = new StringTokenizer(br.readLine());
            int L = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());

            int lo = K+1, hi = 1000000, ans = K+1;

            while(lo<=hi){

                int mid = (lo+hi)/2;

                int count = ft.range(K+1, mid);

                if(count == mid-K){
                    lo = mid+1;
                }else{
                    ans = mid;
                    hi = mid-1;
                }
            }

            sb.append(ans).append("\n");
        }

        System.out.print(sb);
    }
}
