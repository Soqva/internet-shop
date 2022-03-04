package com.s0qva.application.mapper;

public interface Mapper<F, T> {

    T map(F from);
}
