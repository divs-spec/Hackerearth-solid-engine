#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <string>
#include <vector>
#include <algorithm>
#include <set>
#include <map>
#include <cmath>
#include <ctime>
#include <functional>
#include <sstream>
#include <fstream>
#include <valarray>
#include <complex>
#include <queue>
#include <cassert>
#include <bitset>
using namespace std;

#ifdef LOCAL
	#define debug_flag 1
#else
	#define debug_flag 0
#endif

template <class T1, class T2 >
std::ostream& operator << (std::ostream& os, const pair<T1, T2> &p) 
{
	os << "[" << p.first << ", " << p.second << "]";
	return os;
}

template <class T >
std::ostream& operator << (std::ostream& os, const std::vector<T>& v) 
{
	os << "[";
	bool first = true;
	for (typename std::vector<T>::const_iterator it = v.begin(); it != v.end(); ++it)
	{
		if (!first)
			os << ", ";
		first = false;
		os << *it;
	}
	os << "]";
	return os;
}

#define dbg(args...) { if (debug_flag) { _print(_split(#args, ',').begin(), args); cerr << endl; } else { void(0);} }

vector<string> _split(const string& s, char c) {
	vector<string> v;
	stringstream ss(s);
	string x;
	while (getline(ss, x, c))
		v.emplace_back(x);
	return v;
}

void _print(vector<string>::iterator) {}
template<typename T, typename... Args>
void _print(vector<string>::iterator it, T a, Args... args) {
    string name = it -> substr((*it)[0] == ' ', it -> length());
    if (isalpha(name[0]))
	    cerr << name  << " = " << a << " ";
	else
	    cerr << name << " ";
	_print(++it, args...);
}

typedef long long int int64;

const int N = 3e5 + 100;
const int B_SIZE = 400;
const int B_CNT = N / B_SIZE;
const int INF = (int)2e9 + (int)1e6;

void relax_max(int& a, int b) {
  a = max(a, b);
}

struct Item {
  int id, x, p;

  Item() : id(), x(), p() {}
  Item(int _id, int _x, int _p) :
    id(_id), x(_x), p(_p) {}

  bool operator < (const Item& other) const {
    int f = id - p;
    int other_f = other.id - other.p;
    return f < other_f;
  }
};

std::ostream& operator << (std::ostream& os, const Item &p) 
{
	os << "[id = " << p.id << ", x = " << p.x << ", p = " << p.p << "]";
	return os;
}

struct Query {
  int a, b, id;

  Query() : a(), b(), id() {}
  Query(int _a, int _b, int _id) :
    a(_a), b(_b), id(_id) {}

  bool operator < (const Query& other) const {
    return a < other.a;
  }
};

struct Block {
  Item item_list[B_SIZE];
  int ptr;
  int prefix_max[B_SIZE];
  int suffix_max[B_SIZE];

  Block() {}

  void init(int x_list[], int p_list[]) {
    for (int i = 0; i < B_SIZE; i++) {
      item_list[i] = Item(i, x_list[i], p_list[i]);
    }
    sort(item_list, item_list + B_SIZE);
    update_max();
  }

  void clear_ptr() {
    ptr = 0;
  }

  void update(int pos) {
    while (pos - 1 >= 0 && item_list[pos] < item_list[pos - 1]) {
      swap(item_list[pos], item_list[pos - 1]);
      pos--;
    }

    while (pos + 1 < B_SIZE && item_list[pos + 1] < item_list[pos]) {
      swap(item_list[pos], item_list[pos + 1]);
      pos++;
    }
    update_max();
  }

  void update_max() {
    for (int i = 0; i < B_SIZE; i++) {
      prefix_max[i] = item_list[i].x + item_list[i].id;
      if (i - 1 >= 0) {
        relax_max(prefix_max[i], prefix_max[i - 1]);
      }
    }

    for (int i = B_SIZE - 1; i >= 0; i--) {
      suffix_max[i] = item_list[i].x + item_list[i].p;
      if (i + 1 < B_SIZE) {
        relax_max(suffix_max[i], suffix_max[i + 1]);
      }
    }
  }

  int get_pos(int id) {
    for (int i = 0; i < B_SIZE; i++) {
      if (item_list[i].id == id) {
        return i;
      }
    }
    assert(false);
  }

  void set_x(int id, int x) {
    int pos = get_pos(id);
    item_list[pos].x = x;
    update(pos);
  }

  void set_p(int id, int p) {
    int pos = get_pos(id);
    item_list[pos].p = p;
    update(pos);
  }

  void set_xp(int id, int x, int p) {
    int pos = get_pos(id);
    item_list[pos].x = x;
    item_list[pos].p = p;
    update(pos);
  }

  void clear(int pos) {
    set_xp(pos, 0, -INF);
  }

