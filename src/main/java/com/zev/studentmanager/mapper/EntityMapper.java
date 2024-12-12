package com.zev.studentmanager.mapper;

import org.mapstruct.*;

import java.util.List;

public interface EntityMapper <E, D>{

    D toDto(E e);

    E toEntity(D d);

    List<D> toDto(List<E> e);

    List<E> toEntity(List<D> d);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
    void partialUpdate(@MappingTarget E entity, D dto);
}
