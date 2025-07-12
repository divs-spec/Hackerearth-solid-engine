#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include <limits.h>
#define z 1000000007
int main()
{
	long long int ans[20][200],i,j,k;
    memset(ans,0,20*200*sizeof(long long int));
    ans[0][0]=1;
    for(i=1;i<20;i++)
     {
	   for(j=0;j<200;j++)
	     {
     	   for(k=0;k<10&&j-k>=0;k++)
		     {
			    ans[i][j]+=ans[i-1][j-k];
			    ans[i][j]%=z;
		     }
	      }
	    ans[i][j]%=z;
     }
    int q;
	scanf("%d",&q);
	while(q--)
	{
		long long int s,cnt=0,sum=0;
		scanf("%lld",&s);
        for(i=1;i<=s;i=i*10+1)
		{
			cnt++;
			if(s%i==0&&s/i<200)
			  sum+=ans[cnt][s/i];
			sum%=z;
        }
        printf("%lld\n",sum);
	}
}
