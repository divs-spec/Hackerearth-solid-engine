#include <bits/stdc++.h>
using namespace std;

const long long MOD = 1000000007;

long long count_valid_permutations(int n, vector<int>& A) {
    // Check non-increasing
    for (int i = 1; i < n; i++) {
        if (A[i] > A[i - 1]) {
            return 0;
        }
    }

    // Track used elements
    unordered_set<int> used(A.begin(), A.end());
    int remaining = n - (int)used.size();

    // Compute factorial modulo MOD
    long long factorial = 1;
    for (int i = 1; i <= remaining; i++) {
        factorial = (factorial * i) % MOD;
    }

    return factorial;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int t;
    cin >> t;

    while (t--) {
        int n;
        cin >> n;

        vector<int> A(n);
        for (int i = 0; i < n; i++) cin >> A[i];

        cout << count_valid_permutations(n, A) << "\n";
    }

    return 0;
}
