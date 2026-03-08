#include<bits/stdc++.h>

using namespace std;

#define vi vector < int >
#define pii pair < int , int >
#define pb push_back
#define mp make_pair
#define ff first
#define ss second
#define foreach(it,v) for( __typeof((v).begin())it = (v).begin() ; it != (v).end() ; it++ )
#define ll long long
#define llu unsigned long long
#define MOD 1000000007
#define INF 0x3f3f3f3f
#define dbg(x) { cout<< #x << ": " << (x) << endl; }
#define dbg2(x,y) { cout<< #x << ": " << (x) << " , " << #y << ": " << (y) << endl; }
#define all(x) x.begin(),x.end()
#define mset(x,v) memset(x, v, sizeof(x))
#define sz(x) (int)x.size()

void mulmat(ll a[][2],ll b[][2])
{
     int i,j,k;
     ll c[2][2];
     for(i=0;i<2;i++)
     {
        for(j=0;j<2;j++)
        {
            c[i][j]=0;
            for(k=0;k<2;k++)
            {

                    c[i][j] += (a[i][k]*b[k][j])%MOD;
                    if(c[i][j] >= MOD)
                    	c[i][j] -= MOD;
                    if(c[i][j] < 0)
                    	c[i][j] += MOD;
            }
        }
     }
     for(i=0;i<2;i++)
     {
        for(j=0;j<2;j++)
        a[i][j] = c[i][j];
     }
}
ll fib(ll n)
{
    ll res[2][2]={1,0,
                  0,1};

    ll X[2][2]={1,1,
                1,0};
    while(n>0)
    {
       if(n&1)
       {
              mulmat(res,X);
       }
       n>>=1;
       mulmat(X,X);
    }
    return res[0][1];
}

ll sum(ll n)
{
    if(n <= 0)
        return 0;
    ll ret;
    if(n&1)
        ret = (fib(n-1)+1+MOD)%MOD;
    else
        ret = (-fib(n-1)+1+MOD)%MOD;
    return ret;
}

int main()
{
    int t;
    scanf("%d",&t);
    assert(1 <= t && t <= 10000);
    while(t--)
    {
        ll l,r;
        scanf("%lld%lld",&l,&r);
        assert(0 <= l && l <= 1e15);
        assert(l <= r && r <= 1e15);
        ll ans = (sum(r) - sum(l-1) + MOD)%MOD;
        printf("%lld\n",ans);
    }
    return 0;
}

