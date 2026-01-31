#include <stdio.h>
#include <stdlib.h>   // <-- FIX HERE

int bit[26][210021];
int pos[26][210021];

char str[210021], ch;
int fq[26];

void update(int ind1, int ind2, int val)
{
    while (ind2 < 210021)
    {
        bit[ind1][ind2] += val;
        ind2 += ind2 & (-ind2);
    }
}

int dis(int ind1, int ind2)
{
    int ret = 0;
    while (ind2 > 0)
    {
        ret += bit[ind1][ind2];
        ind2 -= ind2 & (-ind2);
    }
    return ret;
}

int solve(int a, int b)
{
    int prev, ans = 0, total = 0;
    do
    {
        prev = ans;
        ans = dis(a, b + ans);
        total += abs(ans - prev);   // abs now works
    } while (prev != ans);

    return total;
}

int main()
{
    int len;
    scanf("%s%n", str + 1, &len);

    for (int i = 1; i <= len; ++i)
    {
        int key = str[i] - 'a';
        pos[key][++fq[key]] = i;
    }

    int q, x;
    scanf("%d", &q);

    for (int i = 0; i < q; ++i)
    {
        scanf("%d %c", &x, &ch);
        int add = solve(ch - 'a', x);
        str[pos[ch - 'a'][x + add]] = -1;
        update(ch - 'a', x + add, 1);
    }

    for (int i = 1; i <= len; ++i)
        if (str[i] + 1)
            putchar(str[i]);

    return 0;
}
