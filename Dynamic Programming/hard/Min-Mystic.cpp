#include <bits/stdc++.h>
using namespace std;
#define int long long int
#define F first
#define S second
const int N=1000005;


vector<pair<int, int>> v[N];
int par[N], dp[N][3], sum[N][3];
int c = 2e18;

void dfs(int u, int p) {
    par[u] = p;
    for(auto i : v[u]) {
        if(i.F != p) {
            dfs(i.F, u);
        }
    }
}

int foo(int u, int f) {
    if(dp[u][f] != -1) {
        return dp[u][f];
    }
    int c = 0;
    if(f == 0) {
        c = 1;
    }
    int sum = 0;
    for(auto i : v[u]) {
        if(i.F != par[u]) {
            c = c + foo(i.F, (f - 1 + 3) % 3);
        }
    }
    return dp[u][f] = c;
}

int dfs_sum(int u, int f) {
    if(sum[u][f] != -1) {
        return sum[u][f];
    }
    int c = 0;
    for(auto i : v[u]) {
        if(i.F != par[u]) {
            int f1 = (f - 1 + 3) % 3;
            c = c + i.S * dp[i.F][f1] + dfs_sum(i.F, f1);
        }
    }
    return sum[u][f] = c;
}

void dfs_root(int u, int p) {
    int l = sum[u][1] + 2 * sum[u][2];
    c = min(c, l);
    for(auto i : v[u]) {
        if(i.F != p) {
            int node = i.F, e = i.S;

            dp[u][0] -= dp[node][2], dp[u][1] -= dp[node][0], dp[u][2] -= dp[node][1];
            sum[u][0] = sum[u][0] - e * dp[node][2] - sum[node][2];
            sum[u][1] = sum[u][1] - e * dp[node][0] - sum[node][0];
            sum[u][2] = sum[u][2] - e * dp[node][1] - sum[node][1];

            dp[node][0] += dp[u][2], dp[node][1] += dp[u][0], dp[node][2] += dp[u][1];
            sum[node][0] = sum[node][0] + e * dp[u][2] + sum[u][2];
            sum[node][1] = sum[node][1] + e * dp[u][0] + sum[u][0];
            sum[node][2] = sum[node][2] + e * dp[u][1] + sum[u][1];

            dfs_root(node, u);

            dp[node][0] -= dp[u][2], dp[node][1] -= dp[u][0], dp[node][2] -= dp[u][1];
            sum[node][0] = sum[node][0] - e * dp[u][2] - sum[u][2];
            sum[node][1] = sum[node][1] - e * dp[u][0] - sum[u][0];
            sum[node][2] = sum[node][2] - e * dp[u][1] - sum[u][1];

            dp[u][0] += dp[node][2], dp[u][1] += dp[node][0], dp[u][2] += dp[node][1];
            sum[u][0] = sum[u][0] + e * dp[node][2] + sum[node][2];
            sum[u][1] = sum[u][1] + e * dp[node][0] + sum[node][0];
            sum[u][2] = sum[u][2] + e * dp[node][1] + sum[node][1];

        }
    }
}


void solve() {

    int n;
    cin >> n;

    for(int i = 0; i <= n; i++) {
        v[i].clear();
        par[i] = 0;
        dp[i][0] = dp[i][1] = dp[i][2] = -1;
        sum[i][0] = sum[i][1] = sum[i][2] = -1;
    }
    c = 2e18;

    for(int i = 0; i < n - 1; i++) {
        int x, y, w;
        cin >> x >> y >> w;
        v[x].push_back(make_pair(y, w));
        v[y].push_back(make_pair(x, w));
    }

    dfs(1, 0);

    for(int i = 1; i <= n; i++) {
        foo(i, 0), foo(i, 1), foo(i, 2);
        dfs_sum(i, 0), dfs_sum(i, 1), dfs_sum(i, 2);
    }

    dfs_root(1, 0);

    cout << c << endl;

}


#undef int 
int main() {
#define int long long int
ios_base::sync_with_stdio(false); 
cin.tie(0); 
cout.tie(0);

    int T;
    cin >> T;

    for(int tc = 1; tc <= T; tc++){
        // cout << "Case #" << tc << ": ";
        solve();
    }

return 0;  

}
