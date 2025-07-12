import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
class TestClass {
    public static void reverse(int[] nums, int st , int end){
        while(st < end){
            int temp = nums[st];
            nums[st] = nums[end];
            nums[end] = temp;
            st++;
            end--;
        }
    }
    public static void monkandrotation( int[] nums, int k){
        int n = nums.length;
        k = k%n;
       reverse(nums,0,n-1);
       reverse(nums,0,k-1);
       reverse(nums,k,n-1);
        for (int j : nums) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
    public static void main(String args[] ) throws Exception {
       Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t > 0){
            int n = sc.nextInt();
            int k = sc.nextInt();
            int[] arr = new int[n];
            for(int i = 0; i < n ; i++) {
                arr[i] = sc.nextInt();
            }
            monkandrotation(arr,k);
            --t;
        }
    }
}
