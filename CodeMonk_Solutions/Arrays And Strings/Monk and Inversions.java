import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
class TestClass {
    public static int monkandinversions(int[][] nums, int n){
        int tmp = 0;
        for(int i = 0 ; i < n; i++){
            for(int j = 0; j < n; j++){
                for(int p = i ; p < n; p++){
                    for(int q = j; q < n; q++){
                        if(nums[i][j] > nums[p][q])
                        tmp++;
                    }
                }
            }
        }
        return tmp;
    }
    public static void main(String args[] ) throws Exception {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t > 0){
            int n = sc.nextInt();
            int[][] nums = new int[n][n];
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    nums[i][j] = sc.nextInt();
                }
            }
            int tmp = monkandinversions(nums,n);
            System.out.println(tmp);
            --t;
        }
    }
}
