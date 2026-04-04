#include <bits/stdc++.h>
#include <ext/pb_ds/assoc_container.hpp> 
#include <ext/pb_ds/tree_policy.hpp> 
using namespace std;
using namespace __gnu_pbds;
#define int long long int
#define ordered_set tree<int, nuint_type,less<int>, rb_tree_tag,tree_order_statistics_node_update> 
mt19937 rng(std::chrono::duration_cast<std::chrono::nanoseconds>(chrono::high_resolution_clock::now().time_since_epoch()).count());
#define mp make_pair
#define pb push_back
#define F first
#define S second
const int N=100005;
#define M 1000000007
#define BINF 1e16
#define init(arr,val) memset(arr,val,sizeof(arr))
#define MAXN 10000005
#define deb(xx) cout << #xx << " " << xx << "\n";
const int LG = 22;

vector<int> v[N];
int prime[N], par[N], sub[N], dp[N][2];


void dfs(int u, int p) {
    par[u] = p;
    sub[u] = 1;
    for(auto i : v[u]) {
        if(i != p) {
            dfs(i, u);
            sub[u] = sub[u] + sub[i];
        }
    }
}

int foo(int u, int f) {
    if(prime[u] > f or f < 0) {
        return dp[u][f] = 0;
    }
    if(dp[u][f] != -1) {
        return dp[u][f];
    }
    int c = 1;
    for(auto i : v[u]) {
        if(i != par[u]) {
            c = c + foo(i, f - prime[u]);
        }
    }
    return dp[u][f] = c;
}


void solve() {

    for(int i = 2; i < N; i++) {
        prime[i] = 1;
    }
    for(int i = 2; i < N; i++) {
        for(int j = 2 * i; j < N; j += i) {
            prime[j] = 0;
        }
    }

    int n;
    cin >> n;

    for(int i = 0; i <= n; i++) {
        dp[i][0] = dp[i][1] = -1;
    }

    for(int i = 0; i < n - 1; i++) {
        int x, y;
        cin >> x >> y;
        v[x].push_back(y);
        v[y].push_back(x);
    }

    dfs(1, 0);

    for(int i = 1; i <= n; i++) {
        foo(i, 0);
        foo(i, 1);
    }

    for(int i = 1; i <= n; i++) {
        dp[i][1] = dp[i][1] - dp[i][0];
    }

    int c = 0;
    for(int i = 1; i <= n; i++) {
        c = c + dp[i][1] - prime[i];
        int zero = 0, one = 0, zero_sq = 0, one_sq = 0;
        for(auto j : v[i]) {
            if(j == par[i]) continue;
            zero = zero + dp[j][0];
            zero_sq = zero_sq + dp[j][0] * dp[j][0];
            one = one + dp[j][1];
            one_sq = one_sq + dp[j][1] * dp[j][1];
        }
        if(prime[i] == 0) {
            for(auto j : v[i]) {
                if(j == par[i]) continue;
                c = c + dp[j][0] * (one - dp[j][1]);
            }
        } else {
            c = c + (zero * zero - zero_sq) / 2;
        }
    }

    cout << c << endl;

}


#undef int 
int main() {
#define int long long int
ios_base::sync_with_stdio(false); 
cin.tie(0); 
cout.tie(0);
#ifndef ONLINE_JUDGE
    freopen("input.txt", "r", stdin);
    freopen("optput.txt", "w", stdout);
#endif

    // int T;
    // cin >> T;

    // for(int tc = 1; tc <= T; tc++){
        // cout << "Case #" << tc << ": ";
        solve();
    // }

return 0;  
 
}
