package com.kjm.test.mapping;

import java.util.List;

import com.kjm.test.mapping.model.UserInfo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {
    /*
        {"name": "bryan", "city": "Seoul", "age": 18, "cars": ["GV80", "X6", "GLE350"]}
        [
            {"name": "bryan", "city": "Seoul", "age": 18, "cars": ["GV80", "X6", "GLE350"]},
            {"name": "hello", "city": "Pusan", "age": 19, "cars": ["GV70", "X5", "GLE250"]},
            {"name": "tistory", "city": "Suwon", "age": 20, "cars": ["G80", "M5", "GLA200"]}
        ]
    */

    // json data
    @GetMapping("/model")
    public ResponseEntity<String> modelTest(@RequestBody UserInfo userInfo){
        log.debug("userInfo = {}", userInfo.toString());
        return ResponseEntity.ok(userInfo.toString());
    }

    // json array
    @GetMapping("/model2")
    public ResponseEntity<String> modelTest2(@RequestBody List<UserInfo> userInfo){
        log.debug("userInfo = {}", userInfo.toString());
        return ResponseEntity.ok(userInfo.toString());
    }
}
