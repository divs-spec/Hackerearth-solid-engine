import java.util.*;

class Node {
    char id;
    Node left, right;
    int usedLinks;

    Node() {
        this.left = this.right = null;
        this.usedLinks = 0;
    }

    Node(char id) {
        this.id = id;
        this.left = this.right = null;
        this.usedLinks = 0;
    }
}

public class TreeFromString {

    static Node buildTreeFromString(String str) {
        if (str.length() == 0) return null;

        Node treeRoot = new Node(str.charAt(0));
        Stack<Character> brackets = new Stack<>();
        Stack<Node> treeStack = new Stack<>();
        treeStack.push(treeRoot);

        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (Character.isLowerCase(ch)) {
                Node newNode = new Node(ch);
                Node root = treeStack.peek();

                if (root.usedLinks == 0) {
                    root.left = newNode;
                    root.usedLinks++;
                    treeStack.push(newNode);
                } else if (root.usedLinks == 1) {
                    root.right = newNode;
                    root.usedLinks++;
                    treeStack.push(newNode);
                }
            } else if (ch == '(') {
                brackets.push('(');
            } else if (ch == ')') {
                if (!brackets.isEmpty()) brackets.pop();
                if (!treeStack.isEmpty()) treeStack.pop();
            } else {
                Node root = treeStack.peek();
                if (root.usedLinks == 0) {
                    root.usedLinks++;
                } else if (root.usedLinks == 1) {
                    root.usedLinks++;
                }
            }
        }

        return treeRoot;
    }

    static void getRes(Node root, int C, int c, List<Character> res) {
        if (root == null) return;

        if (c == C) {
            res.add(root.id);
        }

        getRes(root.left, C, c - 1, res);
        getRes(root.right, C, c + 1, res);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();
        while (t-- > 0) {
            int C = sc.nextInt();
            String str = sc.next();
            Node root = buildTreeFromString(str);

            List<Character> res = new ArrayList<>();
            getRes(root, C, 0, res);

            if (res.isEmpty()) {
                System.out.println("Common Gandhijee!");
            } else {
                Collections.sort(res);
                for (char ch : res) {
                    System.out.print(ch);
                }
                System.out.println();
            }
        }

        sc.close();
    }
}
