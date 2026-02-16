#include <bits/stdc++.h>
#define ff first
#define ss second
#define pb push_back
#define MOD (1000000007LL)
#define LEFT(n) (2*(n))
#define RIGHT(n) (2*(n)+1)
 
using namespace std;
typedef long long ll;
typedef pair<int, int> ii;
typedef pair<int, ii> iii;
 
ll pwr(ll base, ll p, ll mod=MOD){
ll ans = 1;while(p){if(p&1)ans=(ans*base)%mod;base=(base*base)%mod;p/=2;}return ans;
}
 
 
ll gcd(ll a, ll b){
    if(b == 0)  return a;
    return gcd(b, a%b);
}
 
 
const int N = 100002;
int p, z, arr[22];
vector<int> interesting;
ll L, R, DP[22][N][2];
 
 
ll dp(int pos, int rem, int less_than){
 
	if(pos == 19)	return (rem == 0);
	ll &ans = DP[pos][rem][less_than];
	if(ans != -1)	return ans;
 
	ans = 0;
	for(auto dig : interesting){
		if(less_than == 0 && dig > arr[pos])	continue;
		int new_less_than = less_than | (dig < arr[pos]);
		int new_rem = (rem * 10 + dig) % p;
		ans += dp(pos+1, new_rem, new_less_than);
	}
	return ans;
}
 
 
 
ll solve(ll n){

	for(int i=0;i<19;i++){
    	arr[18-i] = n % 10;
    	n /= 10;
    }
 
    memset(DP, -1, sizeof(DP));

    ll ans = 0;
    bool less_than = false;
    for(int pos=0;pos<19;pos++){
    	for(auto dig : interesting){
    		if(dig == 0)	continue;
    		if(!less_than && dig > arr[pos])	continue;
    		ans += dp(pos+1, dig%p, less_than | (dig < arr[pos]));
    	}
    	less_than |= (0 < arr[pos]);
    }

    return ans;
}
 
 
int main(){
 
    ios_base::sync_with_stdio(0);
    cin.tie(0);
 
    cin>>p>>L>>R>>z;
    assert(p >= 1 && p <= 100000);
    assert(L >= 1 && L <= (ll)1e18);
    assert(R >= L && R <= (ll)1e18);
    assert(z >= 1 && z <= 10);
 
    while(z--){
    	int a;
    	cin>>a;
    	assert(a >= 0 && a < 10);
    	interesting.pb(a);
    }
 
    cout<<solve(R) - solve(L-1);   
    return 0;
}

