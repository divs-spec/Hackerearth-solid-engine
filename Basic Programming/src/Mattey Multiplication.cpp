#include<bits/stdc++.h>

#define ll long long

#define fast ios_base::sync_with_stdio(false);cin.tie(NULL);

using namespace std;

ll count(ll n)

{

    ll ans=0;

    while(n)

    {

        n=n&(n-1);

        ans++;

    }

    return ans;

}

ll lefmost(ll n)

{

    ll mask=n;

    mask|=mask>>1;

    mask|=mask>>2;

    mask|=mask>>4;

    mask|=mask>>8;

    mask|=mask>>16;

    mask|=mask>>32;

    mask = mask >> 1;

    return mask&n;

}

ll setBitNumber(ll n)

{

if (n == 0)

return 0;

 

ll msb = 0;

while (n != 0) {

n = n / 2;

msb++;

}

    msb--;

return msb;

}

int main()

{

    fast;

    ll t,n,a;

    cin>>t;

    while(t--)

    {

        ll n,m,k;

        cin>>n>>m;

        while(m)

        {

            k=setBitNumber(m);

            cout<<"("<<n<<"<<"<<k<<")";

            if(count(m)!=1 && m!=0)

            cout<<" "<<"+"<<" ";

            m=lefmost(m);

        }

        cout<<endl;

    }

}
