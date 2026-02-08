#include <bits/stdc++.h>
using namespace std;

#define ll long long
#define ld double
#define vi vector<int>
#define vll vector<ll >
#define endl "\n"
#define print(s) cerr<<#s<<" : ";for(auto i:(s))cerr<<i<<" ";cerr<<"\n";

struct base{
    ld x,y;
    base(){x=y=0;}
    base(ld _x, ld _y){x = _x,y = _y;}
    base(ld _x){x = _x, y = 0;}
    void operator = (ld _x){x = _x,y = 0;}
    ld real(){return x;}
    ld imag(){return y;}
    base operator + (const base& b){return base(x+b.x,y+b.y);}
    void operator += (const base& b){x+=b.x,y+=b.y;}
    base operator * (const base& b){return base(x*b.x - y*b.y,x*b.y+y*b.x);}
    void operator *= (const base& b){ld p = x*b.x - y*b.y, q = x*b.y+y*b.x; x = p, y = q;}
    void operator /= (ld k){x/=k,y/=k;}
    base operator - (const base& b){return base(x - b.x,y - b.y);}
    void operator -= (const base& b){x -= b.x, y -= b.y;}
    base conj(){ return base(x, -y);}
    base operator / (ld k) { return base(x / k, y / k);}
    void Print(){ cerr << x <<  " + " << y << "i\n";}
};
double PI = 2.0*acos(0.0);
const int MAXN = 19;
const int maxn = (1<<MAXN);
base W[maxn],invW[maxn], P1[maxn], Q1[maxn];
void precompute_powers(){
    for(int i = 0;i<maxn/2;i++){
        double ang = (2*PI*i)/maxn; 
        ld _cos = cos(ang), _sin = sin(ang);
        W[i] = base(_cos,_sin);
        invW[i] = base(_cos,-_sin);
    }
}
void fft (vector<base> & a, bool invert) {
    int n = (int) a.size();
 
    for (int i=1, j=0; i<n; ++i) {
        int bit = n >> 1;
        for (; j>=bit; bit>>=1)
            j -= bit;
        j += bit;
        if (i < j)
            swap (a[i], a[j]);
    }
    for (int len=2; len<=n; len<<=1) {
        for (int i=0; i<n; i+=len) {
            int ind = 0,add = maxn/len;
            for (int j=0; j<len/2; ++j) {
                base u = a[i+j],  v = (a[i+j+len/2] * (invert?invW[ind]:W[ind]));
                a[i+j] = (u + v);
                a[i+j+len/2] = (u - v);
                ind += add;
            }
        }
    }
    if (invert) for (int i=0; i<n; ++i) a[i] /= n;
}
inline ll mul(ll a, ll b, ll mod){
    return (a*b)%mod;
}

