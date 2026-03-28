#include <bits/stdc++.h>
#define MAX 100005

using namespace std;

vector <int> F, B;
int tree[MAX];

void validate(string s)
{
    for ( int i = 0; i < (int)s.size(); i++ ) {
        assert(s[i] >= 'a' && s[i] <= 'z');
    }
}

bool chk(string s, string t)
{
    int p = 0;
    for ( int i = 0; i < (int)s.size() && p < (int)t.size(); i++ ) {
        if ( s[i] == t[p] ) p++;
    }
    if ( p == (int)t.size() ) return true;
    return false;
}

void update(int idx)
{
    while ( idx <= MAX - 4) {
        tree[idx] += 1;
        idx += (idx & (-idx));
    }
}

int query(int idx)
{
    int ans = 0;
    while ( idx > 0 ) {
        ans += tree[idx];
        idx -= (idx & (-idx));
    }
    return ans;
}

int main()
{
    int tc, p, idx;
    long long ans;
    string s, t;
    
    cin >> tc;
    assert(tc >= 1 && tc <= 10);
    
    while ( tc-- ) {
        
        cin >> s >> t;
        assert((int)s.size() >= 1 && (int)s.size() <= 100000);
        assert((int)t.size() >= 1 && (int)t.size() <= (int)s.size());
        validate(s);
        validate(t);
        assert(chk(s, t));
        memset(tree, 0, sizeof(tree));
        F.clear(), B.clear();
        
        p = 0;
        for ( int i = 0; i < (int)s.size(); i++ ) {
            if ( p < (int)t.size() && s[i] == t[p] ) p++;
            F.push_back(p);
        }
        
        p = 0;
        
        for ( int i = (int)s.size() - 1; i >= 0; i-- ) {
            if ( p < (int)t.size() && s[i] == t[(int)t.size() - 1 - p] ) p++;
            B.push_back(p);
        }
        
        reverse(B.begin(), B.end());
        
        ans = 0;
        idx = (int)s.size() - 1;
        
        for ( int i = (int)s.size() - 1; i >= 0; i-- ) {
            while ( idx > i + 1 ) {
                if ( B[idx] ) update(B[idx]);
                idx--;
            }
            if ( F[i] == (int)t.size() ) {
                ans += (int)s.size() - i - 1;
            }
            else {
                ans += query((int)t.size()) - query((int)t.size() - F[i] - 1);
            }
        }
        
        for ( int i = 1; i < (int)s.size(); i++ ) {
            ans += (B[i] == (int)t.size());
        }
        
        cout << ans << endl;
    }
    
    
    return 0;
}
