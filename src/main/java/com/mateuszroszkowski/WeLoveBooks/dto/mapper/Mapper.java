package com.mateuszroszkowski.WeLoveBooks.dto.mapper;

public interface Mapper<E, DTO> {
    DTO map(E e);
}
