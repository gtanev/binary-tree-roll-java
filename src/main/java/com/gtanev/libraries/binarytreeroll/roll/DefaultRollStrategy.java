package com.gtanev.libraries.binarytreeroll.roll;

import com.gtanev.libraries.binarytreeroll.tree.BinaryTree;

abstract sealed class DefaultRollStrategy<T> extends AbstractRollStrategy<T>
    permits ClockwiseRollStrategy, CounterClockwiseRollStrategy {

  @Override
  public BinaryTree<T> roll(BinaryTree<T> tree) {
    var rollHandler = getRollHandler();
    rollHandler.setRolledRoot(null);
    rollHandler.roll(tree.getRoot(), null);
    tree.setRoot(rollHandler.getRolledRoot());
    return tree;
  }
}
