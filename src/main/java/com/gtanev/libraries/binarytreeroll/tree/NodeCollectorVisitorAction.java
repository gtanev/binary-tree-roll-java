package com.gtanev.libraries.binarytreeroll.tree;

import java.util.ArrayList;
import java.util.List;

public class NodeCollectorVisitorAction<T> implements VisitorAction<T> {

  private final List<T> list;

  public NodeCollectorVisitorAction() {
    this.list = new ArrayList<>();
  }

  public List<T> getList() {
    return list;
  }

  @Override
  public void accept(BinaryTree.Node<T> node) {
    list.add(node.getValue());
  }
}
