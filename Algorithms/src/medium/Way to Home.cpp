#include<bits/stdc++.h>
using namespace std;
#define mod 1000000007
const int N=1004;
long long int fact[N],invfact[N];
long long fastexp(int num,int p)
{
    if(p==0) return 1;
    long long res=fastexp(num,p/2);
    if(p%2==0)
        return res*res%mod;
    else
        return (((res*res)%mod)*num)%mod;
}
int main()
{
    //freopen("in10.txt","r",stdin);
    //freopen("out10.txt","w",stdout);
    int n,m;
    scanf("%d %d",&n,&m);
    int i;
    int h=n-1;
    int r=m-1;
    if(h+1<r)
        printf("0\n");
    else
    {
        fact[0]=1;
        invfact[0]=1;
        for(i=1;i<N;i++)
        {
            fact[i]=(fact[i-1]*i)%mod;
            invfact[i]=fastexp(fact[i],mod-2);
        }
        long long ans=(((fact[h+1]*invfact[r])%mod)*invfact[h+1-r])%mod;
        printf("%lld\n",ans);
    }
    return 0;
}

