#include <bits/stdc++.h>
#define ll long long

using namespace std;
const int ma = 1e5+5;
ll mal[ma], mar[ma] , mil[ma], mir[ma], ar[ma], a[ma], mins[ma],mp[ma], fre[1000005];
int n,x,k;
stack <int> st;
void great(bool f)
{
  if(f)
  {
    for(int i=1;i<=n;i++)
    {
      if(i==0)
        st.push(i);
      else
      {
        while(!st.empty() and a[i] > a[st.top()])
        {
          mar[st.top()] = i;
          st.pop();
        }
        st.push(i);
      }
    }
    while(!st.empty())
    {
      mar[st.top()]=n+1;
      st.pop();
    }
  }
  else
  {
    for(int i=n;i>=1;i--)
    {
      if(i==n)
        st.push(i);
      else
      {
        while(!st.empty() and a[i] > a[st.top()])
        {
          mal[st.top()] = i;
          st.pop();
        }
        st.push(i);
      }
    }
    while(!st.empty())
    {
      mal[st.top()]=0;
      st.pop();
    }
  }
}
void smal(bool f)
{
  if(f)
  {
    for(int i=1;i<=n;i++)
    {
      if(i==0)
        st.push(i);
      else
      {
        while(!st.empty() and a[i] < a[st.top()])
        {
          mir[st.top()] = i;
          st.pop();
        }
        st.push(i);
      }
    }
    while(!st.empty())
    {
      mir[st.top()]=n+1;
      st.pop();
    }
  }
  else
  {
    for(int i=n;i>=1;i--)
    {
      if(i==n)
        st.push(i);
      else
      {
        while(!st.empty() and a[i] < a[st.top()])
        {
          mil[st.top()] = i;
          st.pop();
        }
        st.push(i);
      }
    }
    while(!st.empty())
    {
      mil[st.top()]=0;
      st.pop();
    }
  }
}
bool cmp(ll a, ll b)
{
  return a > b;
}
int main(int argc, char* argv[])
{
  /*freopen(argv[1],"r",stdin);
  freopen(argv[2],"w",stdout);*/
  cin>>n>>x>>k;
  assert(1<=n and n<=1e5);
  assert(1<=x and x<=n);
  for(int i=1;i<=n;i++)
    cin>>a[i],fre[a[i]]++,assert(0<=a[i] and a[i]<=1e6);
  /*for(int i=0;i<n;i++)
  {
    for(int j=i+1;j<n;j++)
    {
      //cout<<a[i]<<" "<<a[j]<<endl;
      assert(abs(a[i]-a[j])>=2);
    }
  }*/
    for(int i=0;i<=1e6;i++)
      assert(fre[i]<=1);
  great(true);
  great(false);
  smal(true);
  smal(false);
  
  ll mas=0, mis=0;
  for(int i=1;i<=n;i++)
  {
    mp[i] = ((mar[i]-i)*(i-mal[i]))-((mir[i]-i)*(i-mil[i]));
    mas+= ((mar[i]-i)*(i-mal[i]))*a[i];
    mis+= (mir[i]-i)*(i-mil[i])*a[i];
  }
  sort(mp+1,mp+n+1, cmp);
  int c=0;
  
  ll sum = mas-mis;
  for(int i=1;i<=x;i++)
  {
      if(mp[i]>0)
        sum+=mp[i];
  }

  cout<<sum<<endl;

  

  return 0;
}
