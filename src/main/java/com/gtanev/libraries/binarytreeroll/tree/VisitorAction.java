package com.gtanev.libraries.binarytreeroll.tree;

import java.util.function.Consumer;

public interface VisitorAction<T> extends Consumer<BinaryTree.Node<T>> {}
