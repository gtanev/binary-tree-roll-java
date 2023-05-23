package com.gtanev.libraries.binarytreeroll.tree;

import com.gtanev.libraries.binarytreeroll.tree.BinaryTree.Node;
import java.util.function.Consumer;

public record PreorderVisitor<T>(Consumer<Node<T>> action) implements Visitor<T> {

  @Override
  public void visit(Node<T> root) {
    if (root != null) {
      action.accept(root);
      this.visit(root.getLeft());
      this.visit(root.getRight());
    }
  }
}
