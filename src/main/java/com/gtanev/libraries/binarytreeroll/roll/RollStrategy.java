package com.gtanev.libraries.binarytreeroll.roll;

import com.gtanev.libraries.binarytreeroll.tree.BinaryTree;

public sealed interface RollStrategy<T> permits AbstractRollStrategy {
  BinaryTree<T> roll(BinaryTree<T> tree);
}
