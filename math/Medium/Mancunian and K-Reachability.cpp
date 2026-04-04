#include <bits/stdc++.h>
using namespace std;

#define N 10000007
#define siz 8

int arr[N],k;

bool reachable(int a, int b){
	int i, cnt = 0;
	for(i = 0; i < siz; i++){
		if(b == 0 && a != 0)
			return 0;
		if(a % 10 != b % 10)
			cnt++;
		a /= 10;
		b /= 10;
	}
	return cnt <= k;
}

int main(){
	int i,j,q,n,l,r;
	scanf("%d %d %d", &n, &k, &q);
	for(i = 1; i < N; i++)
		arr[i] = reachable(i, n);
	for(i = 1; i < N; i++)
		arr[i] += arr[i - 1];
	while(q--){
		scanf("%d %d", &l, &r);
		printf("%d\n", arr[r] - arr[l - 1]);
	}
    return 0;
}
