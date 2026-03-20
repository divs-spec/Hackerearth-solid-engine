#include <bits/stdc++.h>
using namespace std;

#define fr freopen("in.txt", "r", stdin);
#define fw freopen("out.txt", "w", stdout);
#define sd(x) scanf("%d", &x);
#define sl(x) scanf("%lld", &x);
#define sf(x) scanf("%lf", &x);
#define ll long long
#define ull unsigned long long
#define fi first
#define se second
#define mem(v, x) memset(v, x, sizeof(v));
#define EB emplace_back
#define PB push_back
#define MP make_pair
#define N 1000010

int n, q, arr[N],k;
struct node
{
    int cnt;
    node *left, *right;

    node()
    {
    }

    node(int cnt, node *left, node *right)
    {
        this->cnt = cnt;
        this->left = left;
        this->right = right;
    }

    node *insert(int l, int r, int v);
};

node *null = new node(0, NULL, NULL);
node *root[N];

node *node::insert(int s, int e, int v)
{
    if (v < s || v > e)
        return this;
    if (s == e)
        return new node(this->cnt + 1, null, null);
    int mid = s + e >> 1;
    return new node(this->cnt + 1, this->left->insert(s, mid, v), this->right->insert(mid + 1, e, v));
}

inline int query(node *a, node *b, int s, int e)
{
    int chk1 = b->cnt - a->cnt;
    int chk2 = e - s + 1;

    if (chk1 == chk2 || e <= k)
        return -1;

    if (s == e)
        return s;

    int mid = s + e >> 1;
    int ret = query(a->left, b->left, s, mid);
    if (ret == -1)
        ret = query(a->right, b->right, mid + 1, e);

    return ret;
}

int main()
{

    sd(n);
    sd(k);

    for (int x = 0; x < n; x++)
    {
        sd(arr[x]);
        assert(arr[x]<=1000000 && arr[x]>=1);
    }

    null->left = null->right = null;
    root[0] = null->insert(1, 1000001, arr[0]);

    for (int x = 1; x < n; x++)
        root[x] = root[x - 1]->insert(1, 1000001, arr[x]);

    sd(q);
    assert(q<=100000 && q>=1);

    while (q--)
    {
        int l, r;
        sd(l);
        sd(r);
        assert(l<=r);

        l--;
        r--;

        int res = query(l == 0 ? null : root[l - 1], root[r], 1, 1000001);
        printf("%d\n", res);
    }
}
