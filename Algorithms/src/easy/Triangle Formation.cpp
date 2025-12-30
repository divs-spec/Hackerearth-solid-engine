#include <bits/stdc++.h>
using namespace std;

bool isRightTriangle(long long a, long long b, long long c) {
    // All sides must be positive
    if (a <= 0 || b <= 0 || c <= 0) return false;

    vector<long long> v = {a, b, c};
    sort(v.begin(), v.end());

    // Triangle inequality
    if (v[0] + v[1] <= v[2]) return false;

    // Use int128 to avoid overflow
    __int128 x = v[0];
    __int128 y = v[1];
    __int128 z = v[2];

    return x * x + y * y == z * z;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int t;
    cin >> t;

    while (t--) {
        long long a, b, c, x;
        cin >> a >> b >> c >> x;

        bool ok = false;

        // Add x to exactly two numbers
        ok |= isRightTriangle(a + x, b + x, c);
        ok |= isRightTriangle(a + x, b, c + x);
        ok |= isRightTriangle(a, b + x, c + x);

        cout << (ok ? "YES\n" : "NO\n");
    }

    return 0;
}
