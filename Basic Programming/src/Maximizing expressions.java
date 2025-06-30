#include <bits/stdc++.h>
using namespace std;

typedef long long ll;
const int MAXN = 100009;

ll n;
ll A[MAXN], B[MAXN], C[MAXN];

// Returns whether the bit at `pos` in `val` is set (1)
bool getBit(ll val, ll pos) {
    return (val >> pos) & 1LL;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> n;

    for (ll i = 1; i <= n; i++) cin >> A[i];
    for (ll i = 1; i <= n; i++) cin >> B[i];
    for (ll i = 1; i <= n; i++) cin >> C[i];

    ll totalSum = 0;

    for (ll i = 1; i <= n; i++) {
        ll temp = 0;
        bool hasValidBit = false;

        for (ll j = 0; j < 30; j++) {
            bool bitA = getBit(A[i], j);
            bool bitB = getBit(B[i], j);
            bool bitC = getBit(C[i], j);

            bool diffAB = bitA ^ bitB;
            bool diffABC = diffAB ^ bitC;

            // Add max(diffAB, diffABC) * (1 << j)
            temp += (1LL << j) * max(diffAB, diffABC);

            // If this bit is set in both diffABC and C[i], mark flag
            if (diffABC && bitC) {
                hasValidBit = true;
            }
        }

        // If no such valid bit was found, we must subtract the least significant set bit of C[i]
        if (!hasValidBit) {
            int leastBit = 0;
            while (!getBit(C[i], leastBit)) {
                leastBit++;
            }

            // Safety check: this bit must be set in temp
            if (!getBit(temp, leastBit)) {
                cerr << "Error: Bit not set in temp that is expected.\n";
                return 1;
            }

            temp -= (1LL << leastBit);
        }

        totalSum += temp;
    }

    cout << totalSum << '\n';
    return 0;
}
