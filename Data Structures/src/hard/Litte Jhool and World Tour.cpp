#include<bits/stdc++.h>

using namespace std;

int main(){

long long t;

cin>>t;

long long n,q;

while(t--){

    cin>>n>>q;

    long long flag=0;

    while(q--){

        long long x,y;

        cin>>x>>y;

        if(x==y){

            flag=1;

            break;
        }

        else if(x>n && x>y){

            if((x-y)>n){

            flag=1;

            }

        }

    }

    if(flag==0){

        cout<<"YES\n";
    }

    else{

        cout<<"NO\n";
    }

}
    return 0;

}
