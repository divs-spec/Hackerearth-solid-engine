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

int n,m,x,y;
int dx[4] = {0,0,1,-1};
int dy[4] = {1,-1,0,0};
int dp[26][7][7][64][64];

int calcIntro(int pos,int introMask,int extroMask)
{
    int r = pos/m;
    int c = pos - (r*m);
    int ans = 0;
    if(r > 0){
        if(introMask&(1<<(m-1))){
            ans -= 180;
        }
        if(extroMask&(1<<(m-1))){
            ans -= 30;
        }
    }
    if(c > 0){
        if(introMask&1){
            ans -= 180;
        }
        if(extroMask&1){
            ans -= 30;
        }
    }
    return ans;
}

int calcExtro(int pos,int introMask,int extroMask)
{
    int r = pos/m;
    int c = pos - (r*m);
    int ans = 0;
    if(r > 0){
        if(introMask&(1<<(m-1))){
            ans -= 30;
        }
        if(extroMask&(1<<(m-1))){
            ans += 120;
        }
    }
    if(c > 0){
        if(introMask&1){
            ans -= 30;
        }
        if(extroMask&1){
            ans += 120;
        }
    }
    return ans;
}

int checker(int pos,int introLeft,int extroLeft,int introMask,int extroMask)
{
    if(pos >= (n*m)){
        return 0;
    }
    if(dp[pos][introLeft][extroLeft][introMask][extroMask] != -1){
        return dp[pos][introLeft][extroLeft][introMask][extroMask];
    }   
    int newIntroMask = (introMask<<1)&63;
    int newExtroMask = (extroMask<<1)&63;
    int ans = checker(pos+1,introLeft,extroLeft,newIntroMask,newExtroMask);
    if(introLeft){
        ans = max(ans,360 + checker(pos+1,introLeft-1,extroLeft,newIntroMask+1,newExtroMask) + calcIntro(pos,introMask,extroMask));
    }
    if(extroLeft){
        ans = max(ans,120 + checker(pos+1,introLeft,extroLeft-1,newIntroMask,newExtroMask+1) + calcExtro(pos,introMask,extroMask));
    }
    return dp[pos][introLeft][extroLeft][introMask][extroMask] = ans;
}

void solve()
{
    cin>>n>>m>>x>>y;
    memset(dp,-1,sizeof dp);
    int ans = checker(0,x,y,0,0);
    cout<<ans<<endl;
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
