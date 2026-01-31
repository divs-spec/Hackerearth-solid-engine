#include <bits/stdc++.h>
using namespace std;

static const int MAXC = 1000000;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, k, q;
    cin >> n >> k >> q;

    vector<int> a(n);
    for (int i = 0; i < n; i++) cin >> a[i];

    vector<int> cnt(MAXC + 1, 0);
    vector<int> freq(n + 1, 0);

    // IMPORTANT FIX
    freq[0] = n;

    int L = 0, R = -1;
    int lastAns = 0;

    auto add = [&](int x) {
        int &c = cnt[x];
        freq[c]--;
        c++;
        freq[c]++;
    };

    auto remove = [&](int x) {
        int &c = cnt[x];
        freq[c]--;
        c--;
        freq[c]++;
    };

    while (q--) {
        int l, r;
        cin >> l >> r;

        l = (l + lastAns) % n;
        r = (r + lastAns) % n;
        if (l > r) swap(l, r);

        while (L > l) add(a[--L]);
        while (R < r) add(a[++R]);
        while (L < l) remove(a[L++]);
        while (R > r) remove(a[R--]);

        lastAns = (k <= n ? freq[k] : 0);
        cout << lastAns << '\n';
    }

    return 0;
}
