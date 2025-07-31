#include <bits/stdc++.h>
using namespace std;

#define ms(s, n) memset(s, n, sizeof(s))
#define FOR(i, a, b) for (int i = (a); i < (b); i++)
#define FORd(i, a, b) for (int i = (a) - 1; i >= (b); i--)
#define FORall(it, a) for (__typeof((a).begin()) it = (a).begin(); it != (a).end(); it++)
#define FORalld(it, a) for (__typeof((a).rbegin()) it = (a).rbegin(); it != (a).rend(); it++)
#define sz(a) int((a).size())
#define present(t, x) (t.find(x) != t.end())
#define all(a) (a).begin(), (a).end()
#define uni(a) (a).erase(unique(all(a)), (a).end())
#define pb push_back
#define pf push_front
#define mp make_pair
#define fi first
#define se second
#define prec(n) fixed << setprecision(n)
#define bit(n, i) (((n) >> (i)) & 1)
#define bitcount(n) __builtin_popcountll(n)
typedef long long ll;
typedef unsigned long long ull;
typedef long double ld;
typedef pair<int, int> pi;
typedef vector<int> vi;
typedef vector<pi> vii;
const int MOD = (int)1e9 + 7;
const int INF = (int)1e9;
const ll LINF = (ll)1e18;
const ld PI = acos((ld)-1);
const ld EPS = 1e-9;
inline ll gcd(ll a, ll b)
{
  ll r;
  while (b)
  {
    r = a % b;
    a = b;
    b = r;
  }
  return a;
}
inline ll lcm(ll a, ll b) { return a / gcd(a, b) * b; }
inline ll fpow(ll n, ll k, int p = MOD)
{
  ll r = 1;
  for (; k; k >>= 1)
  {
    if (k & 1)
      r = r * n % p;
    n = n * n % p;
  }
  return r;
}
template <class T>
inline int chkmin(T &a, const T &val) { return val < a ? a = val, 1 : 0; }
template <class T>
inline int chkmax(T &a, const T &val) { return a < val ? a = val, 1 : 0; }
template <class T>
inline T isqrt(T k)
{
  T r = sqrt(k) + 1;
  while (r * r > k)
    r--;
  return r;
}
template <class T>
inline T icbrt(T k)
{
  T r = cbrt(k) + 1;
  while (r * r * r > k)
    r--;
  return r;
}
inline void addmod(int &a, int val, int p = MOD)
{
  if ((a = (a + val)) >= p)
    a -= p;
}
inline void submod(int &a, int val, int p = MOD)
{
  if ((a = (a - val)) < 0)
    a += p;
}
inline int mult(int a, int b, int p = MOD) { return (ll)a * b % p; }
inline int inv(int a, int p = MOD) { return fpow(a, p - 2, p); }
inline int sign(ld x) { return x < -EPS ? -1 : x > +EPS; }
inline int sign(ld x, ld y) { return sign(x - y); }

const int maxn = 2e5 + 5;
const int logn = 20;
const int magic = 5;
int n, q;
vi adj[maxn];
int lev[maxn];
int p[logn][maxn];
int tin[maxn];
int tou[maxn];
int idx[maxn];
int tms;
vii f[maxn];
int mxinc[logn][maxn];
int mxdsc[logn][maxn];

vector<int> event;
int st[maxn];

int ff[logn][maxn << 1];
void build()
{
  for (int i = 0; i < event.size(); i++)
  {
    ff[0][i] = event[i];
  }
  for (int i = 1; i < logn; i++)
  {
    for (int j = 0; j + (1 << i - 1) < event.size(); j++)
    {
      ff[i][j] = min(ff[i - 1][j], ff[i - 1][j + (1 << i - 1)]);
    }
  }
}
int query(int u, int v)
{
  int l = u == v ? 0 : __lg(v - u);
  return min(ff[l][u], ff[l][v - (1 << l) + 1]);
}

void dfs(int u, int dad)
{
  idx[tin[u] = tms++] = u;
  st[u] = event.size();
  event.push_back(tin[u]);
  for (int v : adj[u])
  {
    if (v != dad)
    {
      lev[v] = lev[u] + 1;
      p[0][v] = u;
      dfs(v, u);
      f[u].pb(f[v][0]);
      event.push_back(tin[u]);
    }
  }
  f[u].pb(mp(lev[u], u));
  sort(all(f[u]), greater<pi>());
  while (sz(f[u]) > magic)
    f[u].pop_back();
  tou[u] = tms - 1;
}