  int solve(int sh0) {
    while (ptr < B_SIZE) {
      int sh = sh0 + item_list[ptr].id;
      int p = item_list[ptr].p;
      if (sh < p) {
        ptr++;
      } else {
        break;
      }
    }
    int ans = -INF;
    if (ptr - 1 >= 0) {
      int cur_ans = prefix_max[ptr - 1] + sh0;
      relax_max(ans, cur_ans);
    }
    if (ptr < B_SIZE) {
      int cur_ans = suffix_max[ptr];
      relax_max(ans, cur_ans);
    }
    return ans;
  }
};

int n, q;
int x_list[N], p_list[N];
int t_list[N], a_list[N], b_list[N];
int cur_x_list[N], cur_p_list[N];

int blocks_cnt;
Block block_list[B_CNT];

int ans_list[N];

int get_f(int pos, int a) {
  return cur_x_list[pos] + min(cur_p_list[pos], pos - a);
}

void solve(int l, int r) {
  vector<int> interesting_pos;
  for (int i = l; i <= r; i++) {
    if (t_list[i] == 1 || t_list[i] == 2) {
      interesting_pos.push_back(a_list[i]);
    }
  }
  sort(interesting_pos.begin(), interesting_pos.end());
  interesting_pos.erase(unique(interesting_pos.begin(), interesting_pos.end()),
      interesting_pos.end());
  for (int pos : interesting_pos) {
    int b = pos / B_SIZE;
    int pos_in_b = pos % B_SIZE;
    block_list[b].clear(pos_in_b);
  }

  for (int i = 0; i < n; i++) {
    cur_x_list[i] = x_list[i];
    cur_p_list[i] = p_list[i];
  }
  for (int pos : interesting_pos) {
    cur_x_list[pos] = 0;
    cur_p_list[pos] = -INF;
  }

  vector<Query> q_list;
  for (int i = l; i <= r; i++) {
    if (t_list[i] == 3) {
      q_list.push_back(Query(a_list[i], b_list[i], i));
    }
  }
  sort(q_list.begin(), q_list.end());
  for (int i = 0; i < blocks_cnt; i++) {
    block_list[i].clear_ptr();
  }
  for (const Query& query : q_list) {
    ans_list[query.id] = -INF;
    int new_a = query.a;
    int new_b = query.b;
    while (new_a % B_SIZE != 0 && new_a <= new_b) {
      relax_max(ans_list[query.id], get_f(new_a, query.a));
      new_a++;
    }
    while ((new_b + 1) % B_SIZE != 0 && new_a <= new_b) {
      relax_max(ans_list[query.id], get_f(new_b, query.a));
      new_b--;
    }
    for (int i = 0; i < blocks_cnt; i++) {
      int block_a = i * B_SIZE;
      int block_b = (i + 1) * B_SIZE - 1;
      if (new_a <= block_a && block_b <= new_b) {
        int cur_ans = block_list[i].solve(block_a - query.a);
        relax_max(ans_list[query.id], cur_ans);
      }
    }
  }

  for (int i = l; i <= r; i++) {
    if (t_list[i] == 1) {
      x_list[a_list[i]] = b_list[i];
    } else if (t_list[i] == 2) {
      p_list[a_list[i]] = b_list[i];
    } else {
      int cur_ans = -INF;
      for (int pos : interesting_pos) {
        if (a_list[i] <= pos && pos <= b_list[i]) {
          relax_max(cur_ans, x_list[pos] + min(p_list[pos], pos - a_list[i]));
        }
      }
      relax_max(ans_list[i], cur_ans);
    }
  }
  
  for (int pos : interesting_pos) {
    int b = pos / B_SIZE;
    int pos_in_b = pos % B_SIZE;
    block_list[b].set_xp(pos_in_b, x_list[pos], p_list[pos]);
  }
}

int main()
{
#ifdef LOCAL
	freopen ("input.txt", "r", stdin);
#endif

  scanf("%d%d", &n, &q);
  for (int i = 0; i < n; i++) {
    scanf("%d", &x_list[i]);
  }
  for (int i = 0; i < n; i++) {
    scanf("%d", &p_list[i]);
  }
  for (int i = 0; i < q; i++) {
    scanf("%d%d%d", &t_list[i], &a_list[i], &b_list[i]);
    a_list[i]--;
    if (t_list[i] == 3) {
      b_list[i]--;
    }
  }

  blocks_cnt = (n + B_SIZE - 1) / B_SIZE;
  for (int i = 0; i < blocks_cnt; i++) {
    block_list[i].init(x_list + i * B_SIZE, p_list + i * B_SIZE);
  }

  for (int l = 0; l < q; l += B_SIZE) {
    int r = min(l + B_SIZE - 1, q - 1);
    solve(l, r);
  }

  for (int i = 0; i < q; i++) {
    if (t_list[i] == 3) {
      printf("%d\n", ans_list[i]);
    }
  }

	return 0;
}
