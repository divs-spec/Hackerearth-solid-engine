#include <bits/stdc++.h>
using namespace std;

#define ll long long

class S
{
    public:
    const int N = 10000005;

    ll int *t, re=0, n;

    S(vector<int> &x, int n)
    {
        t = new ll int[N];
        this->n = n;
        _B(x);
    }

    void _B(vector<int> &x)
    {
        for (int i = 0; i < n; ++i)     t[n+i]=x[i];
        for (int i = n - 1; i > 0; --i) t[i] = t[i<<1] + t[i<<1|1];
    }

    void _R(int l, int r)
    {
        for (l += n, r += n; l < r; l >>= 1, r >>= 1)
        {
            if(l&1)   re = re + t[l++];
            if(r&1)   re = re + t[--r];
        }
    }

    ll int _SUM(int l, int r)
    {
        re = 0;_R(l, r);

        return re;
    }

    void _U(int p, ll int v)
    {
        for(t[p += n] = v; p > 1; p >>= 1)
            t[p>>1] = (t[p] + t[p^1]);
    }
};

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    int maxN = 2e6+1;

    vector<ll int> sumOfDiv(maxN);

    for(int i=1; i<maxN; i++)
    {
        for(int j=i; j<maxN; j+=i)
            sumOfDiv[j] += i;
    }

    vector<pair<int, int>> sumOfDivOrder;
    for(int i=1; i<maxN; i++)
        sumOfDivOrder.push_back({sumOfDiv[i], i});

    sort(sumOfDivOrder.begin(), sumOfDivOrder.end());

    vector<int> order(maxN);
    for(int i=0; i<sumOfDivOrder.size(); i++)
        order[sumOfDivOrder[i].second] = i;

    int Q, N, M;
    cin>>Q;

    vector<ll int> ans(Q);

    assert(Q>=1 && Q<=1e6);

    vector<array<int, 3>> queries;
    int tmp = 0;

    while(Q--)
    {
        N, M;
        cin>>N>>M;

        assert(1<=N && N<=2e6);
        assert(1<=M && M<=N);

        queries.push_back({N, M, tmp++});
    }

    sort(queries.rbegin(), queries.rend());

    vector<int> x(maxN);

    S segment_tree_sum(x, maxN);
    S segment_tree_count(x, maxN);

    for(int i=1, low, high, mid, j; i<maxN; i++)
    {
        segment_tree_count._U(order[i], 1);
        segment_tree_sum._U(order[i], sumOfDiv[i]);

        while(queries.size() && queries.back()[0]==i)
        {
            auto &[N, M, pos] = queries.back();
            queries.pop_back();

            low=0, high=maxN-1, mid, j=-1;

            while(low <= high)
            {
                mid = (low + high)/2;

                if(segment_tree_count._SUM(mid, maxN) >= M)
                    j=mid, low=mid+1;
                else
                    high=mid-1;
            }

            assert(j!=-1);

            ans[pos] = segment_tree_sum._SUM(j, maxN);
        }
    }

    for(auto e: ans)        cout<<e<<"\n";
}
