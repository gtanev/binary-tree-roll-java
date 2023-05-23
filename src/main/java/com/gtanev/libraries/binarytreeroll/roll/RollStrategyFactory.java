package com.gtanev.libraries.binarytreeroll.roll;

public interface RollStrategyFactory {

  static <T> RollStrategy<T> create(RollDirection direction) {
    return switch (direction) {
      case CLOCKWISE -> new ClockwiseRollStrategy<>();
      case COUNTERCLOCKWISE -> new CounterClockwiseRollStrategy<>();
    };
  }

  static <T> RollStrategy<T> createImmutable(RollDirection direction) {
    return switch (direction) {
      case CLOCKWISE -> new ClockwiseRollStrategy.Immutable<>();
      case COUNTERCLOCKWISE -> new CounterClockwiseRollStrategy.Immutable<>();
    };
  }
}
