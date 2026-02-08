#include<bits/stdc++.h>
using namespace std;

const int MAX_N = 2e6 + 121;

struct my{
    int value;
    int summa;
    my(){
        value = 0;
        summa = 0;
    }
    my(int value,int summa){
        this->value = value;
        this->summa = summa;
    }
};

my t[MAX_N * 4];
int n, m;
int a[MAX_N];

int getCountDigit(int x){
    int result = 0;
    while(1){
        ++result;
        x /= 10;
        if(x == 0)break;
    }
    return result;
}

void build(int v,int tl,int tr){
    if(tl == tr){
        t[v] = my(a[tl], getCountDigit(a[tl]));
        return;
    }
    int tm = (tl + tr) >> 1,
        L = v << 1,
        R = L | 1;
    build(L, tl, tm);
    build(R, tm + 1, tr);
    t[v].summa = t[L].summa + t[R].summa;
}

void upd(int v,int tl,int tr,int pos,int val){
    if(tl > pos || pos > tr)return;
    if(tl == tr){
        a[tl] = val;
        t[v] = my(val, getCountDigit(val));
        return;
    }
    int tm = (tl + tr) >> 1,
        L = v << 1,
        R = L | 1;
    upd(L, tl, tm, pos, val);
    upd(R, tm + 1, tr, pos, val);
    t[v].summa = t[L].summa + t[R].summa;
}

int get(int v,int tl,int tr,int l,int r){
    if(tl > r || tr < l)return 0;
    if(tl >= l && tr <= r)return t[v].summa;
    int tm = (tl + tr) >> 1,
        L = v << 1,
        R = L | 1;
    return get(L, tl, tm, l, r) + get(R, tm + 1, tr, l, r);
}

char getDigit(int value,int pos){
    string s = "";
    while(1){
        s += char('0' + (value % 10));
        value /= 10;
        if(value == 0)break;
    }
    reverse(s.begin(), s.end());
    return s[pos - 1];
}

string get(int l,int r,int k){
    if(get(1, 1, n, l, r) < k)return "-1";
    int previous = get(1, 1, n, 1, l - 1);
    k += previous;
    int v = 1,
        tl = 1,
        tr = n;
    while(tl < tr){
        int tm = (tl + tr) >> 1,
            L = v << 1,
            R = L | 1;
        if(t[L].summa >= k){
            tr = tm;
            v = L;
        }else{
            k -= t[L].summa;
            tl = tm + 1;
            v = R;
        }
    }
    string answer = "";
    answer += getDigit(t[v].value, k);
    return answer;

}


int main(){

    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> n >> m;
    for(int i = 1; i <= n; ++i){
        cin >> a[i];
    }

    build(1, 1, n);

    while(m-->0){
        string s;
        cin >> s;
        if(s == "update"){
            int pos, val;
            cin >> pos >> val;
            upd(1, 1, n, pos, val);
        }else{
            int l, r, k;
            cin >> l >> r >> k;
            cout << get(l, r, k) << endl;
        }
    }


    return 0;
}
