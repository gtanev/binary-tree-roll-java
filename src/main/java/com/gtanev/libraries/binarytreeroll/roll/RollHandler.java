package com.gtanev.libraries.binarytreeroll.roll;

import com.gtanev.libraries.binarytreeroll.tree.BinaryTree;

abstract sealed class RollHandler<T> permits ClockwiseRollHandler, CounterClockwiseRollHandler {

  private BinaryTree.Node<T> rolledRoot;

  public BinaryTree.Node<T> getRolledRoot() {
    return rolledRoot;
  }

  public void setRolledRoot(BinaryTree.Node<T> rolledRoot) {
    this.rolledRoot = rolledRoot;
  }

  abstract void roll(BinaryTree.Node<T> root, BinaryTree.Node<T> parent);
}
