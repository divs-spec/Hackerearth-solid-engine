#include <cstdio>
#include <algorithm>
#include <cassert>

int const INF = 1 << 30;

int a[1234567];

int dp[4][4][222222];

int main() {
  int n;
  assert(1 == scanf("%d", &n));
  for (int i = 0; i < n; i++) {
    assert(1 == scanf("%d", a + i));
  }
  for (int i = 0; i < n; i++) {
    int x;
    assert(1 == scanf("%d", &x));
    a[i] ^= x;
  }
  if (n == 3) {
    if (a[0] != a[1] || a[0] != a[2]) {
      puts("Impossible");
    } else {
      printf("%d\n", a[0]);
    }
    return 0;
  }
  for (int i = 0; i < 4; i++)
    for (int k = 0; k < 4; k++) 
      for (int j = 0; j < n; j++) dp[k][i][j] = INF;
  dp[a[0] * 2 + a[1]][a[0] * 2 + a[1]][0] = 0;
  for (int i = 0; i + 2 < n; i++) {
    for (int smask = 0; smask < 4; smask++) {
      for (int mask = 0; mask < 4; mask++) {        
        int val = dp[smask][mask][i];
        if (val == INF) continue;
        for (int flip = 0; flip < 2; flip++) {
          int nsmask = smask;
          if (flip == 1) {
            if (i == 0) {
              nsmask ^= 3;
            } else if (i == 1) {
              nsmask ^= 1;
            }
          }
          int nmask = ((mask * 2 + a[i + 2]) ^ (flip * 7));
          if (i >= 2 && nmask >= 4) {
            continue;
          }
          nmask &= 3;
          int &cur = dp[nsmask][nmask][i + 1];
          cur = std::min(cur, val + flip);
        }
      }
    }
  }
  int ans = INF;
  for (int mask1 = 0; mask1 < 4; mask1++) {
    for (int mask2 = 0; mask2 < 4; mask2++) {
      int val = dp[mask1][mask2][n - 2];
      if (val == INF) continue;
      int mask = (mask2 << 2) | mask1;
      for (int flip1 = 0; flip1 < 2; flip1++) {
        for (int flip2 = 0; flip2 < 2; flip2++) {
          if (((14 * flip1) ^ (7 * flip2) ^ mask) != 0) continue;
          ans = std::min(ans, val + flip1 + flip2);
        }
      }
    }
  }
  if (ans == INF) {
    puts("Impossible");
  } else {
    printf("%d\n", ans);
  }
}
