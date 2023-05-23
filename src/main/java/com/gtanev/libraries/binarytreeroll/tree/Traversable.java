package com.gtanev.libraries.binarytreeroll.tree;

public interface Traversable<T> {
  void traverse(Visitor<T> visitor);
}
