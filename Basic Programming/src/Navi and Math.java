#include <bits/stdc++.h>
using namespace std;

#define MOD 1000000007
#define MAXM 10000000

// Fast modular exponentiation
long long power(long long base, long long exponent, long long modulus) {
    long long result = 1;
    base %= modulus;
    while (exponent > 0) {
        if (exponent % 2 == 1)
            result = (result * base) % modulus;
        base = (base * base) % modulus;
        exponent >>= 1;
    }
    return result;
}

int main() {
    int t;
    cin >> t;
    assert(1 <= t && t <= 10);

    for (int tt = 1; tt <= t; tt++) {
        int n;
        cin >> n;
        assert(2 <= n && n <= 16);

        vector<long long> a(n);
        for (int i = 0; i < n; i++) {
            cin >> a[i];
            assert(1 <= a[i] && a[i] <= MAXM);
        }

        long long ans = LLONG_MIN;
        int totalMasks = 1 << n;

        // Iterate over all subsets using bitmask
        for (int mask = 1; mask < totalMasks; mask++) {
            long long sum = 0, prod = 1;
            int count = 0;

            for (int i = 0; i < n; i++) {
                if (mask & (1 << i)) {
                    count++;
                    sum = (sum + a[i]) % MOD;
                    prod = (prod * a[i]) % MOD;
                }
            }

            if (count >= 2) {
                long long inv = power(sum, MOD - 2, MOD);  // modular inverse
                long long val = (prod * inv) % MOD;
                ans = max(ans, val);
            }
        }

        cout << "Case #" << tt << ": " << ans << endl;
    }
    return 0;
}
