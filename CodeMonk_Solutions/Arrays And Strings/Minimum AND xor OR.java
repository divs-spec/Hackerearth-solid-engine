import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
class TestClass {
    public static void main(String args[] ) throws Exception {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t > 0){
            int n = sc.nextInt();
            int[] arr = new int[n];
            for(int i = 0; i < n; i++){
                arr[i] = sc.nextInt();
            }
            int m = Integer.MAX_VALUE;
            int x = 0;
            Arrays.sort(arr);
            for(int i = 0 ; i < n-1; i++){
                x = (arr[i] ^ arr[i+1]);
                m = Math.min(m,x);
            }
            System.out.println(m);
            --t;
        }
    }
    
}
