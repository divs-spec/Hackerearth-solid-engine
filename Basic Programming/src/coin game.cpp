#include<bits/stdc++.h>

using namespace std;

int main(){

    ios_base::sync_with_stdio(false);

    cin.tie(NULL);

    int t;

    cin>>t;

    while(t--){

        int n;

        cin>>n;

        int A[n];

        for(int i = 0; i<n; i++){

            cin>>A[i];

        }

        int c = 0;

        for(int i = 0; i<n; i++){

            while(A[i]%2==0){

                c++;

                A[i] = A[i]/2;

            }

        }

        if(c%2==0){

            cout<<"Alan"<<endl;

        }

        else{

            cout<<"Charlie"<<endl;

        }

    }

    return 0;

}
