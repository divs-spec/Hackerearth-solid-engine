#include <bits/stdc++.h>
using namespace std;

#define L(i) 2 * i
#define R(i) 2 * i + 1
#define MOD 1000000007
#define N 100005
#define rank _rank

typedef struct ${
	int cnt, sum_o, sum_e;
} Node;

pair<int, int> M[N];
int n, curr, val[N], rank[N], ans[N];
vector<int> v[N];
Node tree[4 * N];

void update(int node, int l, int r, int idx, int adder){
	if(l == r){
		tree[node].cnt = adder;
		tree[node].sum_e = adder * val[idx];
		tree[node].sum_o = 0;
		return;
	}
	int m = (l + r) >> 1;
	(rank[idx] <= m) ? update(L(node), l, m, idx, adder) : update(R(node), m + 1, r, idx, adder);

	tree[node].cnt = tree[L(node)].cnt + tree[R(node)].cnt;
	if(tree[L(node)].cnt & 1){
		tree[node].sum_e = (tree[L(node)].sum_e + tree[R(node)].sum_o) % MOD;
		tree[node].sum_o = (tree[L(node)].sum_o + tree[R(node)].sum_e) % MOD;
	}
	else{
		tree[node].sum_e = (tree[L(node)].sum_e + tree[R(node)].sum_e) % MOD;
		tree[node].sum_o = (tree[L(node)].sum_o + tree[R(node)].sum_o) % MOD;
	}
	return;
}

void dfs(int start, int parent = 0){
	update(1, 1, n, start, 1);
	ans[start] = (1ll * tree[1].sum_o * tree[1].sum_e) % MOD;
	for(auto next : v[start])
		if(next != parent)
			dfs(next, start);
	update(1, 1, n, start, 0);
}

int main(){
	int i,j,a,b;
    scanf("%d", &n);
    for(i = 0; i < n; i++){
    	scanf("%d", &val[i + 1]);
    	M[i] = {val[i + 1], i + 1};
    }
    sort(M, M + n);
    for(i = 0; i < n; i++)
    	rank[M[i].second] = ++curr;

    for(i = 1; i < n; i++){
    	scanf("%d %d", &a, &b);
    	v[a].push_back(b);
    	v[b].push_back(a);
    }
    dfs(1);
    for(i = 1; i <= n; i++)
    	printf("%d\n", ans[i]);
    return 0;
}
