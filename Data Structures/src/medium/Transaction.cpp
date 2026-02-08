#include <bits/stdc++.h>

using namespace std;

struct node
{
    int l, r, sz;
} seg[100000*25];

int nxt;

int build(int begin, int end)
{
    int root=nxt++;
    if(begin==end)
        return root;
    int mid=(begin+end)/2;
    seg[root].l=build(begin, mid);
    seg[root].r=build(mid+1, end);
    return root;
}

int update(int root, int begin, int end, int x, int v)
{
    if(x<begin || end<x)
        return root;
    seg[nxt]=seg[root];
    root=nxt++;
    if(begin==end)
        seg[root].sz+=v;
    else
    {
        int mid=(begin+end)/2;
        seg[root].l=update(seg[root].l, begin, mid, x, v);
        seg[root].r=update(seg[root].r, mid+1, end, x, v);
        seg[root].sz=seg[seg[root].l].sz+seg[seg[root].r].sz;
    }
    return root;
}

int query(int r1, int begin, int end, int k)
{
    if(k>seg[r1].sz)
        return -1;
    while(begin!=end)
    {
        int mid=(begin+end)/2;
        int lsz=seg[seg[r1].l].sz;
        if(k<=lsz)
            r1=seg[r1].l, end=mid;
        else
            r1=seg[r1].r, begin=mid+1, k-=lsz;
    }
    return begin;
}

int N, Q;
int A[100001];
pair<int, int> B[100001];
int tree[100001];

int main()
{
    scanf("%d%d", &N, &Q);
    assert(1<=N && N<=100000);
    assert(1<=Q && Q<=100000);
    for(int i=1; i<=N; i++)
    {
        scanf("%d", A+i);
        assert(1<=A[i] && A[i]<=100000);
        B[i]=make_pair(-A[i], i);
    }
    sort(B+1, B+1+N);
    tree[0]=build(1, N);
    for(int i=1; i<=N; i++)
        tree[i]=update(tree[i-1], 1, N, B[i].second, 1);
    while(Q--)
    {
        int a, b;
        scanf("%d%d", &a, &b);
        assert(1<=a && a<=100000);
        assert(1<=b && b<=100000);
        int idx=lower_bound(B+1, B+1+N, make_pair(-a+1, -1))-B-1;
        int x=query(tree[idx], 1, N, b);
        if(x!=-1)
            x=A[x];
        printf("%d\n", x);
    }
    return 0;
}
