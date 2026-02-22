#include <bits/stdc++.h>
using namespace std;

double dp[101][11][251];
bool vis[101][11][251];
int TARGET;

double solve(int a, int l, int s) {
    if (s >= TARGET) return 1.0;
    if (a == 100 || l == 0) return 0.0;

    if (vis[a][l][s]) return dp[a][l][s];
    vis[a][l][s] = true;

    double res = 0.0;

    // Rolls 1 to 4
    for (int i = 1; i <= 4; i++) {
        res += (1.0 / 6.0) * solve(a + 1, l, min(s + i, TARGET));
    }

    // Green (extra roll)
    res += (1.0 / 6.0) * solve(a, l, min(s + 5, TARGET));

    // Red (lose life)
    res += (1.0 / 6.0) * solve(a + 1, l - 1, s);

    return dp[a][l][s] = res;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;

    cout << fixed << setprecision(2);

    while (T--) {
        int a, l, c, t;
        cin >> a >> l >> c >> t;

        memset(vis, false, sizeof(vis));
        TARGET = t;

        double ans = solve(a, 10 - l, c) * 100.0;

        // truncate (not round)
        ans = floor(ans * 100) / 100;

        cout << ans << "\n";
    }
}
