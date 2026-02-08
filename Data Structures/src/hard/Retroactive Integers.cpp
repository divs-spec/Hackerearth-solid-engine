#include <bits/stdc++.h>

using namespace std;

typedef long double ld;
typedef long long ll;

const int M = 200100;
const int N = 6;
const int mod = 1e9 + 7;

int add(int x, int y) {
  x += y;
  if (x >= mod)
    x -= mod;
  return x;
}

int udd(int &x, int y) {
  return x = add(x, y);
}

int mul(ll x, ll y) {
  return x * y % mod;
}

struct Matrix {
  int a[N][N];

  Matrix() {
    for (int i = 0; i < N; ++i)
      fill(a[i], a[i] + N, 0);
  }

  int* operator[](int x) {
    return a[x];
  }

  Matrix operator*(Matrix to) {
    Matrix ans;
    for (int i = 0; i < N; ++i)
      for (int k = 0; k < N; ++k)
        for (int j = 0; j < N; ++j)
          udd(ans[i][j], mul(a[i][k], to[k][j]));
    return ans;
  }
} I;


int q, x[M], xs[M];
char type[M];
int from[M], to[M], by[M];
Matrix t[4 * M];

void ini() {
  for (int i = 0; i < N; ++i)
    I[i][i] = 1;
  fill(t, t + (4 * M), I);
}

int readVariable() {
  char c;
  cin >> c;
  return c - 'a' + 1;
}

void read() {
  cin >> q;
  for (int i = 0; i < q; ++i) {
    cin >> x[i];
    xs[i] = x[i];

    cin >> type[i];
    if (type[i] == '?') {
      from[i] = readVariable();
    } else if (type[i] == '=') {
      to[i] = readVariable();
      from[i] = readVariable();
      by[i] = 1;
    } else if (type[i] == '!') {
      to[i] = readVariable();
      from[i] = 0;
      cin >> by[i];
    } else if (type[i] == '+') {
      to[i] = readVariable();
      from[i] = 0;
      cin >> by[i];
    } else if (type[i] == '*') {
      to[i] = readVariable();
      from[i] = to[i];
      cin >> by[i];
    } else {
      cerr << "i = " << i << endl;
      cerr << "type = " << type[i] << endl;
      assert(false);
    }
  }
}

void comp() {
  sort(xs, xs + q);
  for (int i = 0; i < q; ++i)
    x[i] = lower_bound(xs, xs + q, x[i]) - xs;
}


Matrix get(int v, int l, int r, int at) {
  assert(l <= at && at < r);
  if (l + 1 == r) {
    return t[v];
  }
  int m = (l + r) / 2;
  if (at < m)
    return get(2 * v, l, m, at);
  else {
    return t[2 * v] * get(2 * v + 1, m, r, at);
  }
}

void upd(int v, int l, int r, int at, Matrix &A) {
  assert(l <= at && at < r);
  if (l + 1 == r) {
    t[v] = A;
    return;
  }
  int m = (l + r) / 2;
  if (at < m)
    upd(2 * v, l, m, at, A);
  else
    upd(2 * v + 1, m, r, at, A);
  t[v] = t[2 * v] * t[2 * v + 1];
}

void kill() {
  for (int i = 0; i < q; ++i) {
    int at = x[i];
    if (type[i] == '?') {
      Matrix A = get(1, 0, q, at);
      cout << A[0][from[i]] << "\n";
    } else {
      Matrix A = I;
      if (type[i] != '+' && type[i] != '*') {
        A[to[i]][to[i]] = 0;
      }
      A[from[i]][to[i]] = by[i];
      upd(1, 0, q, at, A);
    }
  }
}

int main() {
#ifdef LOCAL
  assert(freopen("a.in", "r", stdin));
#endif

  ios_base::sync_with_stdio(false);

  ini();
  read();
  comp();
  kill();
}
