#include<bits/stdc++.h>
using namespace std;
class Tree{
    public:
    int data;
    Tree *left,*right,*root=NULL;
    void insert(int data)
    {
        Tree *tmp = root,*second;
        if(tmp==NULL)
        {
            root = new Tree();
            root->data = data;
            root->left=root->right=NULL;
            return;
        }
       
        second = new Tree();
        second->data = data;
        while(tmp!=NULL)
        {
           
            if(tmp->data>=data)
            {
                if(tmp->left==NULL)
                {
                    tmp->left = second; 
                    break;
                }
                else
                    tmp = tmp->left;
            }
            else
            {
                if(tmp->right==NULL)
                {
                    tmp->right = second;
                    break;
                }
                else
                {
                    tmp = tmp->right;
                }
                
            }
        }

    }
    void find(int x,int y)
    {
        Tree *tmp = root;
        Tree *small,*large;
        int ans = 0,val;
        small = large = root;
        while(tmp!=NULL)
        {
            if(small==large)
                val = small->data;
            else
            {
                ans = max(ans,small->data);
                ans = max(ans,large->data);
            }
            
            if(small->data>x)
            {
                small = small->left;
            }
            else if(small->data<x)
            {
                small = small->right;
            }
            if(large->data>y)
            {
                large = large->left;
            }
            else if(large->data<y)
            {
                large = large->right;
            }
            if(small!=large)
            {
                ans = max(ans,val);
            }
            else
            {
                continue;
            }
            if(small->data==x&&large->data==y)
            {
                ans = max(ans,y);
                break;
            }
            
        }
        cout<<ans<<endl;
    }
};
int main()
{
    Tree t1;
    int n,i,x,y;
    cin>>n;
    int a[n];
    for(i=0;i<n;i++)
    {
        cin>>a[i];
        t1.insert(a[i]);
    }
    cin>>x>>y;
    int tmp;
    if(x>y)
    {
        tmp = x;
        x = y;
        y = tmp;
    }

    t1.find(x,y);
    return 0;

}
