// 672115039 Metavee Aeinjang
// this code using for adt & problem solving only
package ADT;
import java.io.*;
import java.util.Scanner;

class Node {
    char data;
    Node next;

    public Node(char data) {
        this.data = data;
        this.next = null;
    }
}

class Stack {
    private Node top;

    public Stack() {
        top = null;
    }

    public void push(char data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
    }

    public char pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack Underflow");
        }
        char data = top.data;
        top = top.next;
        return data;
    }

    public char peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }
}

public class InfixToPostfix {

    private static int precedence(char op) {
        switch (op) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^':         return 3;
            default:          return -1;
        }
    }

    private static boolean isOperand(char c) {
        return Character.isLetterOrDigit(c);
    }

    public static String convertToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack stack = new Stack();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            // Ignore spaces
            if (c == ' ') {
                continue;
            }

            if (isOperand(c)) {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // Remove '('
                }
            } else { 
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    public static void main(String[] args) {
        String filePath;
        if (args.length >= 1) {
            filePath = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the file path: ");
            filePath = scanner.nextLine();
            scanner.close();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Remove extra spaces
                if (!line.isEmpty()) {
                    System.out.println("Infix: " + line);
                    String postfix = convertToPostfix(line);
                    System.out.println("Postfix: " + postfix);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
