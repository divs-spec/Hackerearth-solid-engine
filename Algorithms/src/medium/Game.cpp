#include <cstdio>
#include <algorithm>
#include <cassert>

int const N = 3234567;

int allx[N], ax[N], ay[N], have[N];

void solve() {
  int n, m;
  assert(2 == scanf("%d%d", &n, &m));
  int cn = 0;
  allx[cn++] = 1;
  allx[cn++] = n;
  for (int i = 0; i < m; i++) {
    int x, y;
    assert(2 == scanf("%d%d", &x, &y));
    for (int dx = -3; dx <= 3; dx++) {
      int cx = x + dx;
      if (cx >= 1 && cx <= n) {
        allx[cn++] = cx;
      }
    }
    ax[i] = x;
    ay[i] = y;
  }
  std::sort(allx, allx + cn);
  cn = (int) (std::unique(allx, allx + cn) - allx);
  for (int i = 0; i < cn; i++) have[i] = 0;
  for (int i = 0; i < m; i++) {
    int w = (int) (std::lower_bound(allx, allx + cn, ax[i]) - allx);
    have[w] |= 1 << (ay[i] - 1);
  }
  int start = 7 ^ have[0];
  int ans = 0;
  for (int i = 0; i < cn; i++) {
    start = ((start << 1) | (start >> 1) | (start)) & 7;
    start &= 7 ^ have[i];
    if (start != 0) ans = allx[i];
  }
  printf("%d\n", ans);
}


int main() {
  int t;
  assert(scanf("%d", &t) == 1);
  while (t--) solve();
}
