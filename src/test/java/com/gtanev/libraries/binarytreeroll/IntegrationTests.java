package com.gtanev.libraries.binarytreeroll;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Named.named;

import com.gtanev.libraries.binarytreeroll.roll.RollDirection;
import com.gtanev.libraries.binarytreeroll.roll.RollStrategyFactory;
import com.gtanev.libraries.binarytreeroll.tree.BinaryTree;
import com.gtanev.libraries.binarytreeroll.tree.BinaryTreePrinter;
import com.gtanev.libraries.binarytreeroll.tree.InorderVisitor;
import com.gtanev.libraries.binarytreeroll.tree.NodeCollectorVisitorAction;
import com.gtanev.libraries.binarytreeroll.tree.PostorderVisitor;
import com.gtanev.libraries.binarytreeroll.tree.PreorderVisitor;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class IntegrationTests<T> {

  private NodeCollectorVisitorAction<T> preorderCollector1, inorderCollector1, postorderCollector1;
  private NodeCollectorVisitorAction<T> preorderCollector2, inorderCollector2, postorderCollector2;

  private final BinaryTreePrinter<T> printer = new BinaryTreePrinter<>(System.out);

  @BeforeEach
  void setUp() {
    preorderCollector1 = new NodeCollectorVisitorAction<>();
    preorderCollector2 = new NodeCollectorVisitorAction<>();
    inorderCollector1 = new NodeCollectorVisitorAction<>();
    inorderCollector2 = new NodeCollectorVisitorAction<>();
    postorderCollector1 = new NodeCollectorVisitorAction<>();
    postorderCollector2 = new NodeCollectorVisitorAction<>();
  }

  @ParameterizedTest
  @MethodSource("sampleTrees")
  void testClockwiseRoll(BinaryTree<T> tree) {
    printer.printTree(tree);

    tree.traverse(new InorderVisitor<>(inorderCollector1));
    tree.traverse(new PostorderVisitor<>(postorderCollector1));

    tree.roll(RollStrategyFactory.create(RollDirection.CLOCKWISE));
    printer.printTree(tree);

    tree.traverse(new PreorderVisitor<>(preorderCollector2));
    tree.traverse(new InorderVisitor<>(inorderCollector2));

    assertAll(
        () -> assertEquals(postorderCollector1.getList(), inorderCollector2.getList()),
        () -> assertEquals(inorderCollector1.getList(), preorderCollector2.getList())
    );
  }

  @ParameterizedTest
  @MethodSource("sampleTrees")
  void testCounterClockwiseRoll(BinaryTree<T> tree) {
    printer.printTree(tree);

    tree.traverse(new PreorderVisitor<>(preorderCollector1));
    tree.traverse(new InorderVisitor<>(inorderCollector1));

    tree.roll(RollStrategyFactory.create(RollDirection.COUNTERCLOCKWISE));
    printer.printTree(tree);

    tree.traverse(new InorderVisitor<>(inorderCollector2));
    tree.traverse(new PostorderVisitor<>(postorderCollector2));

    assertAll(
        () -> assertEquals(preorderCollector1.getList(), inorderCollector2.getList()),
        () -> assertEquals(inorderCollector1.getList(), postorderCollector2.getList())
    );
  }

  @ParameterizedTest
  @MethodSource("sampleTrees")
  void testImmutableClockwiseRoll(BinaryTree<T> tree) {
    printer.printTree(tree);

    tree.traverse(new InorderVisitor<>(inorderCollector1));
    tree.traverse(new PostorderVisitor<>(postorderCollector1));

    final var rolledTree = tree.roll(RollStrategyFactory.createImmutable(RollDirection.CLOCKWISE));
    printer.printTree(rolledTree);

    rolledTree.traverse(new PreorderVisitor<>(preorderCollector2));
    rolledTree.traverse(new InorderVisitor<>(inorderCollector2));

    final var inorderCollector3 = new NodeCollectorVisitorAction<T>();
    final var postorderCollector3 = new NodeCollectorVisitorAction<T>();

    tree.traverse(new InorderVisitor<>(inorderCollector3));
    tree.traverse(new PostorderVisitor<>(postorderCollector3));

    assertAll(
        () -> assertNotSame(tree, rolledTree),
        () -> assertEquals(tree.size(), rolledTree.size()),
        () -> assertEquals(inorderCollector1.getList(), preorderCollector2.getList()),
        () -> assertEquals(postorderCollector1.getList(), inorderCollector2.getList()),
        () -> assertEquals(inorderCollector1.getList(), inorderCollector3.getList()),
        () -> assertEquals(postorderCollector1.getList(), postorderCollector3.getList())
    );
  }

  @ParameterizedTest
  @MethodSource("sampleTrees")
  void testImmutableCounterClockwiseRoll(BinaryTree<T> tree) {
    printer.printTree(tree);

    tree.traverse(new PreorderVisitor<>(preorderCollector1));
    tree.traverse(new InorderVisitor<>(inorderCollector1));

    final var rolledTree =
        tree.roll(RollStrategyFactory.createImmutable(RollDirection.COUNTERCLOCKWISE));

    printer.printTree(rolledTree);

    rolledTree.traverse(new InorderVisitor<>(inorderCollector2));
    rolledTree.traverse(new PostorderVisitor<>(postorderCollector2));

    final var preorderCollector3 = new NodeCollectorVisitorAction<T>();
    final var inorderCollector3 = new NodeCollectorVisitorAction<T>();

    tree.traverse(new PreorderVisitor<>(preorderCollector3));
    tree.traverse(new InorderVisitor<>(inorderCollector3));

    assertAll(
        () -> assertNotSame(tree, rolledTree),
        () -> assertEquals(tree.size(), rolledTree.size()),
        () -> assertEquals(preorderCollector1.getList(), inorderCollector2.getList()),
        () -> assertEquals(inorderCollector1.getList(), postorderCollector2.getList()),
        () -> assertEquals(preorderCollector1.getList(), preorderCollector3.getList()),
        () -> assertEquals(inorderCollector1.getList(), inorderCollector3.getList())
    );
  }

  static Stream<Named<BinaryTree<?>>> sampleTrees() {
    var trees = List.of(
        BinaryTree.of(1),
        BinaryTree.of(1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987),
        BinaryTree.of(1, null, 2, null, 3, null, 4, null, 5, null, 6, null, 7, null, 8, null, 9),
        BinaryTree.of(0, 1, null, 2, null, 3, null, 4, null, 5, null, 6, null, 7, null, 8, null, 9),
        BinaryTree.of(true, true, null, true, null, null, false, null, false),
        BinaryTree.of(1.6f, null, 3f, 6.4f, 12, 25.6f, 51, 102.4f, 204, null, null, null, 409.6f),
        BinaryTree.of(9.999d, 8d, null, 7.75d, 6, 5.5d, 4, 3.25d, 2, null, null, null, null, 1.1d),
        BinaryTree.of('A', 'B', 'C', 'D', 'E', null, 'F', 'G', null, 'H', 'I', 'J', 'K', 'L', 'M'),
        BinaryTree.of(new String[]{"ARRAY", "OF", "STRINGS", "ON", "A", "TREE"}, null),
        BinaryTree.of("E,D,H,B,#,F,J,A,C,#,G,I".split(","), "#")
    );

    var names = List.of(
        "singletonTree",
        "perfectFibonacciTree",
        "rightSkewedIntsTree",
        "leftSkewedIntsTree",
        "symmetricBooleanTree",
        "rightSubtreeFloatsTree",
        "leftSubtreeDoublesTree",
        "balancedCharTree",
        "completeStringTree",
        "customNullIdentifierTree"
    );

    return IntStream.range(0, trees.size()).mapToObj(i -> named(names.get(i), trees.get(i)));
  }
}
