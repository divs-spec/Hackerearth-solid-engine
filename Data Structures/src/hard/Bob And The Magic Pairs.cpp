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

struct trie{
    trie *bitTrie[2] = {};
    int node_cnt = 0;

    void add(int r,int pos)
    {
        bool bit = r&(1LL<<pos);
        node_cnt++;
        if(bitTrie[bit] == nullptr){
            bitTrie[bit] = new trie();
        }
        if(pos >= 0){
            bitTrie[bit]->add(r,pos-1);
        }
    }

    int checker(int r, int y,int pos)
    {
        bool bit = r&(1LL<<pos);
        bool bitRange = y&(1LL<<pos);
        bool xors = (r^y)&(1LL<<pos);
        int ans = 0;
        if(bitRange and bitTrie[bit]!=nullptr){
            ans += bitTrie[bit]->node_cnt;
        }
        if(bitTrie[xors] != nullptr){
            ans += bitTrie[xors]->checker(r,y,pos-1);
        } 
        return ans;   
    }
};

void solve()
{
    int n,u,v; cin>>n>>u>>v;
    trie bitTrie;
    int ans = 0;
    for(int i=0;i<n;i++){
        int x; cin>>x;
        ans += bitTrie.checker(x,v+1LL,31) - bitTrie.checker(x,u,31);
        bitTrie.add(x,31);
    }
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
    int t; cin>>t;
    while(t--)
    solve();
    return 0;
}
 
// 1LL check ?
