#include <bits/stdc++.h>

using namespace std;

#define GODSPEED           \
  ios::sync_with_stdio(0); \
  cin.tie(0);              \
  cout.tie(0);             \
  cout << fixed;           \
  cout << setprecision(12);
#define f first
#define s second
#define pb push_back
#define mset(a, x) memset(a, x, sizeof(a))
#define deb1(a) cout << a << "\n";
#define all(a) a.begin(), a.end()

typedef long long ll;
const ll N = 5e5 + 5;

ll n, m, q, d[N], l[N], p[N], sz[N], ap[N], tin[N], tout[N], t = 0, w[N];
vector<pair<ll, ll>> adj[N], comp[N];

void make(ll n)
{
  for (int i = 1; i <= n; i++)
  {
    p[i] = i;
    sz[i] = 1;
  }
}

int find(ll x)
{
  return (p[x] == x) ? x : p[x] = find(p[x]);
}

void merge(ll x, ll y)
{
  ll a = find(x);
  ll b = find(y);
  if (a != b)
  {
    if (sz[a] >= sz[b])
      swap(a, b);
    p[a] = b;
    sz[b] += sz[a];
  }
}

void dfs(int x, int i, int ww)
{
  w[x] = ww;
  tin[x] = t++;
  int f = 1, ct = 0;
  for (auto [v, ind] : adj[x])
  {
    if (ind == i)
      continue;
    if (d[v] != -1)
    {
      l[x] = min(l[x], d[v]);
    }
    else
    {
      d[v] = l[v] = d[x] + 1;
      dfs(v, ind, ww);
      if (l[v] >= d[x])
      {
        f = 0;
        comp[x].pb({tin[v], tout[v]});
      }
      l[x] = min(l[x], l[v]);
    }
  }
  if (x == 1 && ct > 1)
    ap[x] = 1;
  else if (x != 1 && f == 0)
    ap[x] = 1;
  tout[x] = t++;
}

bool check(int a, int b, int c)
{
  if (ap[c] == 0)
    return 1;
  auto inda = upper_bound(all(comp[c]), make_pair(tin[a], t + 1)) - comp[c].begin() - 1;
  auto indb = upper_bound(all(comp[c]), make_pair(tin[b], t + 1)) - comp[c].begin() - 1;
  if (inda != -1 && (tin[a] < comp[c][inda].f || tout[a] > comp[c][inda].s))
    inda = -1;
  if (indb != -1 && (tin[b] < comp[c][indb].f || tout[b] > comp[c][indb].s))
    indb = -1;
  return inda == indb;
}

int main()
{
  GODSPEED;
  cin >> n >> m >> q;
  make(n + 1);
  for (int i = 1; i <= m; i++)
  {
    int u, v, w;
    cin >> u >> v >> w;
    if (w == 0)
    {
      adj[u].pb({v, i});
      adj[v].pb({u, i});
    }
    else
    {
      merge(u, v);
    }
  }
  for (int i = 1; i <= n; i++)
  {
    for (auto &[x, v] : adj[i])
      x = find(x);
    if (find(i) != i)
    {
      for (auto it : adj[i])
        adj[find(i)].pb(it);
    }
  }
  mset(d, -1);
  for (int i = 1; i <= n; i++)
  {
    if (find(i) == i)
    {
      if (d[i] == -1)
      {
        d[i] = l[i] = 0;
        dfs(i, 0, i);
      }
    }
  }
  for (int i = 1; i <= n; i++)
  {
    sort(all(comp[i]));
  }
  for (int i = 1; i <= q; i++)
  {
    int a, b, c, d;
    cin >> a >> b >> c >> d;
    if (find(c) == find(d) || w[find(a)] != w[find(b)])
    {
      deb1("No")
    }
    else if (find(d) != find(a) && find(d) != find(b) && check(find(a), find(b), find(d)))
    {
      deb1("Yes")
    }
    else if (find(c) != find(a) && find(c) != find(b) && check(find(a), find(b), find(c)))
    {
      deb1("Yes")
    }
    else
    {
      deb1("No")
    }
  }
}
