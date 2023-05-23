package com.gtanev.libraries.binarytreeroll.roll;

final class ClockwiseRollStrategy<T> extends DefaultRollStrategy<T> {

  @Override
  public RollHandler<T> getRollHandler() {
    return new ClockwiseRollHandler<>();
  }

  static final class Immutable<T> extends ImmutableRollStrategy<T> {

    @Override
    public RollHandler<T> getRollHandler() {
      return new ClockwiseRollHandler<>();
    }
  }
}
