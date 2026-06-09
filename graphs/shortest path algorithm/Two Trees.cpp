#include<bits/stdc++.h>
#define ll           long long int
#define pb           push_back
#define F            first
#define S            second
using namespace std;
void solve()
{
    int n;
    cin>>n;
    vector<vector<pair<int,int>>> g(n);
    for(int i=1;i<n;++i)
    {
        int u,v,w;
        cin>>u>>v>>w;--u;--v;
        g[u].pb({v,w});
        g[v].pb({u,w});
    } 
    bool visit[n]={0}; 
    vector<vector<int>> dp(n,vector<int>(20)), p(n,vector<int>(20));
    vector<int> w(n),a(n), h(n);
    vector<ll> s(n);s[0]=0;
    for(int i=0;i<n;++i)cin>>w[i];
    h[0]=1;
    queue<int> que;que.push(0);
    while(!que.empty())
    {
        auto u=que.front();
        que.pop();
        visit[u]=1;
        for(int i=1;(h[u]-(1<<i))>0;++i)
        {
            int m=p[u][i-1];
            p[u][i]=p[m][i-1];
            int x=dp[m][i-1];
            int y=dp[u][i-1];
            if((w[y]+s[y])<(w[x]+s[x]))dp[u][i]=y;
            else dp[u][i]=x;
        }
        for(auto v:g[u])
        {
            if(visit[v.F])continue;
            p[v.F][0]=u;
            h[v.F]=h[u]+1;
            s[v.F]=v.S+s[u];
            int x=a[u];
            if((s[v.F]-s[x]+w[x])<(w[v.F]))a[v.F]=x;
            else a[v.F]=v.F;
            if((w[v.F]+v.S)<w[u])dp[v.F][0]=v.F;
            else dp[v.F][0]=u;
            que.push(v.F);
        }
    }
    int q;
    cin>>q;
    while(q--)
    {
        int u,v;
        cin>>u>>v;--u;--v;
        ll ans=(s[u]-s[v])<<1;
        int x=a[u],y=u,r=u;
        for(int i=19;i>=0;--i)
        {
            if((h[r]-(1<<i))>=(h[v]))
            {
                int z=dp[r][i];
                if((w[z]+s[z])<=(w[y]+s[y]))y=z;
                r=p[r][i];
            }
        }
        if(h[y]<h[x])
        {
            ll t=w[x]+w[y]-(s[x]-s[y]);
            if(t<0)ans+=t;
        }
        cout<<ans<<"\n";
    }
}
int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    solve();
    return 0;
}
