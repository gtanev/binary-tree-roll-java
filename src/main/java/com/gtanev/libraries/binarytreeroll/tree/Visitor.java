package com.gtanev.libraries.binarytreeroll.tree;

public interface Visitor<T> {
  void visit(BinaryTree.Node<T> root);
}
