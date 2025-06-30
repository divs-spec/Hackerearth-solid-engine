#include<bits/stdc++.h>

using namespace std ;

#define vi vector<int>

#define pii pair<int,int>

#define vii pair<pii>

// loops

#define lp(i,a,b) for(int i=a;i<b;i++)

#define lpe(i,a,b) for(int i=a;i<=b;i++)

#define lpr(i,a,b) for(int i=a;i>b;i--)

#define lpre(i,a,b) for(int i=a;i>=b;i--)

// shortcuts

#define ff first

#define ss second

#define mp make_pair

#define pb push_back

#define IOS ios_base::sync_with_stdio(0);cin.tie(0);cout.tie(0) //to synchronize the input of cin and scanf

// bit manipulation

#define bit(x,i) (x&(1<<i)) //select the bit of position i of x

#define lowbit(x) ((x)&((x)^((x)-1))) //get the lowest bit of x

#define hBit(msb,n) asm("bsrl %1,%0" : "=r"(msb) : "r"(n)) //get the highest bit of x, maybe the fastest

#define setBits(x) builtin_popwent(x)

#define lli long long int

#define ll long long


 

int main()

{

    IOS;

    int t;

    cin >> t;

    while(t--)

    {

        ll n;

        cin >> n;

        ll ct = __builtin_popcountll(n);

        cout << (1LL<<ct) <<"\n";

    }

    return 0 ;

}
