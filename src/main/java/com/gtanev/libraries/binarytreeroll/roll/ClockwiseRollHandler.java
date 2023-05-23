package com.gtanev.libraries.binarytreeroll.roll;

import com.gtanev.libraries.binarytreeroll.tree.BinaryTree;

final class ClockwiseRollHandler<T> extends RollHandler<T> {

  @Override
  public void roll(BinaryTree.Node<T> root, BinaryTree.Node<T> parent) {
    if (root != null) {
      if (root.getLeft() != null) {
        this.roll(root.getLeft(), parent);
        root.getLeft().setRight(root);
        root.setLeft(null);
      } else if (parent != null) {
        parent.setLeft(root);
        parent.setRight(null);
      } else {
        setRolledRoot(root);
      }
      if (root.getRight() != null) {
        this.roll(root.getRight(), root);
      }
    }
  }
}
