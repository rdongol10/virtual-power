package com.rdongol.virtualpower.commons.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static <T> T mapObject(Object fromValue, Class<T> toValueType) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.convertValue(fromValue, toValueType);
    }

    public static <T> List<T> mapObjectList(List<Object> fromValues , Class<T> toValueType){
        return fromValues.stream().map(
                        fromValue -> Utils.mapObject(fromValue, toValueType))
                .collect(Collectors.toList());
    }
}
