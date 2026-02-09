#include <bits/stdc++.h>

using namespace std;

int n;

inline void fiu(unsigned long long int *a)
{
 register char c=0;
 while (c<33) c=getchar_unlocked();
 *a=0;
 int tmp = 0;
 while (c>33)
 {
     if ( c == 45 ) tmp = 1;
     else *a=*a*10+c-'0';
     c=getchar_unlocked();
 }
 if ( tmp == 1 ) *a = 0-(*a);
}

inline void fi(int *a)
{
 register char c=0;
 while (c<33) c=getchar_unlocked();
 *a=0;
 int tmp = 0;
 while (c>33)
 {
     if ( c == 45 ) tmp = 1;
     else *a=*a*10+c-'0';
     c=getchar_unlocked();
 }
 if ( tmp == 1 ) *a = 0-(*a);
}
long long f(unsigned long long X, vector <unsigned long long> v)
{
	long long ans = 0;
	for ( int i = 1; i < (1<<n); i++ ) {
		long long pro = 1;
		for ( int j = 0; j < n; j++ ) {
			if ( i&(1<<j) ) pro = pro*v[j];
		}
		int parity = __builtin_popcount(i);
		if ( parity&1 ) ans += X/pro;
		else ans -= X/pro;
	}	
	return ans;
}

int main()
{
	int t;
	unsigned long long K,val,L,R,mx,mid;
	fi(&t);
	while ( t-- ) {
		string s;
		vector <unsigned long long> v;
		cin >> s;
		fiu(&K);
		for ( int i = 0; i < 40; i++ ) {
			if ( s[i] == '1' ) v.push_back((long long)(i+1));
		}
		n = (int)v.size();
		L = 1LL;
		R = (long long)(1e19);
		while ( L <= R ) {
			mid = (L+R)/2;
			val = f(mid,v);
			if ( val == K ) {
                 mx = 0;
                 for ( int i = 0; i < n; i++ ) {
                     mx = max(mx, (mid/v[i])*v[i]);
                 }
                 printf("%llu\n", mx);
                 break;
            }
			else if ( val > K ) R = mid-1;
			else L = mid+1;
		}
	}
    return 0;
}
