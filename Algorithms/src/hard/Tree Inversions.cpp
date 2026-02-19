#include <bits/stdc++.h>
using namespace std;
#define int long long int
const int N=100005;
const int LG = 22;

int lvl[N], P[N][LG], a[N], in[N], out[N], cur = 1;
int ID[N << 1], BL[N << 1], freq[N], VIS[N], sum = 0;
vector<int>g[N];

void clear(int n) {
    cur = 1;
    sum = 0;
    for(int i = 0; i <= n; i++) {
        lvl[i] = 0, a[i] = 0, in[i] = 0, out[i] = 0, freq[i] = 0, VIS[i] = 0;
        g[i].clear();
        for(int j = 0; j < LG; j++) {
            P[i][j] = -1;
        }
    }
    for(int i = 0; i <= 2 * n; i++) {
        ID[i] = 0, BL[i] = 0;
    }
}

void dfs(int u, int par){
    ID[cur] = u;
    in[u] = cur++;
    lvl[u] = 1+lvl[par];
    P[u][0] = par;
    for(int i = 1; i < LG; i++) {
        if(P[u][i - 1] != -1) {
            P[u][i] = P[P[u][i - 1]][i - 1];
        }
    }
    for(int v:g[u]){
        if (v == par) continue;
        dfs(v, u);
    }
    ID[cur] = u;
    out[u] = cur++;
}
 
int lca(int u, int v){
    int i, lg;
    if (lvl[u] < lvl[v]) swap(u, v);
 
    for(lg = 0; (1<<lg) <= lvl[u]; lg++);
    lg--;
 
    for(i=lg; i>=0; i--){
        if (lvl[u] - (1<<i) >= lvl[v])
            u = P[u][i];
    }
 
    if (u == v) 
        return u;
 
    for(i=lg; i>=0; i--){
        if (P[u][i] != -1 and P[u][i] != P[v][i])
            u = P[u][i], v = P[v][i];
    }
 
    return P[u][0];
}

int dis(int u, int v){
    if (lvl[u] < lvl[v]) swap(u, v);
    int w = lca(u, v);
    return lvl[u] + lvl[v] - 2*lvl[w] + 1;
}

struct query{
    int id, l, r, lc;
    bool operator < (const query& rhs){
        return (BL[l] == BL[rhs.l]) ? (r < rhs.r) : (BL[l] < BL[rhs.l]);
    }
}Q[N];

void check(int x){
    sum -= (freq[a[x]] * (freq[a[x]] - 1)) / 2;
    if(VIS[x]) {
        freq[a[x]] -= 1;
    } else {
        freq[a[x]] += 1;
    }
    sum += (freq[a[x]] * (freq[a[x]] - 1)) / 2;
    VIS[x] ^= 1;
}


void solve() {

    int n, q;
    cin >> n >> q;

    clear(n);

    for(int i = 1; i <= n; i++) {
        cin >> a[i];
    }

    for(int i = 0; i < n - 1; i++) {
        int x, y;
        cin >> x >> y;
        g[x].push_back(y);
        g[y].push_back(x);
    }

    dfs(1, 0);

    int sz = sqrt(cur);
    for (int i = 1; i <= cur; i++) {
        BL[i] = (i - 1) / sz + 1;
    }

    for(int i = 0; i < q; i++) {
        int x, y;
        cin >> x >> y;
        Q[i].lc = lca(x, y);
        if (in[x] > in[y]) {
            swap(x, y);
        }
        if(Q[i].lc == x) Q[i].l = in[x], Q[i].r = in[y];
        else Q[i].l = out[x], Q[i].r = in[y];
        Q[i].id = i;
    }
    sort(Q, Q + q);

    vector<int> arr(q, 0);

    int curL = Q[0].l, curR = Q[0].l - 1;
    for (int i = 0; i < q; i++){
 
        while (curL < Q[i].l) check(ID[curL++]);
        while (curL > Q[i].l) check(ID[--curL]);
        while (curR < Q[i].r) check(ID[++curR]);
        while (curR > Q[i].r) check(ID[curR--]);
 
        int x = ID[curL], y = ID[curR];

        if (Q[i].lc != x and Q[i].lc != y) check(Q[i].lc);
        
        int len = dis(x, y);
        arr[Q[i].id] = (len * (len - 1)) / 2 - sum;
 
        if (Q[i].lc != x and Q[i].lc != y) check(Q[i].lc);
    }

    for(int i = 0; i < q; i++) {
        cout << arr[i] << endl;
    }

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
