package com.gtanev.libraries.binarytreeroll.tree;

import com.gtanev.libraries.binarytreeroll.roll.RollStrategy;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * A binary tree.
 *
 * @param <T> the type of values held by the tree
 */
public class BinaryTree<T> implements Traversable<T>, Rollable<T> {

  private Node<T> root;

  public BinaryTree() {}

  public BinaryTree(Node<T> root) {
    this.root = root;
  }

  public Node<T> getRoot() {
    return root;
  }

  public void setRoot(Node<T> root) {
    this.root = root;
  }

  public long size() {
    return size(root);
  }

  private long size(Node<T> root) {
    return root == null ? 0 : 1 + size(root.getLeft()) + size(root.getRight());
  }

  public long height() {
    return height(root);
  }

  private long height(Node<T> root) {
    return root == null ? 0 : 1 + Math.max(height(root.getLeft()), height(root.getRight()));
  }

  /**
   * Creates a binary tree from a given varargs of values using a default null identifier.
   *
   * @param values the array of values
   *
   * @return the binary tree
   */
  @SafeVarargs
  public static <T> BinaryTree<T> of(T... values) {
    return of(values, null);
  }

  /**
   * Creates a binary tree from a given array of values.
   *
   * @param values         the array of values
   * @param nullIdentifier the value that represents a null node
   *
   * @return the binary tree
   */
  public static <T> BinaryTree<T> of(T[] values, T nullIdentifier) {
    if (values == null || values.length == 0 || values[0] == nullIdentifier) {
      return new BinaryTree<>();
    }

    Node<T> rootNode = new Node<>(values[0]);
    Queue<Node<T>> nodeQueue = new LinkedList<>();
    nodeQueue.offer(rootNode);

    int valPtr = 1;

    while (!nodeQueue.isEmpty()) {
      T leftVal = (valPtr < values.length) ? values[valPtr++] : null;
      T rightVal = (valPtr < values.length) ? values[valPtr++] : null;
      Node<T> node = nodeQueue.poll();

      if (leftVal != null && !leftVal.equals(nullIdentifier)) {
        Node<T> leftNode = new Node<>(leftVal);
        node.setLeft(leftNode);
        nodeQueue.offer(leftNode);
      }

      if (rightVal != null && !rightVal.equals(nullIdentifier)) {
        Node<T> rightNode = new Node<>(rightVal);
        node.setRight(rightNode);
        nodeQueue.offer(rightNode);
      }
    }

    return new BinaryTree<>(rootNode);
  }

  public BinaryTree<T> deepCopy() {
    return new BinaryTree<>(root != null ? root.deepCopy() : null);
  }

  @Override
  public void traverse(Visitor<T> visitor) {
    if (this.root == null) {
      throw new IllegalStateException("Cannot traverse an empty tree.");
    }

    this.root.traverse(visitor);
  }

  @Override
  public BinaryTree<T> roll(RollStrategy<T> strategy) {
    return strategy.roll(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BinaryTree<?> that)) return false;

    return Objects.equals(root, that.root);
  }

  @Override
  public int hashCode() {
    return root != null ? root.hashCode() : 0;
  }

  /**
   * A node in a binary tree.
   *
   * @param <T> the type of the value held by the node
   */
  public static class Node<T> implements Traversable<T> {

    private T value;
    private Node<T> left;
    private Node<T> right;

    public Node() {}

    public Node(T value) {
      this.value = value;
    }

    public T getValue() {
      return value;
    }

    public void setValue(T value) {
      this.value = value;
    }

    public Node<T> getLeft() {
      return left;
    }

    public void setLeft(Node<T> left) {
      this.left = left;
    }

    public Node<T> getRight() {
      return right;
    }

    public void setRight(Node<T> right) {
      this.right = right;
    }

    public void traverse(Visitor<T> visitor) {
      visitor.visit(this);
    }

    /**
     * Creates a deep copy of the current node and its children
     *
     * @return a deep copy of the current node and its children
     */
    public Node<T> deepCopy() {
      var copy = new Node<>(this.value);

      if (this.left != null) {
        copy.setLeft(this.left.deepCopy());
      }

      if (this.right != null) {
        copy.setRight(this.right.deepCopy());
      }

      return copy;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Node<?> node)) return false;

      if (!Objects.equals(value, node.value)) return false;
      if (!Objects.equals(left, node.left)) return false;
      return Objects.equals(right, node.right);
    }

    @Override
    public int hashCode() {
      int result = value != null ? value.hashCode() : 0;
      result = 31 * result + (left != null ? left.hashCode() : 0);
      result = 31 * result + (right != null ? right.hashCode() : 0);
      return result;
    }
  }
}
