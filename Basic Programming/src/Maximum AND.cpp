#include<bits/stdc++.h>

#define ll long long

#define fast ios_base::sync_with_stdio(false);cin.tie(NULL);    

using namespace std;

int main()

{

    fast;

    ll t,i,j;

    cin>>t;

    while(t--)

    {

        cin>>i>>j;

        if((j-i)==1)

        cout<<(i&j)<<endl;

        else

        {

            cout<<max(j&(j-1),(j-1)&(j-2))<<endl;

        }

    }

}
