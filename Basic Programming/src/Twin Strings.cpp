#include <bits/stdc++.h>
using namespace std;
 
long long twinString(string &s, int n)
{
 
    // Initializing integer arrays 'bit' and 'xors'.
    vector<int> bit, xors;
 
    // Converting character to number.
    for (int i = 0; i < n; i++)
    {
        int x = s[i] - 'a';
        bit.push_back((1LL << x));
    }
 
    // Initializing integer variable 'val' and 64bit integer variable 'ans'.
    int val = 0;
    long long ans = 0;
 
    // Storing xor at each position.
    for (int i = 0; i < n; i++)
    {
        val ^= bit[i];
        xors.push_back(val);
    }
 
    // Initializing a hashmap 'mp' with {key, value} as [int, int].
    unordered_map<int, int> mp;
 
    // Calculating occurrences of xor.
    mp[0]++;
    for (int i = 0; i < n; i++)
    {
        mp[xors[i]]++;
    }
 
    // Calculating 'ans'.
    for (auto i : mp)
    {
        ans += (i.second * (i.second - 1)) / 2;
    }
 
    // We are returning the answer here.
    return ans;
}
 
int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int t;
    cin >> t;
    while (t--)
    {
        int n;
        cin >> n;
        string s;
        cin >> s;
        cout << twinString(s, n) << "\n";
    }
    return 0;
}
