#include <bits/stdc++.h>
using namespace std;
const int SZ = int(1e3+5);

int N, M, k, val[SZ];
vector<pair<int, int> > edge[SZ];

bool cmp(pair<int, int> a, pair<int, int> b) {
    if (a.first == b.first) return a.second > b.second;
    return a.first > b.first;
}

int main() {
//    freopen("input.txt", "r", stdin);
    ios_base::sync_with_stdio(false);
    cin.tie(0);

    cin >> N >> M >> k;
    for (int i=1; i<=N; i++) {
        cin >> val[i];
    }
    for (int i=0; i<M; i++) {
        int X, Y;
        cin >> X >> Y;
        edge[Y].push_back({val[X], X});
        edge[X].push_back({val[Y], Y});
    }
    for (int i=1; i<=N; i++) {
        sort(edge[i].begin(), edge[i].end(), cmp);
        int length = edge[i].size();
        if (k > length) cout << -1 << endl;
        else cout << edge[i][k-1].second << endl;
    }
    return 0;
}
