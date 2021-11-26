package com.kjm.test.demo;

import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kjm.test.demo.model.Project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class InfoController {
    @GetMapping("/info")
    public Object projectInfo(){
        log.debug("test! info is start");
        Project project = new Project();
        project.projectName = "kjm";
        project.author = "hello-kjm";
        project.createdDate = new Date();
        return project;
        // test code
        // return "Test rest api with mysql";
    }

    // gson을 이용해 json 리턴
    @GetMapping("/info2")
    public String customJson() {
        JsonObject jo = new JsonObject();

        jo.addProperty("projectName", "demo2");
        jo.addProperty("author", "fucking-kjm");
        jo.addProperty("createdDate", new Date().toString());

        JsonArray ja = new JsonArray();
        for(int i = 0; i< 5; i++){
            JsonObject jObj = new JsonObject();
            jObj.addProperty("prop" + i, i);        
            ja.add(jObj);
        }

        jo.add("follower", ja);

        return jo.toString();
    }
}
