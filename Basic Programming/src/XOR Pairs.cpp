#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        int N;
        cin >> N;
        unordered_map<int, long long> freq;
        freq.reserve(N*2);

        for (int i = 0; i < N; ++i) {
            int a;
            cin >> a;
            int key = a ^ (i + 1);  // i converted to 1-based
            freq[key]++;
        }

        long long ans = 0;
        for (auto& [k, cnt] : freq) {
            ans += cnt * (cnt - 1) / 2;
        }

        cout << ans << "\n";
    }

    return 0;
}
