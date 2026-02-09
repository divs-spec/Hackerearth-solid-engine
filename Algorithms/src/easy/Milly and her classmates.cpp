#include<bits/stdc++.h>
using namespace std;
#define ll				long long int
#define MOD				1000000007
#define si(a)			scanf("%d", &a)
#define sl(a)			scanf("%lld", &a)
#define pi(a)			printf("%d", a)
#define pl(a)			printf("%lld", a)
#define pn 				printf("\n")
ll pow_mod(ll a, ll b) {
	ll res = 1;
	while(b) {
		if(b & 1)
			res = (res * a) % MOD;
		a = (a * a) % MOD;
		b >>= 1;
	}
	return res;
}
int ar[10005];
int main() {
	int t;
	cin >> t;
	assert(t >= 1 && t <= 10);
	while(t--) {
		int n;
		cin >> n;
		assert(n >= 1 && n <= 10000);
		for(int i = 0; i < n; ++i) {
			cin >> ar[i];
			assert(ar[i] >= 1 && ar[i] <= (int)(1e9));
		}
		int st = -1, ed = -1;
		for(int i = 1; i < n; ++i) {
			if(ar[i] < ar[i - 1]) {
				st = i;
				break;
			}
		}
		if(st != -1) {
			ed = n;
			for(int i = st; i < n; ++i) {
				if(ar[i] > ar[i - 1]) {
					ed = i;
					break;
				}
			}
			reverse(ar + st - 1, ar + ed);
			for(int i = 1; i < n; ++i) {
				if(ar[i] < ar[i - 1]) {
					st = -1;
					ed = -1;
					break;
				}
			}
		}
		cout << st << " " << ed << endl;
	}
	return 0;
}
