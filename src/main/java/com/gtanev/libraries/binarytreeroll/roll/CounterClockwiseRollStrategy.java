package com.gtanev.libraries.binarytreeroll.roll;

final class CounterClockwiseRollStrategy<T> extends DefaultRollStrategy<T> {

  @Override
  public RollHandler<T> getRollHandler() {
    return new CounterClockwiseRollHandler<>();
  }

  static final class Immutable<T> extends ImmutableRollStrategy<T> {

    @Override
    public RollHandler<T> getRollHandler() {
      return new CounterClockwiseRollHandler<>();
    }
  }
}
