package com.gtanev.libraries.binarytreeroll;

import com.gtanev.libraries.binarytreeroll.tree.BinaryTree;
import com.gtanev.libraries.binarytreeroll.tree.BinaryTreePrinter;
import com.gtanev.libraries.binarytreeroll.tree.InorderVisitor;

import java.util.ArrayList;

public class Example2 {

  public static void main(String... args) {
    // Create a binary tree
    var tree = BinaryTree.of('T', 'E', 'X', 'A', 'R', null, null, null, 'C', 'H');
    // Print the tree to the standard output stream
    new BinaryTreePrinter<Character>(System.out).printTree(tree);
    // Initialize an empty list
    var nodeValues = new ArrayList<Character>();
    // Perform an inorder traversal of the tree and add the node values to the list
    tree.traverse(new InorderVisitor<>(node -> nodeValues.add(node.getValue())));
    // Check if the list is sorted to determine if the tree is a binary search tree
    boolean isBST = nodeValues.stream().sorted().toList().equals(nodeValues);
    // Print the result to the standard output stream
    System.out.println(isBST ? "This tree is a BST" : "This tree is not a BST");
  }
}
