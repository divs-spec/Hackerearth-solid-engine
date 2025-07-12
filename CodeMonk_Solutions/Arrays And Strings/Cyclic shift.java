import java.util.*;
class TestClass {
    public static int func(String s, int n, int k){
        Queue<String> queue = new LinkedList<>();
        for(int i = 0; i < n ; i++){
            char ch = s.charAt(i);
            queue.add(Character.toString(ch));
        }
        TreeMap<Long, Long> hm = new TreeMap<>();
        for(long i = 0; i < n; i++){
            String res = String.join("",queue);
            long num = Long.parseLong(res);
            if(hm.containsKey(num) == false){
                hm.put(num,i);
            }
            else
            {
                break;
            }
            String x = queue.poll();
            queue.add(x);
        }
        long x = hm.lastKey();
        long y = hm.get(x);
        return (int)(y*k)+(k-1);
    }
    public static void main(String args[] ) throws Exception {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t > 0){
            int n = sc.nextInt();
            int k = sc.nextInt();
            String s = sc.next();
            int x = func(s,n,k);
            System.out.println(x);
            --t;
        }
    }
}
