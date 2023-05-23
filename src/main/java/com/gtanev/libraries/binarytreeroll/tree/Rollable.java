package com.gtanev.libraries.binarytreeroll.tree;

import com.gtanev.libraries.binarytreeroll.roll.RollStrategy;

public interface Rollable<T> {
  BinaryTree<T> roll(RollStrategy<T> rollStrategy);
}
