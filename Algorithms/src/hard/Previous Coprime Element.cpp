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

const int maxm = 1e5+2;
vector<int>primes(maxm);
map<int,int>empty_map;
vector<int>a;

void precomp()
{   
    for(int i=0;i<maxm;i++){
        primes[i] = i;
    }
    for(int i=2;i<maxm;i++){
        if(primes[i] == i){
            for(int j=2*i;j<maxm;j+=i){
                primes[j] = i;
            }
        }
    }
}

vector<P>uniqueFactors(int x)
{
    map<int,int>visFacts;
    int ok = x;
    vector<int>facts;
    while(ok > 1){
        if(!visFacts[primes[ok]]){
            visFacts[primes[ok]] = true;
            facts.pb(primes[ok]);
        }
        ok /= primes[ok];
    }
    int me = (1LL<<(facts.size()));
    vector<P>allProducts;
    for(int j=1;j<me;j++){
        int bits = 0;
        int prod = 1;
        for(int k=0;k<facts.size();k++){
            if(j&(1LL<<k)){
                prod *= facts[k];
                bits++;
            }
        }
        if(bits&1){
            allProducts.pb({prod,1});
        }
        else{
            allProducts.pb({prod,-1});
        }
    }
    return allProducts;
}

class items {
public:
    int comps;
    map<int, int> ok;

    items() : comps(0) {}

    void init(int val) {
        comps = 1;
        for (auto& factor : uniqueFactors(val)) {
            ok[factor.F] += 1;
        }
    }

    bool check(int val) {
        int count = comps;
        for (auto& x : uniqueFactors(val)) {
            count -= ok[x.F]*x.S;
        }
        return count > 0;
    }
};

class segtree {
public:
    vector<items> nodes;
    vector<int> arr;

    segtree(const vector<int>& input) : arr(input) {
        nodes.resize(4 * input.size());
        build(0, input.size(), 1);
    }

    void build(int x, int y, int k) {
        if (x + 1 == y) {
            nodes[k].init(arr[x]);
            return;
        }
        int mid = (x + y) / 2;
        build(x, mid, 2 * k);
        build(mid, y, 2 * k + 1);

        for (const auto& x : nodes[2 * k].ok) {
            nodes[k].ok[x.F] += x.S;
        }
        for (const auto& x : nodes[2 * k + 1].ok) {
            nodes[k].ok[x.F] += x.S;
        }

        nodes[k].comps = nodes[2 * k].comps + nodes[2 * k + 1].comps;
    }

    int query(int x, int y, int k, int pos) {
        if ((y-x) == 1) {
            if(x >= pos){
                return -1;
            }
            if(gcd(a[pos],a[x])!=1){
                return -1;
            }
            return x;
        }
        int mid = (x + y) / 2;
        if (mid < pos and nodes[2 * k + 1].check(arr[pos])) {
            int curr = query(mid, y, 2 * k + 1, pos);
            if (curr != -1) {
                return curr;
            }
        }
        return query(x, mid, 2 * k, pos);
    }
};

void solve()
{   
    int n; cin>>n;
    a.clear();
    a.resize(n);
    for(auto &x : a){
        cin>>x;
    }
    segtree tree(a);
    for (int i = 0; i < a.size(); ++i) {
        cout << tree.query(0, n, 1, i) << " ";
    }
    cout<<endl;
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
    precomp();
    int t; cin>>t;
    while(t--)
    solve();
    return 0;
}
 
// 1LL check ?
