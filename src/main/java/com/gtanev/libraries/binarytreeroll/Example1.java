package com.gtanev.libraries.binarytreeroll;

import com.gtanev.libraries.binarytreeroll.roll.RollDirection;
import com.gtanev.libraries.binarytreeroll.roll.RollStrategyFactory;
import com.gtanev.libraries.binarytreeroll.tree.BinaryTree;
import com.gtanev.libraries.binarytreeroll.tree.BinaryTreePrinter;

public class Example1 {

  public static void main(String... args) {
    // Create a binary tree
    var tree = BinaryTree.of(1, 2, 3, null, null, 4, 5, null, null, 6);

    // Instantiate a printer with the standard output stream
    var printer = new BinaryTreePrinter<Integer>(System.out);

    // Print the tree and its traversals
    printer.println("Original tree:\n");
    printer.printTree(tree);
    printer.printPreorder(tree);
    printer.printInorder(tree);
    printer.printPostorder(tree);

    // Roll the tree counterclockwise
    tree.roll(RollStrategyFactory.create(RollDirection.COUNTERCLOCKWISE));

    // Print the rolled tree and its traversals
    printer.println("\nRolled tree:\n");
    printer.printTree(tree);
    printer.printPreorder(tree);
    printer.printInorder(tree);
    printer.printPostorder(tree);
  }
}
