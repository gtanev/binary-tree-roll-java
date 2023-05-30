package com.gtanev.libraries.binarytreeroll.tree;

import com.gtanev.libraries.binarytreeroll.tree.BinaryTree.Node;

public record PostorderVisitor<T>(VisitorAction<T> action) implements Visitor<T> {

  @Override
  public void visit(Node<T> root) {
    if (root != null) {
      this.visit(root.getLeft());
      this.visit(root.getRight());
      action.accept(root);
    }
  }
}
