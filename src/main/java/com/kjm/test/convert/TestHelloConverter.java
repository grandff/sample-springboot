package com.kjm.test.convert;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjm.test.convert.model.TestHello;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
// 소스를 대상으로 변경하기 위한 converter ...
public class TestHelloConverter implements Converter<String, List<TestHello>>{
    // objectmapper 생성자 주입
    private ObjectMapper objectMapper;
    public TestHelloConverter(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public List<TestHello> convert(String value){
        return objectMapper.readValue(value, new TypeReference<List<TestHello>>() {});
    }
}