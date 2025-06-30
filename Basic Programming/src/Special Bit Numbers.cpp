#include <bits/stdc++.h>

using namespace std;

const int N = 1e5 + 10;

 

int main()

{

   int n, q;

   cin >> n >> q;

   int prev = 0;

   int special_bits[N] = {0};

   for (int i = 0; i < n; ++i)

   {

       int x;

       cin >> x;

       for (int j = 0; j < 32; ++j)

       {

           if(x & (1 << j))

           {

               if(prev)

               {

                   special_bits[i + 1] = 1;

                   prev = 0;

                   break;

               }

               prev = 1;

           }

           else

               prev = 0;

       }

   }

   while(q--)

   {

       int ans = 0;

       int l, r;

       cin >> l >> r;

       for (int i = l; i <= r; ++i)

       {

           if (special_bits[i])

           {

               ans++;

           }

       }

       cout << ans << endl;

   }

 

   return 0;

}
