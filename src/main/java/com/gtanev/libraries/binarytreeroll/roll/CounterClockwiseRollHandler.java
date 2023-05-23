package com.gtanev.libraries.binarytreeroll.roll;

import com.gtanev.libraries.binarytreeroll.tree.BinaryTree;

final class CounterClockwiseRollHandler<T> extends RollHandler<T> {

  @Override
  public void roll(BinaryTree.Node<T> root, BinaryTree.Node<T> parent) {
    if (root != null) {
      if (root.getRight() != null) {
        this.roll(root.getRight(), parent);
        root.getRight().setLeft(root);
        root.setRight(null);
      } else if (parent != null) {
        parent.setRight(root);
        parent.setLeft(null);
      } else {
        setRolledRoot(root);
      }
      if (root.getLeft() != null) {
        this.roll(root.getLeft(), root);
      }
    }
  }
}
