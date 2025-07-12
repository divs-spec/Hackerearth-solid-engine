#include<bits/stdc++.h>
using namespace std;
typedef long long ll;

int main() {
    ll n;
    cin >> n;
    vector<ll> a(n + 1);
    for (int i = 1; i <= n; i++)
        cin >> a[i];

    vector<ll> prev(n + 1), next(n + 1);
    stack<ll> s;

    // Find previous greater element indices
    for (int i = 1; i <= n; i++) {
        while (!s.empty() && a[s.top()] <= a[i])
            s.pop();
        prev[i] = s.empty() ? -1 : s.top();
        s.push(i);
    }

    // Clear the stack
    while (!s.empty()) s.pop();

    // Find next greater element indices
    for (int i = n; i >= 1; i--) {
        while (!s.empty() && a[s.top()] <= a[i])
            s.pop();
        next[i] = s.empty() ? -1 : s.top();
        s.push(i);
    }

    // Output the sum of indices
    for (int i = 1; i <= n; i++) {
        cout << prev[i] + next[i] << " ";
    }

    cout << endl;
    return 0;
}
