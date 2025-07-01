#include<bits/stdc++.h>

using namespace std;

 

int main()

{

int n, sum=0;

cin>>n;

string str;

map<int, int> mp;

for(int i=0; i<n; i++)

{

cin>>str;

for(int i=0; i<str.length(); i++)

{

if(str[i] == '1')

mp[i] = 1;

}

}

for(auto it=mp.begin(); it!=mp.end(); it++)

sum += it->second;

if(sum%2 == 0)

cout<<"B"<<"\n";

else

cout<<"A"<<"\n";

cout<<sum;

}
