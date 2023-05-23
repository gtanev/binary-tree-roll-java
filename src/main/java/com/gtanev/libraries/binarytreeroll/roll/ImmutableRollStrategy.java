package com.gtanev.libraries.binarytreeroll.roll;

import com.gtanev.libraries.binarytreeroll.tree.BinaryTree;

abstract sealed class ImmutableRollStrategy<T> extends AbstractRollStrategy<T>
    permits ClockwiseRollStrategy.Immutable, CounterClockwiseRollStrategy.Immutable {

  @Override
  public BinaryTree<T> roll(BinaryTree<T> tree) {
    var rollHandler = getRollHandler();
    rollHandler.setRolledRoot(null);
    var treeCopy = tree.deepCopy();
    rollHandler.roll(treeCopy.getRoot(), null);
    treeCopy.setRoot(rollHandler.getRolledRoot());
    return treeCopy;
  }
}
