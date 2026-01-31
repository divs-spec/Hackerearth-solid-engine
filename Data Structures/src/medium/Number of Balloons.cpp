#include<bits/stdc++.h>
using namespace std;
int n;

namespace segment{
    const int maxn = 1e7; // TODO
    int eof = 1;
    int L[maxn], R[maxn], seg[maxn];
    
    int add(int v, int x, int root, int st = 0, int en = n){
	assert(eof<maxn);
	
	seg[eof] = seg[root] + x;
	L[eof] = L[root];
	R[eof] = R[root];
	root = eof++;
	
	if(en - st < 2)
	    return root;
	int mid = (st + en) >> 1;
	if(v < mid)
	    L[root] = add(v, x, L[root], st, mid);
	else
	    R[root] = add(v, x, R[root], mid, en);
	return root;
    }

    int get(int l, int r, int root, int st = 0, int en = n){
	if(l <= st && en <= r)
	    return seg[root];
	if(r <= st || en <= l)
	    return 0;
	int mid = (st + en) >> 1;
	return get(l, r, L[root], st, mid) +
	    get   (l, r, R[root], mid, en);
    }
}

const int maxn = 1e5 + 10;
int k, q, ar[maxn];
int l[maxn], r[maxn];

void init(){
    cin >> n >> k >> q;
    vector<int> vc;
    for(int i = 0; i < n; i++)
	cin >> ar[i], vc.push_back(ar[i]);
    sort(vc.begin(), vc.end());
    vc.resize(unique(vc.begin(), vc.end()) - vc.begin());
    for(int i = 0; i < n; i++)
	ar[i] = lower_bound(vc.begin(), vc.end(), ar[i]) - vc.begin();
    for(int i = 0; i < q; i++)
	cin >> l[i] >> r[i];
}

vector<int> vc[maxn];
int root[maxn];

inline void add(const int &v,const int &x,int &node){
    if(vc[v].size() >= k)
	node = segment::add(vc[v][vc[v].size() - k], x, node);
    if(vc[v].size() > k)
	node = segment::add(vc[v][vc[v].size() - k - 1], -x, node);
}

void build(){
    int node = 0;
    for(int i = 0; i < n; i++){
	add(ar[i], -1, node);
	vc[ar[i]].push_back(i);
	add(ar[i], +1, node);
	root[i] = node;
    }
}

int main(){
    init();
    build();
    int ans = 0;
    for(int i = 0; i < q; i++){
	l[i] = (l[i] + ans) % n;
	r[i] = (r[i] + ans) % n;
	if(l[i] > r[i])
	    swap(l[i], r[i]);
	ans = segment::get(l[i], r[i] + 1, root[r[i]]);
	cout << ans << '\n';
    }
}
