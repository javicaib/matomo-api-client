package com.javi.mac.generics;

public sealed interface Result<T, E> {

    record Success<T, E>(T value) implements Result<T, E> {
    }

    record Failure<T, E>(E error) implements Result<T, E> {
    }

    default boolean isSuccess() {
        return this instanceof Success;
    }

    default boolean isFailure() {
        return this instanceof Failure;
    }

    default E getFailureError() {
        return ((Failure<T, E>) this).error();
    }

    default T getSuccessValue() {
        return ((Success<T, E>) this).value();
    }
}
