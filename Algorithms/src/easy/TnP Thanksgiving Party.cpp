#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N;
    cin >> N;
    unordered_set<string> prefixes;

    for (int i = 0; i < N; i++) {
        string s;
        cin >> s;
        string prefix = "";
        for (char c : s) {
            prefix += c;
            prefixes.insert(prefix);
        }
    }

    int Q;
    cin >> Q;
    while (Q--) {
        int M, S;
        cin >> M >> S;
        int liked = 0;

        for (int i = 0; i < M; i++) {
            string pizza;
            cin >> pizza;
            if (prefixes.count(pizza)) {
                liked++;
            }
        }

        cout << (liked >= S ? "Yes" : "No") << '\n';
    }

    return 0;
}
