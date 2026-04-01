#include <bits/stdc++.h>
using namespace std;

void solve() {
    int n, m;
    if (!(cin >> n >> m)) return;

    vector<vector<int>> v(n, vector<int>(m, 0));

    int q;
    if (!(cin >> q)) return;

    for (int k = 0; k < q; k++) {
        int i, j;
        if (!(cin >> i >> j)) break;

        if (i >= 0 && i < n && j >= 0 && j < m) {
            v[i][j] = 1;
        }
    }

    int c1 = 0, c2 = 0;

    for (int i = 0; i < n; i++) {
        int length = 0;

        for (int j = 0; j < m; j++) {
            if (v[i][j] == 0) {
                length++;
            } else {
                c1 += (length + 1) / 2;
                c2 += (length + 2) / 3;
                length = 0;
            }
        }

        // remaining segment
        c1 += (length + 1) / 2;
        c2 += (length + 2) / 3;
    }

    cout << c1 << " " << c2 << "\n";
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    solve();
    return 0;
}
