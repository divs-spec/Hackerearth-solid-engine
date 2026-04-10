#include <bits/stdc++.h>
using namespace std;
 
const int MAXN = 100005;
 
vector<int> tree[MAXN];
int in[MAXN], out[MAXN], depth[MAXN];
int timer = 0;
int M[MAXN];
 
vector<pair<int,int>> euler; // (depth, value)
 
void dfs(int u, int d) {
    depth[u] = d;
    in[u] = timer++;
 
    euler.push_back({d, M[u]});
 
    for (int v : tree[u]) {
        dfs(v, d + 1);
    }
 
    out[u] = timer - 1;
}
 
vector<vector<pair<int,int>>> seg;
 
void build(int idx, int l, int r) {
    if (l == r) {
        seg[idx] = {euler[l]};
        return;
    }
 
    int mid = (l + r) / 2;
    build(2*idx, l, mid);
    build(2*idx+1, mid+1, r);
 
    merge(seg[2*idx].begin(), seg[2*idx].end(),
          seg[2*idx+1].begin(), seg[2*idx+1].end(),
          back_inserter(seg[idx]));
}
 
int query(int idx, int l, int r, int ql, int qr, int dl, int dr, int vl, int vr) {
    if (r < ql || l > qr) return 0;
 
    if (ql <= l && r <= qr) {
        // filter depth and value using binary search
        int cnt = 0;
        for (auto &p : seg[idx]) {
            if (p.first >= dl && p.first <= dr &&
                p.second >= vl && p.second <= vr)
                cnt++;
        }
        return cnt;
    }
 
    int mid = (l + r) / 2;
    return query(2*idx, l, mid, ql, qr, dl, dr, vl, vr) +
           query(2*idx+1, mid+1, r, ql, qr, dl, dr, vl, vr);
}
 
int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);
 
    int N, A, B;
    cin >> N >> A >> B;
 
    for (int i = 1; i <= N; i++) cin >> M[i];
 
    for (int i = 2; i <= N; i++) {
        int p;
        cin >> p;
        tree[p].push_back(i);
    }
 
    dfs(1, 0);
 
    seg.resize(4 * N);
    build(1, 0, N-1);
 
    int Q;
    cin >> Q;
 
    while (Q--) {
        int X, U, V;
        cin >> X >> U >> V;
 
        int ans = query(
            1, 0, N-1,
            in[X], out[X],
            depth[X] + A,
            depth[X] + B,
            U, V
        );
 
        cout << ans << "\n";
    }
}
