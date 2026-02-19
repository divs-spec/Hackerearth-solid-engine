#include <bits/stdc++.h>
#define ff first
#define ss second
#define pb push_back
#define LEFT(n) (2*(n))
#define RIGHT(n) (2*(n)+1)
 
using namespace std;
typedef long long ll;
typedef pair<int, int> ii;


const int N = 400005;
int n, q, rank_[20][N], SA[N], inv_SA[N], lcp[N];
int buffer[N], cnt[N], sparse[20][N], powers[N];
vector<ii> qarr[N];
ll tree[2][4*N], lazy[4*N], ans[N], ps[N];
char str[N];


void counting_sort(int k, int offset){

	memset(cnt, 0, (max(n, 30))*sizeof(cnt[0]));
	for(int i=1;i<=n;i++){
		int idx = (i+offset <= n ? rank_[k-1][i+offset] : 0);
		cnt[idx]++;
	}

	for(int i=0,sum=0;i<=max(300, n);i++){
		swap(sum, cnt[i]);
		sum += cnt[i];
	}

	for(int i=1;i<=n;i++){
		int idx = (SA[i]+offset <= n ? rank_[k-1][SA[i]+offset] : 0);
		buffer[++cnt[idx]] = SA[i];
	}

	for(int i=1;i<=n;i++)
		SA[i] = buffer[i];
}




void build_SA(){


	for(int i=1;i<=2*n;i++)
		for(int j=0;j<20;j++)
			rank_[j][i] = 0;

	for(int i=1;i<=n;i++){
		rank_[0][i] = str[i]-'a'+1;
		SA[i] = i;
	}


	int k = 1, curr_n = 1;
	for(;curr_n<=n;k++,curr_n*=2){

		counting_sort(k, curr_n);
		counting_sort(k, 0);

		int temp = 0;
		for(int i=1;i<=n;i++)
			if(i > 1 && rank_[k-1][SA[i-1]] == rank_[k-1][SA[i]] && rank_[k-1][SA[i-1]+curr_n] == rank_[k-1][SA[i]+curr_n])
				rank_[k][SA[i]] = temp;
			else
				rank_[k][SA[i]] = ++temp;
	}

	for(int i=1;i<=n;i++){
		inv_SA[SA[i]] = i;
		ps[i] = ps[i-1] + (n - SA[i] + 1);
	}

	lcp[1] = 0;
	for(int i=2;i<=n;i++){
		int cnt = curr_n;
		int x = SA[i-1], y = SA[i];
		for(int j=k-1;j>=0;j--,cnt/=2)
			if(x+cnt-1 <= n && y+cnt-1 <= n && rank_[j][x] > 0 && rank_[j][x] == rank_[j][y]){
				x += cnt;	y += cnt;	lcp[i] += cnt;
			}
	}

// cout<<"SA: ";for(int i=1;i<=n;i++)	cout<<SA[i]<<" ";cout<<endl;
// cout<<"inv_SA: ";for(int i=1;i<=n;i++)	cout<<inv_SA[i]<<" ";cout<<endl;
// cout<<"lcp: ";for(int i=1;i<=n;i++)	cout<<lcp[i]<<" ";cout<<endl;
// cout<<"ps: ";for(int i=1;i<=n;i++)	cout<<ps[i]<<" ";cout<<endl;
}


void update(int node, int s, int e, int lo, int hi, int val){
	if(s > e || lo > hi)	return;
	if(lazy[node] != 0){
		tree[0][node] = lazy[node];
		tree[1][node] = lazy[node] * 1LL * (e-s+1);
		if(s != e){
			lazy[LEFT(node)] = lazy[node];
			lazy[RIGHT(node)] = lazy[node];
		}
		lazy[node] = 0;
	}
	if(s > hi || lo > e)	return;
	if(s >= lo && e <= hi){
		tree[0][node] = val;
		tree[1][node] = val * 1LL * (e-s+1);
		if(s != e){
			lazy[LEFT(node)] = val;
			lazy[RIGHT(node)] = val;
		}
		return;
	}
	int mid = (s + e)/2;
	update(LEFT(node), s, mid, lo, hi, val);
	update(RIGHT(node), mid+1, e, lo, hi, val);
	tree[0][node] = max(tree[0][LEFT(node)], tree[0][RIGHT(node)]);
	tree[1][node] = tree[1][LEFT(node)] + tree[1][RIGHT(node)];
}


