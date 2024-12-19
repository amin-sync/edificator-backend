package com.hub.edificators.commons;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class GenericModelMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <S,D> List<D> mapCollection(List<S> sourceList, Class<D> destinationClass) {
        return sourceList.stream()
                .map(user -> modelMapper.map(user,destinationClass))
                .collect(Collectors.toList());
    }
    public static <S,D> D mapObject(S source, Class<D> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }

}
