#include<bits/stdc++.h>
#define int long long int
using namespace std;


struct nod{
    bool is_full;
    int lval;
    int lcnt;
    int rval;
    int rcnt;
    int mx;
};

nod merge(nod a,nod b){
    nod ret;
    if(a.is_full && b.is_full && a.lval == b.lval ){
        ret.is_full=true;
        ret.lval = a.lval;
        ret.rval = a.lval;
        ret.lcnt = a.lcnt + b.lcnt;
        ret.rcnt = ret.lcnt;
        //ret.mx = a.mx + b.mx;
        if(ret.rval< ret.rcnt){
            ret.mx = a.rval * (a.rval + 1ll) /2;
        } else {
            int h = a.rval - (ret.rcnt);
            ret.mx =  a.rval * (a.rval + 1ll) /2 - h*(h+1ll)/2;
        }
        return ret;
    }
    ret.is_full=false;
    if(a.is_full && a.lval == b.lval){
        ret.lcnt = a.lcnt + b.lcnt;
        ret.lval = a.lval;
    } else {
        ret.lcnt = a.lcnt;
        ret.lval = a.lval;
    }

    if(b.is_full && b.rval == a.rval){
        ret.rcnt = b.rcnt + a.rcnt;
        ret.rval = b.rval;
    } else {
        ret.rval = b.rval;
        ret.rcnt = b.rcnt;
    }

    ret.mx= max(a.mx,b.mx);
    if(a.rval == b.lval ){
        if(a.rval< a.rcnt + b.lcnt){
            ret.mx = max(ret.mx, a.rval * (a.rval + 1ll) /2);
        } else {
            int h = a.rval - (a.rcnt + b.lcnt);
            ret.mx = max(ret.mx, a.rval * (a.rval + 1ll) /2 - h*(h+1ll)/2);
        }
        
    }

    return ret;
}

nod sgt[1201200];

int arr[300300];
int n,q;

void build(int nd,int l,int r){
    if(l==r){
        sgt[nd].is_full=true;
        sgt[nd].lcnt=1;
        sgt[nd].rcnt=1;
        sgt[nd].lval=arr[l];
        sgt[nd].rval=arr[l];
        sgt[nd].mx=arr[l];
        return;
    }
    int mid=(r+l)/2;
    build(2*nd,l,mid);
    build(2*nd+1,mid+1,r);
    sgt[nd]=merge(sgt[2*nd],sgt[2*nd+1]);
}

void update(int nd, int l, int r, int index)
{
    if(r < index or index < l) return;

    if(l==r and l == index){
        sgt[nd].is_full=true;
        sgt[nd].lcnt=1;
        sgt[nd].rcnt=1;
        sgt[nd].lval=arr[l];
        sgt[nd].rval=arr[l];
        sgt[nd].mx=arr[l];
        return;
    }
    int mid=(r+l)/2;
    update(2*nd,l,mid,index);
    update(2*nd+1,mid+1,r,index);
    sgt[nd]=merge(sgt[2*nd],sgt[2*nd+1]);    
}

nod query(int nd,int l,int r,int s,int e){
    if(s<=l && r<=e){
        return sgt[nd];
    }
    int mid=(r+l)/2;
    bool isSet=false;
    nod ret;
    if(s<=mid){
        ret=query(2*nd,l,mid,s,e);
        isSet=true;
    }
    if(mid+1<=e){
        if(isSet){
            ret=merge(ret,query(2*nd+1,mid+1,r,s,e));
        } else {
            ret=query(2*nd+1,mid+1,r,s,e);
        }
    }
    return ret;
}
signed main(){
    cin >> n >> q;
    assert(1 <= n and n <= 300000);
    assert(1 <= q and q <= 300000);
    for(int i = 1 ; i <= n ; i++){
        cin >> arr[i];
        assert(1 <= arr[i] and arr[i] <= 1000000);
    }
    build(1,1,n);
    while(q--){
        int type;
        cin >> type;
        assert(1 <= type and type <= 2);
        if(type == 1)
        {
            int l,r;
            cin >> l >> r;
            assert(1 <= l and l <= r and r <= n);
            assert(l<=r);
            cout<<query(1,1,n,l,r).mx << " ";
        }
        else{
            int x, v;
            cin >> x >> v;
            assert(1 <= x and x <= n);
            assert(1 <= v and v <= 1000000);
            arr[x] = v;
            update(1,1,n,x);
        }
    }
    cout << endl;
}