ll query(int node, int s, int e, int lo, int hi){
	if(s > e || lo > hi)	return 0;
	if(lazy[node] != 0){
		tree[0][node] = lazy[node];
		tree[1][node] = lazy[node] * 1LL * (e-s+1);
		if(s != e){
			lazy[LEFT(node)] = lazy[node];
			lazy[RIGHT(node)] = lazy[node];
		}
		lazy[node] = 0;
	}
	if(s > hi || lo > e)	return 0;
	if(s >= lo && e <= hi)	return tree[1][node];
	int mid = (s + e)/2;
	ll a = query(LEFT(node), s, mid, lo, hi);
	ll b = query(RIGHT(node), mid+1, e, lo, hi);
	return a+b;
}


int binary_search(int node, int s, int e, int val){
	if(tree[0][node] < val)	return 0;
	if(s == e)	return s;
	int mid = (s + e)/2;
	int ans = binary_search(RIGHT(node), mid+1, e, val);
	if(ans != 0)	return ans;
	return binary_search(LEFT(node), s, mid, val);
}


int find_boundary(int len){
	return binary_search(1, 1, n, len);
}

ll get_suffix_sum(int idx){
	return query(1, 1, n, idx, n);
}

int rmq(int i, int j){
	int k = powers[j-i+1];
	return min(sparse[k][i], sparse[k][j-(1<<k)+1]);
}

int get_leftmost_equivalent_string(int idx, int len){
	int ans = idx, lo = 1, hi = idx-1;
	while(lo <= hi){
		int mid = (lo + hi)/2;
		if(rmq(mid+1, idx) >= len){
			ans = min(ans, mid);
			hi = mid-1;
		}
		else{
			lo = mid+1;
		}
	}
	return ans;
}


int main(){
 
    // ios_base::sync_with_stdio(0);
    // cin.tie(0);

	for(int i=2;i<N;i*=2)	powers[i] = 1;
	for(int i=1;i<N;i++)	powers[i] += powers[i-1];

    scanf("%s", str+1);
    n = strlen(str+1);
    assert(n >= 1 && n <= 200000);
    build_SA();

    for(int i=1;i<=n;i++)
		sparse[0][i] = lcp[i];
	for(int j=1;(1<<j)<=n;j++)
		for(int i=1;i+(1<<j)-1<=n;i++)
			sparse[j][i] = min(sparse[j-1][i], sparse[j-1][i+(1<<(j-1))]);
	
    scanf("%d", &q);
    assert(q >= 1 && q <= 200000);
    for(int i=1;i<=q;i++){
    	int l, r;
    	scanf("%d%d", &l, &r);
    	assert(l >= 1 && l <= r && r <= n);
    	qarr[get_leftmost_equivalent_string(inv_SA[l], r-l+1)].pb(ii(i, r-l+1));
    }

    for(int i=n;i>=1;i--){

    	for(auto it : qarr[i]){

    		int idx = it.ff, len = it.ss;
    		int boundary = max(i, find_boundary(len));

    		ans[idx] = ps[i-1] + (boundary - i + 1)*1LL*len + get_suffix_sum(boundary+1);
    	}

    	int boundary = max(i, find_boundary(lcp[i]));
    	update(1, 1, n, i, boundary, lcp[i]);
    }

    for(int i=1;i<=q;i++)
    	printf("%lld\n", ans[i]);
    return 0;
}