// 4 FFTs in total for a precise convolution
void mul_big_mod(vll &a, vll & b, ll mod){
    int n1 = a.size(),n2 = b.size();
    int final_size = a.size() + b.size() - 1;
    int n = 1;
    while(n < final_size) n <<= 1;
    vector<base> P(n), Q(n);
    int SQRTMOD = (int)sqrt(mod) + 10;
    for(int i = 0;i < n1;i++) P[i] = base(a[i] % SQRTMOD, a[i] / SQRTMOD);
    for(int i = 0;i < n2;i++) Q[i] = base(b[i] % SQRTMOD, b[i] / SQRTMOD);
    fft(P, 0);
    fft(Q, 0);
    base A1, A2, B1, B2, X, Y;
    for(int i = 0; i < n; i++){
        X = P[i];
        Y = P[(n - i) % n].conj();
        A1 = (X + Y) * base(0.5, 0);
        A2 = (X - Y) * base(0, -0.5);
        X = Q[i];
        Y = Q[(n - i) % n].conj();
        B1 = (X + Y) * base(0.5, 0);
        B2 = (X - Y) * base(0, -0.5);
        P1[i] = A1 * B1 + A2 * B2 * base(0, 1);
        Q1[i] = A1 * B2 + A2 * B1;
    }
    for(int i = 0; i < n; i++) P[i] = P1[i], Q[i] = Q1[i];
    fft(P, 1);
    fft(Q, 1);
    a.resize(final_size);
    for(int i = 0; i < final_size; i++){
        ll x = (ll)(P[i].real() + 0.5);
        ll y = (ll)(P[i].imag() + 0.5) % mod;
        ll z = (ll)(Q[i].real() + 0.5);
        a[i] = (x + ((y * SQRTMOD + z) % mod) * SQRTMOD) % mod;
    }
}
const int N = 1e5 + 10, mod = 1e9 + 7;
int fact[N], invfact[N];
inline int add(int x, int y){ x += y; if(x >= mod) x -= mod; return x;}
inline int sub(int x, int y){ x -= y; if(x < 0) x += mod; return x;}
inline int mul(int x, int y){ return (((ll) x) * y) % mod;}
inline int powr(int a, ll b){
    int x = 1 % mod;
    while(b){
        if(b & 1) x = mul(x, a);
        a = mul(a, a);
        b >>= 1;
    }
    return x;
}
inline int inv(int a){ return powr(a, mod - 2);}
void pre(){
    fact[0] = invfact[0] = 1;
    for(int i = 1;i < N; i++) fact[i] = mul(i, fact[i - 1]);
    invfact[N - 1] = inv(fact[N - 1]);
    for(int i = N - 2; i >= 1; i--) invfact[i] = mul(invfact[i + 1], i + 1);
    assert(invfact[1] == 1);
}
inline int C(int n, int k){
    if(n < k || k < 0) return 0;
    return mul(fact[n], mul(invfact[k], invfact[n - k]));
}
vector<ll> eulerian(int n){
    if(n == 0) return {1};
    vector<ll> ret(n + 1), v(n + 1);
    for(int i = 0; i <= n; i++) ret[i] = ((i % 2 == 0) ? C(n + 1, i) : sub(0, C(n + 1, i)));
    for(int i = 0; i <= n; i++) v[i] = powr(i, n);
    mul_big_mod(ret, v, mod);    
    ret.resize(n + 1);
    return ret;
}
vll polymul(vector<vll> & vec){
    if(vec.size() == 1) return vec[0];
    int half = ((int)vec.size()) >> 1;
    vector<vll> lft(vec.begin(), vec.begin() + half);
    vector<vll> rgt(vec.begin() + half, vec.end());
    vll vl = polymul(lft), vr = polymul(rgt);
    mul_big_mod(vl, vr, mod);
    return vl;
}
int C2(ll n, int k){
    int ret = 1;
    for(int i = 1; i <= k; i++)
        ret = mul(ret, mul((n - i + 1) % mod, inv(i)));
    return ret;
}
vector<vll> vec;
int main(){
    pre();
    precompute_powers();
    int n, x, k = 0, sm = 0;
    ll y;
    cin >> n >> y;
    assert(n <= 100000); assert(y <= 1000000000);
    vector<int> a;
    for(int i = 0; i < n; i++){
        cin >> x;
        vec.push_back(eulerian(x));
        sm += x;
        k += x + 1;
    }
    assert( sm <= 100000);
    random_shuffle(vec.begin(), vec.end());
    vector<ll> P = polymul(vec);
    // ans = sum C(k + y - i, k) * P(i)
    // print(P);
    int cv = C2(k + y, k), ans = 0;
    for(int i = 0; i < P.size() && i <= y; i++){
        ans = add(ans, mul(P[i], cv));
        if((k + y - i) % mod == 0){
            cv = C2(k + y - i - 1, k);
        }
        else cv = mul(cv, mul((y - i) % mod, inv((k + y - i) % mod)));
    }
    cout << ans;
    cerr << "time taken = " << clock() / (double) CLOCKS_PER_SEC << endl;
}
