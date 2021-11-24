package com.kjm.test.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @GetMapping("/info")
    public String projectInfo(){
        return "Test rest api with mysql";
    }
}
