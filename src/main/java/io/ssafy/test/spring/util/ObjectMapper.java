package io.ssafy.test.spring.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectMapper {
    ModelMapper defaultModelMapper = new ModelMapper();

    public <S, T> List<T> mapList(Collection<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> defaultModelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public <S, T> T map(S source, Class<T> targetClass) {
        return defaultModelMapper.map(source, targetClass);
    }

    public <S, T> void map(S source, T target) {
        defaultModelMapper.map(source, target);
    }

}
