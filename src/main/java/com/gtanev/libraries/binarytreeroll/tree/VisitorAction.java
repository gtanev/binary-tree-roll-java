package com.gtanev.libraries.binarytreeroll.tree;

import java.util.function.Consumer;

public interface VisitorAction<T> extends Consumer<BinaryTree.Node<T>> {

  @Override
  default VisitorAction<T> andThen(Consumer<? super BinaryTree.Node<T>> after) {
    return Consumer.super.andThen(after)::accept;
  }
}
