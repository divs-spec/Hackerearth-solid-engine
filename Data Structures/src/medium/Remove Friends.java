import java.util.Scanner;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class Main {

    public static void printList(Node head) {
        while (head != null) {
            System.out.print(head.data + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static Node reverseList(Node head) {
        if (head == null || head.next == null) return head;

        Node nxt = head.next;
        Node newList = reverseList(nxt);
        nxt.next = head;
        head.next = null;
        return newList;
    }

    public static Node deleteFrands(Node head, int k) {
        if (k == 0 || head == null) return head;

        boolean deletedFrand = false;
        Node secondLast = null, temp = head, prev = null, ret = head;
        int n = 0;

        while (temp != null) {
            if (prev != null && temp.data > prev.data && k > 0) {
                deletedFrand = true;
                k--;

                if (prev == ret) {
                    ret = temp;
                    prev = null;
                } else {
                    prev.next = null;
                    prev = secondLast;
                    if (secondLast != null) secondLast = secondLast.next;
                }
            } else {
                Node t = temp.next;
                temp.next = prev;
                secondLast = prev;
                prev = temp;
                temp = t;
                n++;
            }
        }

        head = reverseList(prev);

        if (!deletedFrand) {
            temp = head;
            n -= k;

            if (n == 0) return null;

            while (--n > 0 && temp != null) {
                temp = temp.next;
            }

            if (temp != null && temp.next != null) {
                temp.next = null;
            }
        }

        return head;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            int n = sc.nextInt();
            int k = sc.nextInt();

            Node head = null, tail = null;

            for (int i = 0; i < n; i++) {
                int data = sc.nextInt();
                Node newNode = new Node(data);

                if (head == null) {
                    head = newNode;
                    tail = newNode;
                } else {
                    tail.next = newNode;
                    tail = newNode;
                }
            }

            head = deleteFrands(head, k);
            printList(head);
        }

        sc.close();
    }
}
