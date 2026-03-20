#include <bits/stdc++.h>
using namespace std;

vector<int> a[1000001], c[1000001];

int v[1000001] = {0};
int b[1000001] = {0};
int d[1000001] = {0};

void dfs(int s, int k) {
    v[s] = 1;

    // Check k-th ancestor with same color
    if (c[b[s]].size() >= k) {
        int size = c[b[s]].size();
        d[s] = c[b[s]][size - k];
    } else {
        d[s] = -1;
    }

    // Add current node to its color stack
    c[b[s]].push_back(s);

    // Traverse neighbors
    for (int i = 0; i < a[s].size(); i++) {
        int next = a[s][i];
        if (v[next] == 0) {
            dfs(next, k);
        }
    }

    // Backtrack
    if (!c[b[s]].empty()) {
        c[b[s]].pop_back();
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    int n, k;
    cin >> n >> k;

    // Input colors
    for (int i = 1; i <= n; i++) {
        cin >> b[i];
    }

    // Input edges
    for (int i = 1; i < n; i++) {
        int x, y;
        cin >> x >> y;
        a[x].push_back(y);
        a[y].push_back(x);
    }

    // Run DFS from node 1
    dfs(1, k);

    // Output results
    for (int i = 1; i <= n; i++) {
        cout << d[i] << " ";
    }

    return 0;
}
