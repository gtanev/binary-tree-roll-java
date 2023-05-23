package com.gtanev.libraries.binarytreeroll.roll;

abstract sealed class AbstractRollStrategy<T> implements RollStrategy<T>
    permits DefaultRollStrategy, ImmutableRollStrategy {

  abstract RollHandler<T> getRollHandler();
}
