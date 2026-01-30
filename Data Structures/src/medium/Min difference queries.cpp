#include "bits/stdc++.h"
using namespace std;
 
#define MAX 100002
 
int n;
int q;
 
vector<int> v;
 
#define MAGIC 333
#define MM 500
int u[MAGIC][MM];
 
int sz[MAGIC];
 
 
bool ex[MAX];
 
vector<int> vv;
 
vector<int> idx[MAX];
 
set<int> s;
 
 
int main() {
	cin >> n >> q;
	assert(1<=n&&n<=100000);
	assert(1<=q&&q<=100000);
	for (int i = 0; i < n; i++) {
		int a;
		scanf("%d", &a);
		assert(1<=a);
		assert(a<=1000000000);
		v.push_back(a);
		vv.push_back(a);
	}
	sort(vv.begin(), vv.end());
	for (int i = 0; i < v.size(); i++) {
		v[i] = lower_bound(vv.begin(), vv.end(), v[i]) - vv.begin();
		idx[v[i]].push_back(i);
	}
	for (int i = 0; i < n; i+=MAGIC) {
		memset(ex, false, sizeof(ex));
		for (int j = i; j < n; j++) {
			if (ex[v[j]])continue;
			ex[v[j]] = true;
			u[i / MAGIC][sz[i / MAGIC]] = v[j];
			sz[i / MAGIC]++;
			if(sz[i/MAGIC]==MM)break;
		}
	}
	int las = 0;
	while (q--) {
		int a,b;
		scanf("%d%d", &a, &b);
		assert(1<=a&&a<=n&&1<=b&&b<=n);
		int l = (a + las) % n + 1;
		int r = (b + las) % n + 1;
		l--;
		r--;
		assert(0<=l&&0<=r);
		int belong = l / MAGIC;
		s.clear();
		bool en = false;
		for (int i = 0; i < sz[belong]; i++) {  
			int val = u[belong][i];
			int lef = lower_bound(idx[val].begin(), idx[val].end(), l) - idx[val].begin();
			int rig = upper_bound(idx[val].begin(), idx[val].end(), r) - idx[val].begin();
			int oc = rig - lef;
			if (oc == 0)continue;
			if (s.count(oc)) {
				en = true;
				break;
			}
			s.insert(oc);
		}
		if (en) {
			puts("0");
			las = 0;
			continue;
		}
		if (s.size() == 1) {
			puts("-1");
			las = -1;
			continue;
		}
		int ans = INT_MAX;
		int pr = -1;
		for (auto el : s) {
			if (pr != -1) {
				ans = min(ans, el - pr);
			}
			pr = el;
		}
		printf("%d\n", ans);
		las = ans;
	}
	return 0;
}
