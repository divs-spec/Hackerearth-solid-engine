import java.util.Scanner;

 

public class Main {

 

static Scanner sc = new Scanner(System.in);

 

public static final double eps = 1e-9;

 

public static final int MAX = 200009;

 

public static long[] a = new long[MAX];

public static long[] b = new long[MAX];

 

public static void main(String[] args) {

 int i = 0;

 int j = 0;

 int k = 0;

 int n = 0;

 String tempVar = sc.next();

 if (tempVar != null) {

  n = Integer.parseInt(tempVar);

 }

 long sum1 = 0;

 long sum2 = 0;

 long c1 = 0;

 long c2 = 0;

 for (i = 0; i < n; i++) {

  String tempVar2 = sc.next();

  if (tempVar2 != null) {

   a[i] = Long.parseLong(tempVar2);

  }

  sum1 += a[i];

  c1 = c1 | a[i];

 }

 for (i = 0; i < n; i++) {

  String tempVar3 = sc.next();

  if (tempVar3 != null) {

   b[i] = Long.parseLong(tempVar3);

  }

  sum2 += b[i];

  c2 = c2 | b[i];

 }

 sum1 -= c1;

 sum2 -= c2;

 if (sum1 > sum2) {

  System.out.print(1);

  System.out.print(" ");

  System.out.print(sum1 - sum2);

  System.out.print("\n");

 } else if (sum1 < sum2) {

  System.out.print(2);

  System.out.print(" ");

  System.out.print(sum2 - sum1);

  System.out.print("\n");

 } else {

  System.out.print("-1\n");

 }

}

}

 

final class DefineConstants {

public static final int MOD = 1000000007;

}
