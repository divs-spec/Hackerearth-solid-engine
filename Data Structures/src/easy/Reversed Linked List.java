import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.util.*;

class node{

int data;

node next;

node(int x)

{ this.data=x;

}

}

class TestClass {

public static void main(String args[] ) throws Exception {

Scanner sc=new Scanner(System.in);

int size=sc.nextInt();

node head=new node(sc.nextInt());

node curr=head;

for(int i=0;i<size-1;i++) {

curr.next=new node(sc.nextInt());

curr=curr.next; } reverse(head);

}

public static void reverse(node head) {

node curr=head;

node prev=null;

node next;

while(curr!=null) {

if(curr.data%2==0)

{

next=curr.next;

curr.next=prev;

prev=curr;

curr=next;

}

else {

traverse(prev);

prev=null;

System.out.print(curr.data+" ");

curr=curr.next;

}

}

traverse(prev);

}

public static void traverse(node head) {

while(head!=null) {

System.out.print(head.data+" ");

head=head.next;

}

}

}
