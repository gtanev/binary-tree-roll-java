package com.gtanev.libraries.binarytreeroll;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.gtanev.libraries.binarytreeroll.roll.RollDirection;
import com.gtanev.libraries.binarytreeroll.roll.RollStrategy;
import com.gtanev.libraries.binarytreeroll.roll.RollStrategyFactory;
import com.gtanev.libraries.binarytreeroll.tree.BinaryTree;
import com.gtanev.libraries.binarytreeroll.tree.BinaryTree.Node;
import com.gtanev.libraries.binarytreeroll.tree.BinaryTreePrinter;
import com.gtanev.libraries.binarytreeroll.tree.InorderVisitor;
import com.gtanev.libraries.binarytreeroll.tree.NodeCollectorVisitorAction;
import com.gtanev.libraries.binarytreeroll.tree.PostorderVisitor;
import com.gtanev.libraries.binarytreeroll.tree.PreorderVisitor;
import com.gtanev.libraries.binarytreeroll.tree.VisitorAction;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UnitTests {

  @Test
  void testConstructors() {
    BinaryTree<Integer> tree1 = new BinaryTree<>();
    BinaryTree<Integer> tree2 = new BinaryTree<>(new Node<>(1));

    assertNull(tree1.getRoot());
    assertNotNull(tree2.getRoot());
    assertEquals(1, tree2.getRoot().getValue());
  }

  @Test
  void testGettersAndSetters() {
    BinaryTree<Integer> tree = new BinaryTree<>();
    tree.setRoot(new BinaryTree.Node<>(1));
    assertEquals(1, tree.getRoot().getValue());
  }

  @Test
  void testOf() {
    BinaryTree<Integer> tree = BinaryTree.of(1, 2, 3, null, null, 4, 5, null, 6);

    assertNotNull(tree.getRoot());
    assertEquals(1, tree.getRoot().getValue());
    assertEquals(2, tree.getRoot().getLeft().getValue());
    assertEquals(3, tree.getRoot().getRight().getValue());
    assertEquals(4, tree.getRoot().getRight().getLeft().getValue());
    assertEquals(5, tree.getRoot().getRight().getRight().getValue());
    assertEquals(6, tree.getRoot().getRight().getLeft().getRight().getValue());
    assertEquals(6, tree.size());
  }

  @Test
  void testOfOnEmptyTree() {
    BinaryTree<Integer> tree1 = BinaryTree.of();
    BinaryTree<Float> tree2 = BinaryTree.of(null);
    BinaryTree<String> tree3 = BinaryTree.of(new String[]{});
    BinaryTree<Character> tree4 = BinaryTree.of(new Character[]{'#'}, '#');

    assertNull(tree1.getRoot());
    assertNull(tree2.getRoot());
    assertNull(tree3.getRoot());
    assertNull(tree4.getRoot());
    assertEquals(0, tree1.size());
    assertEquals(0, tree2.size());
    assertEquals(0, tree3.size());
    assertEquals(0, tree4.size());
  }

  @Test
  void testSize() {
    BinaryTree<Object> treeOfSize0 = new BinaryTree<>();
    BinaryTree<String> treeOfSize1 = new BinaryTree<>(new Node<>("string"));
    BinaryTree<Integer> treeOfSize5 = BinaryTree.of(1, 2, 3, null, null, 4, 5);
    BinaryTree<Character> treeOfSize6 = BinaryTree.of('a', 'b', null, 'c', 'd', 'e', 'f', null);

    assertEquals(0, treeOfSize0.size());
    assertEquals(1, treeOfSize1.size());
    assertEquals(5, treeOfSize5.size());
    assertEquals(6, treeOfSize6.size());
  }

  @Test
  void testHeight() {
    BinaryTree<Object> treeOfHeight0 = new BinaryTree<>();
    BinaryTree<String> treeOfHeight1 = new BinaryTree<>(new Node<>("string"));
    BinaryTree<Integer> treeOfHeight3 = BinaryTree.of(1, 2, 3, null, null, 4, 5);
    BinaryTree<Character> treeOfHeight7 =
        BinaryTree.of('a', null, 'b', null, 'c', null, 'd', null, 'e', null, 'f', null, 'g');

    assertEquals(0, treeOfHeight0.height());
    assertEquals(1, treeOfHeight1.height());
    assertEquals(3, treeOfHeight3.height());
    assertEquals(7, treeOfHeight7.height());
  }

  @Test
  void testDeepCopy() {
    BinaryTree<Integer> tree = BinaryTree.of(1, 2, 3, null, null, 4, 5, null, 6);
    BinaryTree<Integer> copy = tree.deepCopy();

    assertEquals(tree, copy);
    assertEquals(tree.getRoot(), copy.getRoot());

    assertNotSame(tree, copy);
    assertNotSame(tree.getRoot(), copy.getRoot());

    assertEquals(tree.size(), copy.size());
    assertEquals(tree.getRoot().getValue(), copy.getRoot().getValue());
    assertEquals(tree.getRoot().getLeft().getValue(), copy.getRoot().getLeft().getValue());
    assertEquals(tree.getRoot().getRight().getValue(), copy.getRoot().getRight().getValue());
    assertEquals(tree.getRoot().getRight().getLeft().getValue(),
        copy.getRoot().getRight().getLeft().getValue());
    assertEquals(tree.getRoot().getRight().getRight().getValue(),
        copy.getRoot().getRight().getRight().getValue());
    assertEquals(tree.getRoot().getRight().getLeft().getRight().getValue(),
        copy.getRoot().getRight().getLeft().getRight().getValue());

    assertNull(copy.getRoot().getLeft().getLeft());
    assertNull(copy.getRoot().getLeft().getRight());
    assertNull(copy.getRoot().getRight().getLeft().getLeft());
  }

  @Test
  void testTraverse() {
    var tree = BinaryTree.of(1, 2, 3, null, null, 4, 5, null, 6);

    var preorderCollector = new NodeCollectorVisitorAction<Integer>();
    var inorderCollector = new NodeCollectorVisitorAction<Integer>();
    var postorderCollector = new NodeCollectorVisitorAction<Integer>();

    tree.traverse(new PreorderVisitor<>(preorderCollector));
    tree.traverse(new InorderVisitor<>(inorderCollector));
    tree.traverse(new PostorderVisitor<>(postorderCollector));

    assertEquals(List.of(1, 2, 3, 4, 6, 5), preorderCollector.getList());
    assertEquals(List.of(2, 1, 4, 6, 3, 5), inorderCollector.getList());
    assertEquals(List.of(2, 6, 4, 5, 3, 1), postorderCollector.getList());
  }

  @Test
  void testTraverseWithLambdas() {
    var tree = BinaryTree.of(1, 2, 3, null, null, 4, 5, null, 6);

    var preorderList = new ArrayList<Integer>();
    var inorderList = new ArrayList<Integer>();
    var postorderList = new ArrayList<Integer>();

    tree.traverse(new PreorderVisitor<>(node -> preorderList.add(node.getValue())));
    tree.traverse(new InorderVisitor<>(node -> inorderList.add(node.getValue())));
    tree.traverse(new PostorderVisitor<>(node -> postorderList.add(node.getValue())));

    assertEquals(List.of(1, 2, 3, 4, 6, 5), preorderList);
    assertEquals(List.of(2, 1, 4, 6, 3, 5), inorderList);
    assertEquals(List.of(2, 6, 4, 5, 3, 1), postorderList);
  }

  @Test
  void testTraverseWithComposedConsumers() {
    var tree = BinaryTree.of(1, 2, 3, null, null, 4, 5, null, 6);

    var preorderList = new ArrayList<Integer>();
    var inorderList = new ArrayList<Integer>();
    var postorderList = new ArrayList<Integer>();

    VisitorAction<Integer> addToPreorderList = node -> preorderList.add(node.getValue());
    VisitorAction<Integer> addToInorderList = node -> inorderList.add(node.getValue());
    VisitorAction<Integer> addToPostorderList = node -> postorderList.add(node.getValue());

    tree.traverse(new PreorderVisitor<>(addToPreorderList
        .andThen(node -> System.out.println("Adding " + node.getValue() + " to preorder list"))));
    tree.traverse(new InorderVisitor<>(addToInorderList
        .andThen(node -> System.out.println("Adding " + node.getValue() + " to inorder list"))));
    tree.traverse(new PostorderVisitor<>(addToPostorderList
        .andThen(node -> System.out.println("Adding " + node.getValue() + " to postorder list"))));

    assertEquals(List.of(1, 2, 3, 4, 6, 5), preorderList);
    assertEquals(List.of(2, 1, 4, 6, 3, 5), inorderList);
    assertEquals(List.of(2, 6, 4, 5, 3, 1), postorderList);
  }

  @Test
  void testTraverseOnEmptyTree() {
    var tree = new BinaryTree<Integer>();

    var preorderCollector = new NodeCollectorVisitorAction<Integer>();
    var inorderCollector = new NodeCollectorVisitorAction<Integer>();
    var postorderCollector = new NodeCollectorVisitorAction<Integer>();

    assertThrows(IllegalStateException.class,
        () -> tree.traverse(new PreorderVisitor<>(preorderCollector)));

    assertThrows(IllegalStateException.class,
        () -> tree.traverse(new InorderVisitor<>(inorderCollector)));

    assertThrows(IllegalStateException.class,
        () -> tree.traverse(new PostorderVisitor<>(postorderCollector)));

    assertEquals(Collections.emptyList(), preorderCollector.getList());
    assertEquals(Collections.emptyList(), inorderCollector.getList());
    assertEquals(Collections.emptyList(), postorderCollector.getList());
  }

  @Test
  void testEqualsAndHashCode() {
    var tree1 = BinaryTree.of(1, 2, 3, null, null, 4, 5, null, 6);
    var tree2 = BinaryTree.of(1, 2, 3, null, null, 4, 5, null, 6);

    assertTrue(tree1.equals(tree2) && tree2.equals(tree1));
    assertEquals(tree1.hashCode(), tree2.hashCode());
  }

  @Nested
  class NodeTest {

    @Test
    void testGettersAndSetters() {
      BinaryTree.Node<Integer> node = new BinaryTree.Node<>();

      node.setValue(1);
      node.setLeft(new BinaryTree.Node<>(2));
      node.setRight(new BinaryTree.Node<>(3));

      assertEquals(1, node.getValue());
      assertEquals(2, node.getLeft().getValue());
      assertEquals(3, node.getRight().getValue());
    }

    @Test
    void testConstructors() {
      BinaryTree.Node<Integer> node1 = new BinaryTree.Node<>();
      BinaryTree.Node<Integer> node2 = new BinaryTree.Node<>(Integer.MAX_VALUE);

      assertNull(node1.getValue());
      assertEquals(Integer.MAX_VALUE, node2.getValue());
    }

    @Test
    void testCopy() {
      BinaryTree.Node<Integer> node = new BinaryTree.Node<>(1);
      node.setLeft(new BinaryTree.Node<>(2));
      node.setRight(new BinaryTree.Node<>(3));

      BinaryTree.Node<Integer> copy = node.deepCopy();

      assertNotSame(node, copy);
      assertEquals(node, copy);
      assertEquals(node.getValue(), copy.getValue());
      assertEquals(node.getLeft().getValue(), copy.getLeft().getValue());
      assertEquals(node.getRight().getValue(), copy.getRight().getValue());
    }

    @Test
    void testCopyWithNestedChildren() {
      BinaryTree.Node<Integer> node = new BinaryTree.Node<>(1);

      node.setLeft(new BinaryTree.Node<>(2));
      node.getLeft().setLeft(new BinaryTree.Node<>(4));
      node.getLeft().setRight(new BinaryTree.Node<>(5));
      node.setRight(new BinaryTree.Node<>(3));
      node.getRight().setLeft(new BinaryTree.Node<>(6));
      node.getRight().setRight(new BinaryTree.Node<>(7));

      BinaryTree.Node<Integer> copy = node.deepCopy();

      assertNotSame(node, copy);
      assertEquals(node, copy);
      assertEquals(1, copy.getValue());
      assertEquals(2, copy.getLeft().getValue());
      assertEquals(3, copy.getRight().getValue());
      assertEquals(4, copy.getLeft().getLeft().getValue());
      assertEquals(5, copy.getLeft().getRight().getValue());
      assertEquals(6, copy.getRight().getLeft().getValue());
      assertEquals(7, copy.getRight().getRight().getValue());
    }

    @Test
    void testEqualsAndHashCode() {
      var node1 = new BinaryTree.Node<>(1);
      var node2 = new BinaryTree.Node<>(1);

      assertTrue(node1.equals(node2) && node2.equals(node1));
      assertEquals(node1.hashCode(), node2.hashCode());
    }
  }

  @Nested
  class PrinterTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final BinaryTreePrinter<Integer> printer =
        new BinaryTreePrinter<>(new PrintStream(outputStream));

    @AfterEach
    void tearDown() {
      outputStream.reset();
    }

    @Test
    void testPrint() {
      printer.print("test");
      assertEquals("test", outputStream.toString());
    }

    @Test
    void testPrintln() {
      printer.println("test");
      assertEquals("test".concat(System.lineSeparator()), outputStream.toString());
    }

    @Test
    void testCloseStream() {
      printer.closeStream();
      printer.print("test");
      assertTrue(outputStream.toString().isEmpty());
    }

    @Test
    void testPrintTree() {
      var tree = BinaryTree.of(1, 2, 3, null, null, 4, 5, null, 6);

      printer.printTree(tree);

      assertTrue(outputStream.toString().contains("1"));
      assertTrue(outputStream.toString().contains("2"));
      assertTrue(outputStream.toString().contains("3"));
      assertTrue(outputStream.toString().contains("4"));
      assertTrue(outputStream.toString().contains("5"));
      assertTrue(outputStream.toString().contains("6"));
    }

    @Test
    void testPrintPreorder() {
      var tree = BinaryTree.of(1, 2, 3, null, null, 4, 5, null, 6);

      printer.printPreorder(tree);
      var digits = outputStream.toString().replaceAll("[^0-9]", "");

      assertEquals("123465", digits);
    }


    @Test
    void testPrintInorder() {
      var tree = BinaryTree.of(1, 2, 3, null, null, 4, 5, null, 6);

      printer.printInorder(tree);
      var digits = outputStream.toString().replaceAll("[^0-9]", "");

      assertEquals("214635", digits);
    }

    @Test
    void testPrintPostorder() {
      var tree = BinaryTree.of(1, 2, 3, null, null, 4, 5, null, 6);

      printer.printPostorder(tree);
      var digits = outputStream.toString().replaceAll("[^0-9]", "");

      assertEquals("264531", digits);
    }
  }

  @Nested
  class RollTest {

    @Test
    void testRollStrategyClockwise() {
      var clockwiseStrategy = RollStrategyFactory.create(RollDirection.CLOCKWISE);

      assertNotNull(clockwiseStrategy);
      assertTrue(RollStrategy.class.isAssignableFrom(clockwiseStrategy.getClass()));
    }

    @Test
    void testRollStrategyCounterclockwise() {
      var counterclockwiseStrategy = RollStrategyFactory.create(RollDirection.COUNTERCLOCKWISE);

      assertNotNull(counterclockwiseStrategy);
      assertTrue(RollStrategy.class.isAssignableFrom(counterclockwiseStrategy.getClass()));
    }

    @Test
    void testRollStrategyFactoryNPE() {
      assertThrows(NullPointerException.class, () -> RollStrategyFactory.create(null));
    }
  }
}
