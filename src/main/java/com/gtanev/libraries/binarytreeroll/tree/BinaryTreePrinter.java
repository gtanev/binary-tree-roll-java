package com.gtanev.libraries.binarytreeroll.tree;

import java.io.PrintStream;

/**
 * A printer for binary trees.
 *
 * @param <T> the type of values held by the tree
 */
public class BinaryTreePrinter<T> {

  private final PrintStream printStream;

  public BinaryTreePrinter(PrintStream printStream) {
    this.printStream = printStream;
  }

  public void print(String s) {
    printStream.print(s);
  }

  public void println(String s) {
    printStream.println(s);
  }

  public void closeStream() {
    printStream.close();
  }

  public void printPreorder(BinaryTree<T> tree) {
    tree.traverse(new PreorderVisitor<>(this::printNodeValue));
    printStream.append(' ').println("Preorder");
  }

  public void printInorder(BinaryTree<T> tree) {
    tree.traverse(new InorderVisitor<>(this::printNodeValue));
    printStream.append(' ').println("Inorder");
  }

  public void printPostorder(BinaryTree<T> tree) {
    tree.traverse(new PostorderVisitor<>(this::printNodeValue));
    printStream.append(' ').println("Postorder");
  }

  public void printTree(BinaryTree<T> tree) {
    var root = tree.getRoot();

    if (root == null) {
      printStream.println("NULL");
    }

    printStream.println(printTree(root, 1, ""));
  }

  private void printNodeValue(BinaryTree.Node<T> node) {
    printStream.print(node.getValue() + " ");
  }

  private String printTree(BinaryTree.Node<T> root, int direction, String prefix) {
    if (root == null) {
      return "";
    }

    var edgeLength = 4;
    var edgeShapes = " ┘┐┤";
    var edgeType = (root.getRight() != null ? 1 : 0) + (root.getLeft() != null ? 2 : 0);

    var postfix = switch (edgeType) {
      case 1, 2, 3 -> " " + "—".repeat(edgeLength) + edgeShapes.charAt(edgeType) + "\n";
      default -> "\n";
    };

    var padding = " ".repeat(edgeLength + root.getValue().toString().length());

    var printRight = printTree(root.getRight(), 2, prefix + "│  ".charAt(direction) + padding);
    var printLeft = printTree(root.getLeft(), 0, prefix + "  │".charAt(direction) + padding);

    return printRight + prefix + root.getValue() + postfix + printLeft;
  }
}
