#include <bits/stdc++.h>
using namespace std;

static const int MAXN = 100000;

int a[MAXN + 5];
int cnt[MAXN + 5], st[MAXN + 5], en[MAXN + 5];
int blockId[MAXN + 5];
int seg[4 * MAXN];

int buildN;

void build(int idx, int l, int r, vector<int> &freq) {
    if (l == r) {
        seg[idx] = freq[l];
        return;
    }
    int mid = (l + r) / 2;
    build(idx * 2, l, mid, freq);
    build(idx * 2 + 1, mid + 1, r, freq);
    seg[idx] = max(seg[idx * 2], seg[idx * 2 + 1]);
}

int query(int idx, int l, int r, int ql, int qr) {
    if (ql > r || qr < l) return 0;
    if (ql <= l && r <= qr) return seg[idx];
    int mid = (l + r) / 2;
    return max(query(idx * 2, l, mid, ql, qr),
               query(idx * 2 + 1, mid + 1, r, ql, qr));
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int t;
    cin >> t;

    while (t--) {
        int n, q;
        cin >> n >> q;

        for (int i = 1; i <= n; i++)
            cin >> a[i];

        // Build blocks
        int blockCnt = 0;
        for (int i = 1; i <= n; ) {
            int j = i;
            while (j <= n && a[j] == a[i]) j++;
            blockCnt++;
            for (int k = i; k < j; k++) {
                blockId[k] = blockCnt;
                cnt[k] = j - i;
                st[k] = i;
                en[k] = j - 1;
            }
            i = j;
        }

        vector<int> freq(blockCnt + 1);
        for (int i = 1; i <= n; i++)
            freq[blockId[i]] = cnt[i];

        buildN = blockCnt;
        build(1, 1, buildN, freq);

        while (q--) {
            int l, r;
            cin >> l >> r;

            if (a[l] == a[r]) {
                cout << r - l + 1 << "\n";
                continue;
            }

            int leftPart = en[l] - l + 1;
            int rightPart = r - st[r] + 1;

            int lb = blockId[l] + 1;
            int rb = blockId[r] - 1;

            int midMax = 0;
            if (lb <= rb)
                midMax = query(1, 1, buildN, lb, rb);

            cout << max({leftPart, rightPart, midMax}) << "\n";
        }
    }
    return 0;
}
