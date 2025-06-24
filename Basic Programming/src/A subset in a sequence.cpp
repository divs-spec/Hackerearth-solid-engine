#include <bits/stdc++.h>
using namespace std;
long pon(long,long);
 
 
//power function is for printing all number.
void power(long long p)
{
   long i=0;
   while(p)
       {
           if(p%2==1)
           {
               cout<<pon(p,i)<<' ';
           }
           p=p/2;
           i++;
       }
       cout<<'\n';
}
 
 
 
//pon function is for power of 3.
long pon(long p,long i)
{
   long j,k=1;
   for(j=1;j<=i;j++)
       k*=3;
   return k;
} 
 
 
int main ()
{
   int T;
   long n,i=0,p;
   cin>>T;
   while(T--)
   {
       cin>>n;
       p=n;
       while(n)
       {
           if(n%2==1)
           {
               i++;
           }
           n=n/2;
       }
       cout<<i<<'\n';
       power(p);
       i=0;
   }
   return 0;
}
