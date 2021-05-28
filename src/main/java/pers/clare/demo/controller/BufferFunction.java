package pers.clare.demo.controller;

@FunctionalInterface
interface BufferFunction<V> {
    V apply(Long buffer, String id, String prefix, Integer length);
}