void upd(int u, int dad)
{
  FOR(i, 1, logn)
  {
    int v = p[i - 1][u];
    p[i][u] = p[i - 1][v];
    mxinc[i][u] = max(mxinc[i - 1][u], mxinc[i - 1][v]);
    mxdsc[i][u] = max(mxdsc[i - 1][u], mxdsc[i - 1][v]);
  }
  multiset<pi> heap;
  heap.insert(mp(lev[u], u));
  for (int v : adj[u])
  {
    if (v != dad)
    {
      heap.insert(f[v][0]);
    }
  }
  for (int v : adj[u])
  {
    if (v != dad)
    {
      heap.erase(heap.find(f[v][0]));
      mxinc[0][v] = heap.rbegin()->fi - lev[u] - lev[u];
      mxdsc[0][v] = heap.rbegin()->fi;
      upd(v, u);
      heap.insert(f[v][0]);
    }
  }
}

int isparent(int u, int v)
{
  return tin[u] <= tin[v] && tou[u] >= tou[v];
}

int lca(int u, int v)
{
  if (st[u] > st[v])
    swap(u, v);
  return idx[query(st[u], st[v])];
}

inline int d(int u, int v)
{
  int a = lca(u, v);
  return lev[u] + lev[v] - lev[a] - lev[a];
}

int queryinc(int u, int v)
{
  int res = -INF;
  FORd(i, logn, 0)
  {
    if (lev[p[i][u]] >= lev[v])
    {
      chkmax(res, mxinc[i][u]);
      u = p[i][u];
    }
  }
  return res;
}

int querydsc(int u, int v)
{
  int res = -INF;
  FORd(i, logn, 0)
  {
    if (lev[p[i][u]] > lev[v])
    {
      chkmax(res, mxdsc[i][u]);
      u = p[i][u];
    }
  }
  return res;
}

void solve()
{
  cin >> n >> q;
  FOR(i, 0, n - 1)
  {
    int u, v;
    cin >> u >> v;
    u--, v--;
    adj[u].pb(v), adj[v].pb(u);
  }
  dfs(0, -1), upd(0, -1), build();
  while (q--)
  {
    int k;
    cin >> k;
    vi a(k);
    FOR(i, 0, k)
        cin >> a[i],
        a[i]--;
    sort(all(a)), uni(a), k = sz(a);
    vi ver;
    pi best = mp(INF, INF);
    FOR(i, 0, k)
    FOR(j, 0, k)
    {
      int u = lca(a[i], a[j]);
      ver.pb(u);
      chkmin(best, mp(lev[u], u));
    }
    sort(all(ver)), uni(ver);
    static int dst[maxn];
    for (int u : ver)
    {
      dst[u] = INF;
      for (int v : a)
      {
        chkmin(dst[u], d(u, v));
      }
    }
    int u = best.se;
    int ans = dst[u] + lev[u] + queryinc(u, 0);
    for (int u : ver)
    {
      for (pi p : f[u])
      {
        int v = p.se;
        int mx = INF;
        for (int w : a)
        {
          chkmin(mx, d(v, w));
        }
        chkmax(ans, mx);
      }
    }
    for (int v : ver)
    {
      int mx = INF;
      for (int w : a)
      {
        chkmin(mx, d(v, w));
      }
      chkmax(ans, mx);
    }
    for (int u : ver)
      for (int v : ver)
      {
        if (u != v && isparent(v, u))
        {
          int ok = 1;
          for (int w : ver)
            if (w != u && w != v)
            {
              if (isparent(v, w) && isparent(w, u))
              {
                ok = 0;
                break;
              }
            }
          if (!ok)
            continue;
          int c = u;
          FORd(i, logn, 0)
          {
            int nc = p[i][c];
            if (lev[nc] > lev[v] && dst[u] + lev[u] - lev[nc] <= dst[v] + lev[nc] - lev[v])
            {
              c = nc;
            }
          }
          chkmax(ans, dst[u] + lev[u] + queryinc(u, c));
          chkmax(ans, dst[v] + querydsc(c, v) - lev[v]);
        }
      }
    cout << ans << "\n";
  }
}

int main()
{
#ifdef _LOCAL_
  freopen("in.txt", "r", stdin); // freopen("out.txt", "w", stdout);
#else
  ios_base::sync_with_stdio(0);
  cin.tie(0);
#endif
  solve();
  cerr << "\nTime elapsed: " << 1.0 * clock() / CLOCKS_PER_SEC << "s\n";
  return 0;
}
