package com.gtanev.libraries.binarytreeroll.tree;

import com.gtanev.libraries.binarytreeroll.tree.BinaryTree.Node;

public record InorderVisitor<T>(VisitorAction<T> action) implements Visitor<T> {

  @Override
  public void visit(Node<T> root) {
    if (root != null) {
      this.visit(root.getLeft());
      action.accept(root);
      this.visit(root.getRight());
    }
  }
}
