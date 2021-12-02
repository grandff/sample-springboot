package com.kjm.test.convert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kjm.test.convert.model.TestHello;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "convert")
public class ConvertController {
    /*
        json data : [{"name":"hello","gender":"man"},{"name":"yes", "gender" : "no"},
{"name":"hello","gender":"woman"}]
    */

    // simple version
    // controller에서 json string을 ObjectMapper로 변환
    @PostMapping(value = "/test")
    public ResponseEntity<Void> test(@RequestPart("files") List<MultipartFile> files,
    @RequestParam("jsonList") String jsonList) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new SimpleModule());    // ??
        List<TestHello> testHelloList = objectMapper.readValue(jsonList, new TypeReference<List<TestHello>>() {}); 
        log.debug("files count = {}", files.size());
        log.debug("json count = {}", testHelloList.size());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // using converter
    @PostMapping(value = "/test2")
    public ResponseEntity<Void> test2(@RequestPart("files") List<MultipartFile> files, @RequestParam("jsonList") List<TestHello> testHelloList) throws JsonProcessingException{
        log.debug("files count = {}", files.size());
        log.debug("json count = {}", testHelloList.size());

        // foreach를 활용한 내용 출력
        testHelloList.stream().forEach(data -> {
            log.debug("name : {}, gender : {}", data.getName(), data.getGender());
        });

        // filter를 통해 이름이 hello 인 것만 가져오기
        Stream<TestHello> list2 = testHelloList.stream().filter(data -> data.getName().equals("hello"));
        list2.forEach(data -> {
            log.debug("hello is name : {}, gender : {}", data.getName(), data.getGender());
        });

        // 필터를 통한 대상 합 구하기 이름이 hello인 것들
        long count = testHelloList.stream().filter(data -> data.getName().equals("hello")).count();
        log.debug("hello name count : {}", count);

        // map으로 가공 
        List<TestHello> list3 = testHelloList.stream().map(data -> {
            data.setName("yas");
            data.setGender("man");
            return data;
        }).collect(Collectors.toList());
        list3.forEach(data -> {
            log.debug("new list name : {}, gender : {}", data.getName(), data.getGender());
        });

        // map을 숫자로 표현 mapToInt, sum 이것도 hashmap으로 하는것만 되나 ..?
        //int mapToInt = testHelloList.stream().mapToInt(String::length).sum();

        // list에 있는 HashMap을 단일 원소로 변환 (이건 제외)
        /*
            //flatmap을 통해 1개의 단일 원소로 반환
            Stream<Object> list4 = list.stream().flatMap(x-> x.keySet().parallelStream());
            list4.forEach(System.out::println);  //map이 1개의 list안에 들어간 모양으로 출력
        */

        // 중복제거 (distinct)
        Stream<TestHello> list4 = testHelloList.stream().distinct();        

        // 정렬 (sort)
        /*
            //일반정렬, 통상 map 객체가 아닌 문자, 숫자 대상으로 자주 사용.
            list.stream().sorted();
            list.forEach(System.out::println);
        */

        // Map 객체 정렬
        /*
            //Map객체에 대한 정렬
            list.sort(Comparator.comparing(
                m -> m.get("id").toString(), 
                Comparator.nullsLast(Comparator.naturalOrder()))
            );
            list.forEach(System.out::println);
        */
            
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
