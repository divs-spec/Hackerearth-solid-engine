#include<bits/stdc++.h>
using namespace std;
int main()
{
    int n,i,c,t;
    cin>>t;
    while(t--)
    {
        cin>>n;
        long long int a[n];
        for(i=0;i<n;i++)
            cin>>a[i];
        sort(a,a+n);
        c=1;
        for(i=1;i<n;i++)
        {
            if(a[i]==a[i-1])
                c++;
            else
            {
                break;
            }
        }
        if(c%2!=0)
            cout<<"Lucky"<<endl;
        else
        {
            cout<<"Unlucky"<<endl;
        }
        
    }
    return 0;
}
