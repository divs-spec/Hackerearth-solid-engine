#include <bits/stdc++.h>
using namespace std;
#define int long long int
const int N=100005;
#define M 1000000007

vector<int> v[N];
int a[N], c = 0;
int n, x;

int fact[N], ifact[N];
 
int power(int x,int y,int m){
    if (y == 0)
        return 1;
    int p = power(x, y/2, m) % m;
    p = (p * p) % m;
    return (y%2 == 0)? p : (x * p) % m;
}
 
void process(){
    fact[0] = 1;
    for(int i = 1; i < N; i++)
        fact[i] = (fact[i - 1] * i) % M;
    for(int i = 0; i < N; i++)
        ifact[i] = power(fact[i], M - 2, M);
}
 
int nCr(int n, int r){
    if(r > n) return 0;
    if(r == n) return 1;
    int ans = (ifact[n - r] * ifact[r]) % M;
    ans = (ans * fact[n]) % M;
    return ans;
}

int dfs(int u, int p) {
    int sum = a[u];
    for(auto i : v[u]) {
        if(i != p) {
            sum += dfs(i, u);
        }
    }
    if(sum % x == 0) c = c + 1;
    return sum;
}

void solve() {

    cin >> n >> x;

    for(int i = 0; i <= n; i++) {
        v[i].clear();
        a[i] = 0;
    }
    c = 0;

    for(int i = 1; i <= n; i++) {
        cin >> a[i];
    }

    for(int i = 0; i < n - 1; i++) {
        int uu, vv;
        cin >> uu >> vv;
        v[uu].push_back(vv);
        v[vv].push_back(uu);
    }

    int sum = dfs(1, 0);

    if(sum % x != 0) {
        c = 0;
    }

    for(int i = 1; i <= n; i++) {
        cout << nCr(c - 1, i - 1) << ' ';
    }
    cout << endl;

}


#undef int 
int main() {
#define int long long int
ios_base::sync_with_stdio(false); 
cin.tie(0); 
cout.tie(0);

    int T;
    cin >> T;

    process();

    for(int tc = 1; tc <= T; tc++){
        // cout << "Case #" << tc << ": ";
        solve();
    }

return 0;  

}
