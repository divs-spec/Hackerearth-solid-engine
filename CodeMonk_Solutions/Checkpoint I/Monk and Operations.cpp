#include<bits/stdc++.h>
using namespace std;
int main()
{
	long long int fans,sum,tmp,s,ans,v1,v2,v3,v4,n,m,i,j;
	cin>>n>>m;
	long long int a[n][m];
	for(i=0;i<n;i++)
	{
		for(j=0;j<m;j++)
		{
			cin>>a[i][j];
		}
	}
	ans=0;
	cin>>v1>>v2>>v3>>v4;
	for(i=0;i<n;i++)
	{
		s=tmp=sum=0;
		for(j=0;j<m;j++)
		{
			s=s+abs(a[i][j]);
			tmp=tmp+abs(a[i][j]+v1);
			sum=sum+abs(v2);
		}
		s = max(s,tmp);
		s = max(s,sum);
		ans+=s;
	}
	fans = ans;
	ans=0;
	for(i=0;i<m;i++)
	{
		s=tmp=sum=0;
		for(j=0;j<n;j++)
		{
			s=s+abs(a[j][i]);
			tmp=tmp+abs(a[j][i]+v3);
			sum=sum+abs(v4);
		}
		s = max(s,tmp);
		s = max(s,sum);
		ans+=s;
	}
	cout<<max(ans,fans)<<endl;
	return 0;
}
