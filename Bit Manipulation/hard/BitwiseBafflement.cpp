#include <bits/stdc++.h>
using namespace std;

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    int test;
    cin>>test;

    assert(test>=1 && test<=1e5);

    int totalN = 0;

    while(test--)
    {
        int N;
        cin>>N;

        assert(N>=1 && N<=1e6);

        totalN += N;

        vector<int> A(N+1);
        for(int i=1; i<=N; i++)
        {
            cin>>A[i];

            assert(A[i]>=1 && A[i]<=1e9);
        }

        auto getCnt = [](int X)
        {
            int c = 0;
            for(int b=0; b<30; b++)
                c += (((1ll<<b)&X) > 0);

            return c;
        };

        multiset<int> mst;

        vector<int> pos(30);
        vector<int> cntClosing(N+1);
        cntClosing[0] = 30;

        for(int i=0; i<30; i++)
            mst.insert(0);

        vector<int> oddBitsInXor(N+1);
        vector<int> evenBitsInXor(N+1);

        long long int ans = 0;
        int prefXOR = 0, cnt, j, cnt2, prev;

        evenBitsInXor[1]++;

        for(int i=1; i<=N; i++)
        {
            prefXOR ^= A[i];
            cnt = getCnt(prefXOR);

            evenBitsInXor[i] += evenBitsInXor[i-1];
            oddBitsInXor[i] += oddBitsInXor[i-1];

            for(int b=0; b<30; b++)
            {
                if(((1ll<<b)&A[i]) == 0)
                {
                    cntClosing[pos[b]]--;
                    mst.erase(mst.find(pos[b]));
                }
            }

            j = i;
            cnt2 = getCnt(A[i]);

            while(mst.size() && j>*mst.begin())
            {
                prev = j;
                j = *(--mst.lower_bound(j));

                if(cnt2%2)
                {
                    if(cnt%2 == 1)    ans += oddBitsInXor[prev] - oddBitsInXor[j];
                    if(cnt%2 == 0)    ans += evenBitsInXor[prev] - evenBitsInXor[j];
                }

                cnt2 -= cntClosing[j];
            }

            if(i+1 == N+1)  break;
            if(cnt%2)       oddBitsInXor[i+1]++;
            else            evenBitsInXor[i+1]++;

            for(int b=0; b<30; b++)
            {
                if(((1ll<<b)&A[i]) == 0)
                {
                    pos[b] = i;
                    cntClosing[pos[b]]++;
                    mst.insert(pos[b]);
                }
            }
        }

        cout<<ans<<"\n";
    }

    assert(totalN>=1 && totalN<=1e6);
}

