import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long T = sc.nextLong();
        long D = sc.nextLong();
        long x = sc.nextLong();

        if (x == T) {
            System.out.println("YES");
        } else if (x < T) {
            System.out.println("NO");
        } else {
            long diff = x - T;
            if (diff >= D && ((diff - D) % D == 0 || (diff - D - 1) % D == 0)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }
}

/*
✅ Problem Explanation :
Jack has a machine that runs in a certain pattern:

It first starts at time T seconds.

Then every D seconds, it runs twice:

First at time T + D

Then again at time T + D + 1

This pattern continues for every next D seconds:

So machine runs at times:
T, T+D, T+D+1, T+2D, T+2D+1, T+3D, T+3D+1, ...

Now Jack wants to destroy the machine at time x.
He can only destroy it if the machine is not running at time x.

🎯 Goal:
Print "YES" if the machine is running at time x,
Print "NO" if the machine is not running at time x.
*/
