package com.pekings.pos.storage;

@FunctionalInterface
public interface ThrowingConsumer<T> {

    void accept(T t) throws Exception;

}
