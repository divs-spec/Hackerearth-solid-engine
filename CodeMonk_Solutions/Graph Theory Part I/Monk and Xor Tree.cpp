#include <bits/stdc++.h>
using namespace std;
const int SZ = int(1e5+5);

int N, K, A[SZ];
long long ans = 0;
map<int, long long> cnt;
vector<int> edge[SZ];

void dfs(int u, int x) {
    x ^= A[u];
    ans += cnt[(x^K)];
    cnt[x]++;
    for (auto v: edge[u]) dfs(v, x);
    cnt[x]--;
}

int main() {
//    freopen("input.txt", "r", stdin);
    ios_base::sync_with_stdio(false);
    cin.tie(0);

    cin >> N >> K;
    for (int i=1; i<=N; i++) cin >> A[i];
    for (int i=2; i<=N; i++) {
        int x;
        cin >> x;
        edge[x].push_back(i);
    }
    cnt[0] = 1;
    dfs(1, 0);
    cout << ans << endl;
    return 0;
}
