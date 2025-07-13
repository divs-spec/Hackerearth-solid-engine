#include<bits/stdc++.h>
using namespace std;
int main()
{
	int tmp,n,i,c,ans;
	cin>>n;
	ans = tmp =0;
	stack <pair <int,int> > st;
	vector <pair <int,int> > v;
	for(i=0;i<n;i++)
	{
		cin>>c;
		v.push_back({c,-1});
		if(st.empty())
		{
			if(c>0)
			st.push(make_pair(c,i));
		}
		else
		{
			if(c<0&&st.top().first==-c)
			{
				v[i].second = 1;
				v[st.top().second].second = 1;
				st.pop();
			}
			else
			{
				st.push(make_pair(c,i));
			}
			
		}
	}

	for(i=0;i<v.size();i++)
	{
		if(i!=0&&v[i].second==1&&v[i-1].second==1)
		{
			if(v[i].first<0)
			{
				tmp+=2;
			}
		}
		else
		{
			tmp=0;
		}
		ans = max(tmp,ans);
	}
	cout<<ans<<endl;
	return 0;
}
