import java.io.*;
import java.util.*;
 
public class Template implements Runnable {
 
 InputStream in;
 PrintWriter out;
 FastReader scan;
 
 void solve() {
 int t = scan.ni();
 while(t-- > 0) {
 int n = scan.ni();
 long k = scan.nl();
 long[] a = new long[n];
 long bitwiseOr = 0;
 
 for(int i = 0; i < n; i++) {
 a[i] = scan.nl();
 bitwiseOr |= a[i];
 }
 
 for(int i = 60; i >= 0; i--) {
 if(((1L << i) & bitwiseOr) == 0) {
 long minSteps = Long.MAX_VALUE;
 int mini = -1;
 for(int j = 0; j < n; j++) {
 long y = ((1L << i) - 1) & a[j];
 long steps = (1L << i) - y;
 if(steps <= k && steps < minSteps) {
 minSteps = steps;
 mini = j;
 }
 }
 if(mini != -1) {
 k -= minSteps;
 a[mini] += minSteps;
 }
 long orr = 0;
 for(int j = 0; j < n; j++) {
 orr |= a[j];
 }
 bitwiseOr = orr;
 }
 }
 out.println(bitwiseOr);
 }
 }
 
 public void run() {
 String INPUT = "C:/Users/tapan/OneDrive/Desktop/in.txt";
 oj = true;
 try {
 in = oj ? System.in : new FileInputStream(INPUT);
 } catch (FileNotFoundException e) {
 e.printStackTrace();
 }
 scan = new FastReader(in);
 out = new PrintWriter(System.out);
 
 long s = System.currentTimeMillis();
 solve();
 out.flush();
 tr(System.currentTimeMillis() - s + "ms");
 
 }
 
 public static void main(String[] args) throws Exception {
 new Thread(null, new Template(), "Template", 1 << 26).start();
 }
 
 static class FastReader {
 BufferedReader br;
 StringTokenizer st;
 
 public FastReader(InputStream in) {
 br = new BufferedReader(
 new InputStreamReader(in));
 }
 
 String next() {
 while (st == null || !st.hasMoreElements()) {
 try {
 st = new StringTokenizer(br.readLine());
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
 return st.nextToken();
 }
 
 int ni() {
 return Integer.parseInt(next());
 }
 
 long nl() {
 return Long.parseLong(next());
 }
 
 double nd() {
 return Double.parseDouble(next());
 }
 
 String ns() {
 String str = "";
 try {
 str = br.readLine();
 } catch (IOException e) {
 e.printStackTrace();
 }
 return str;
 }
 }
 
 private boolean oj = System.getProperty("ONLINE_JUDGE") != null;
 
 private void tr(Object... o) {
 if (!oj)
 System.out.println(Arrays.deepToString(o));
 }
 
}
