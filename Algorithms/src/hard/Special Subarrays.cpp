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
#pragma comment(linker, "/stack:200000000")
#pragma GCC optimize("Ofast")
#pragma GCC target("sse,sse2,sse3,ssse3,sse4,popcnt,abm,mmx,avx,tune=native")
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

class node{
public:
    char data;
    unordered_map<char,node*>hash;
    bool isTerminal;
    //constructor
    node(char d){
        data=d;
        isTerminal=false;
    }
};

class trie{
    node* root;
public:
    trie(){
        root=new node('2');
    }
    //Insertion
    void addword(string &s)
    {
        node* temp=root;
        for(int i=0;i<s.length();i++)
        {
            char ch=s[i];
            if(temp->hash.count(ch)==0){
                node* child=new node(ch);
                temp->hash[ch]=child;
                temp=child;
            }
            else{
                temp=temp->hash[ch];
            }
        }
    }
    //Lookup
    int dfs(node *currentNode, int zeroesSeen, bool seenZero)
    {
        seenZero |= (currentNode->data == '0');
        zeroesSeen = (zeroesSeen + (currentNode->data == '0'))%MOD;
        int ans = 0LL;
        if(seenZero){
            ans = (zeroesSeen-1LL+MOD)%MOD;
        }
        for(auto &x : currentNode->hash){   
            ans = (ans + dfs(x.second,zeroesSeen,seenZero)%MOD)%MOD;
        }
        return ans;
    }

    node *getRoot(){
        return root;
    }
};

void solve()
{   
    int n; cin>>n;
    int ans = 0;
    trie uniquePrefixes;
    for(int i=0;i<n;i++){
        string s; cin>>s;
        uniquePrefixes.addword(s);
    }
    ans = uniquePrefixes.dfs(uniquePrefixes.getRoot(),0,false)%MOD;
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
