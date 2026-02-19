#include<bits/stdc++.h>
// #include <ext/pb_ds/assoc_container.hpp> 
// #include <ext/pb_ds/tree_policy.hpp> 
using namespace std;
// using namespace __gnu_pbds;
#define int long long int
#define ld long double
#define F first
#define S second
#define P pair<int,int>
#define V vector
#define eb emplace_back
#define pb push_back
#define mp make_pair
#define endl "\n"
#define all(x) x.begin(), x.end()
#define forstl(i, s)  for (__typeof ((s).end ()) i = (s).begin (); i != (s).end (); ++i)
// #pragma comment(linker, '/STACK:200000')        //prevent stack overflow ...STACK Limiter
// #pragma comment(linker, "/stack:200000000")
// #pragma GCC optimize("Ofast")
// #pragma GCC target("sse,sse2,sse3,ssse3,sse4,popcnt,abm,mmx,avx,tune=native")
#define time() cerr << "Time : " << (double)clock() / (double)CLOCKS_PER_SEC << "s\n" 
#define db(...) __f(#__VA_ARGS__, __VA_ARGS__)
const int MOD = 1e9 + 7;
const int MOD2 = 998244353;
ld PI=2*acos(0.0);
 
template <typename T> T gcd(T a, T b){return (b==0)?a:gcd(b,a%b);}
template <typename T> T lcm(T a, T b){return a*(b/gcd(a,b));}   
template <typename T> T mod_exp(T b, T p, T m){T x = 1;while(p){if(p&1)x=(x*b)%m;b=(b*b)%m;p=p>>1;}return x;}
template <typename T> T invFermat(T a, T p){return mod_exp(a, p-2, p);}
template <typename T> T exp(T b, T p){T x = 1;while(p){if(p&1)x=(x*b);b=(b*b);p=p>>1;}return x;}
// typedef tree<P, null_type, less<P>, rb_tree_tag, tree_order_statistics_node_update> ordered_multiset;
string tostring(int number){stringstream ss; ss<<number; return ss.str();}
int toint(const string &s) {stringstream ss; ss << s; int x; ss >> x; return x;}
 
template <typename Arg1>
void __f(const char* name, Arg1&& arg1) { cout << name << " : " << arg1 << '\n'; }
template <typename Arg1, typename... Args>
void __f(const char* names, Arg1&& arg1, Args&&... args) {
    const char* comma = strchr(names + 1, ',');
    cout.write(names, comma - names) << " : " << arg1 << " | "; __f(comma + 1, args...);
}   

int n,m;
vector<vector<P>>edges;

vector<bool> visited;
vector<int> tin, low;
vector<int> bridgelen;
int timer;

void dfs(int v, int p = -1) {
    visited[v] = true;
    tin[v] = low[v] = timer++;
    for (P to : edges[v]) {
        if (to.F == p) continue;
        if (visited[to.F]) {
            low[v] = min(low[v], tin[to.F]);
        } else {
            dfs(to.F, v);
            low[v] = min(low[v], low[to.F]);
            if (low[to.F] > tin[v]){
                bridgelen.pb(to.S);
            }
        }
    }
}

void find_bridges() {
    timer = 0;
    visited.assign(n, false);
    tin.assign(n, -1);
    low.assign(n, -1);
    for (int i = 0; i < n; ++i) {
        if (!visited[i]){
            dfs(i);
        }
    }
}

void solve()
{   
    cin>>n>>m;
    edges.resize(3*1e5+5);
    bridgelen.clear();
    vector<int>me(m);
    for(auto &x : me){
        cin>>x;
    }
    for(int i=0;i<m;i++){
        int x,y; cin>>x>>y;
        x--; y--;
        edges[x].pb({y,me[i]});
        edges[y].pb({x,me[i]});
    }
    find_bridges();
    sort(all(bridgelen));
    int minm1 = 0, minm2 = 0;
    int ok = 0;
    for(auto &x : bridgelen){
        if(!ok){
            minm1 += x;
        }
        else{
            minm2 += x;
        }
        ok ^= 1;
    }
    int maxm1 = 0, maxm2 = 0;
    ok = 0;
    reverse(all(bridgelen));
    for(auto &x : bridgelen){
        if(!ok){
            maxm1 += x;
        }
        else{
            maxm2 += x;
        }
        ok ^= 1;
    }
    maxm1 = max(maxm1,maxm2);
    minm1 = min(minm1,minm2);
    cout<<maxm1<<" "<<minm1<<endl;
}

int32_t main()
{
    ios_base:: sync_with_stdio(false);
    cin.tie(NULL); cout.tie(NULL);
    #ifndef ONLINE_JUDGE                    //file start
    freopen("input.txt","r",stdin);
    freopen("output.txt","w",stdout);   
    #endif                                  //file end
    // int cases=1;
    // int t; cin>>t;
    // while(t--)
    solve();
    return 0;
}
 
// 1LL check ?
