#include <cstdio>
#include <vector>
#include <cassert>
using namespace std;

int const T = 1 << 17;
int const ALPHA = 26;
int const N = 123456;

int nextchar() {
  int c = getchar();
  while (c <= 32) c = getchar();
  return c;  
}

struct node {
  int longest[ALPHA];
  int first, firstCount;
  int last, lastCount;
  int len;

  node() : first(0), firstCount(0), last(0), lastCount(0), len(0) {
    fill(longest, longest + ALPHA, 0);
  }
};

node createLetter(int c) {
  node ret;
  ret.longest[c] = 1;
  ret.first = ret.last = c;
  ret.len = ret.firstCount = ret.lastCount = 1;
  return ret;
}

node operator+(node const &a, node const &b) {
  if (a.len == 0) return b;
  if (b.len == 0) return a;
  node ret;
  for (int i = 0; i < ALPHA; i++) {
    ret.longest[i] = std::max(a.longest[i], b.longest[i]);
  }
  ret.len = a.len + b.len;
  ret.first = a.first;
  ret.firstCount = a.firstCount + (a.firstCount == a.len && b.first == a.first ? b.firstCount : 0);
  ret.last = b.last;
  ret.lastCount = b.lastCount + (b.lastCount == b.len && b.last == a.last ? a.lastCount : 0);
  if (a.last == b.first) {
    ret.longest[a.last] = std::max(ret.longest[a.last], a.lastCount + b.firstCount);
  }
  return ret;
}

node tr[2 * T + 5];
int add[2 * T + 5];
int qa[N], qb[N], qc[N], type[N];
vector<int> acc[N];

node processAdd(node const &v, int add) {
  node ret;
  for (int i = 0; i < ALPHA; i++) {
    ret.longest[(i + add) % ALPHA] = v.longest[i];
  }
  ret.first = (v.first + add) % ALPHA;
  ret.last = (v.last + add) % ALPHA;
  ret.len = v.len;
  ret.firstCount = v.firstCount;
  ret.lastCount = v.lastCount;
  return ret;
}

void relax(int v) {
  if (add[v] == 0) return;
  add[2 * v] += add[v];
  add[2 * v + 1] += add[v];
  tr[v] = processAdd(tr[v], add[v]);
  add[v] = 0;
}

node collect(int v, int l, int r, int left, int right) {
  if (r <= left || right <= l) return node();
  if (left <= l && r <= right) {
    return processAdd(tr[v], add[v]);
  }
  relax(v);
  int mid = (l + r) >> 1;
  return collect(v * 2, l, mid, left, right) + collect(v * 2 + 1, mid, r, left, right);
}

void makeAdd(int v, int l, int r, int left, int right, int delta) {
  if (r <= left || right <= l) return;
  if (left <= l && r <= right) {
    add[v] += delta;
    return;
  }
  relax(v);
  int mid = (l + r) >> 1;
  makeAdd(v * 2, l, mid, left, right, delta);
  makeAdd(v * 2 + 1, mid, r, left, right, delta);
  tr[v] = processAdd(tr[v * 2], add[v * 2]) + processAdd(tr[v * 2 + 1], add[v * 2 + 1]);
}

void setIt(int v, int l, int r, int x, int y) {
  if (l + 1 == r) {
    tr[v] = createLetter(y);
    add[v] = 0;
    return;
  }
  relax(v);
  int mid = (l + r) >> 1;
  if (x < mid) setIt(v * 2, l, mid, x, y); else setIt(v * 2 + 1, mid, r, x, y);
  tr[v] = processAdd(tr[v * 2], add[v * 2]) + processAdd(tr[v * 2 + 1], add[v * 2 + 1]);
}

int main() {
  int n, q;
  assert(scanf("%d%d", &n, &q) == 2);
  for (int i = 0; i < n; i++) {
    setIt(1, 0, T, i, nextchar() - 'a');
  }
  for (int i = 0; i < q; i++) {
    int c = getchar();
    while (c <= 32) c = getchar();
    type[i] = c;
    if (c == '?') {
      assert(scanf("%d%d", qa + i, qb + i) == 2);
      --qa[i];
      qc[i] = nextchar() - 'a';
    } else if (c == '+') {
      assert(scanf("%d%d%d", qa + i, qb + i, qc + i) == 3);
      --qa[i];
      qc[i] %= ALPHA;
    } else if (c == '*') {
      assert(scanf("%d", qa + i) == 1);
      --qa[i];
      qc[i] = nextchar() - 'a';
    } else {
      assert(scanf("%d%d", qa + i, qc + i) == 2);
      --qa[i];
      acc[qc[i] - 1].push_back(i);
    }
  }
  for (int i = 0; i < q; i++) {
    for (int x : acc[i]) {
      qc[x] = collect(1, 0, T, qa[x], qa[x] + 1).first;
    }
    if (type[i] == '?') {
      printf("%d\n", collect(1, 0, T, qa[i], qb[i]).longest[qc[i]]);
    } else if (type[i] == '+') {
      makeAdd(1, 0, T, qa[i], qb[i], qc[i]);
    } else {
      setIt(1, 0, T, qa[i], qc[i]);
    }
  }
}
