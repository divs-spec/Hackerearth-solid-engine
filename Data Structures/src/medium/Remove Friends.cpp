#include <bits/stdc++.h>

using namespace std;

typedef long long int lli;

int main()

{

int t;

cin>>t;

while(t--)

{

lli n,k;

cin>>n>>k;

lli i = 0;

deque<lli> s;

vector<lli> arr(n);

for(i = 0;i<n;i++)

cin>>arr[i];

int count1 = 0;

for(i = 0;i<n;i++)

{

if(s.empty() || s.back()>arr[i])

s.push_back(arr[i]);

else

{

while(!s.empty() && (count1 != k) && s.back()<arr[i] )

{

s.pop_back();

count1++;

}

s.push_back(arr[i]);

if(count1 == k)

{

i++;

for(;i<n;i++)

s.push_back(arr[i]);

}

}

}

while(!s.empty())

{

long long int jas = s.front();

cout<<jas<<" ";

s.pop_front();

}

cout<<endl;

}

}
