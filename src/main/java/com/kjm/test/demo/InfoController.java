package com.kjm.test.demo;

import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kjm.test.demo.model.City;
import com.kjm.test.demo.model.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class InfoController {

    // service 생성자 주입
    // 생성자 주입하려는 service에 service annotation이 꼭 있어야함
    private InfoService infoService;

    @Autowired
    public InfoController(InfoService infoService){
        this.infoService = infoService;
    }

    @GetMapping("/info")
    public Object projectInfo(){
        log.debug("/info start");
        Project project = infoService.getProjectInfo();        
        return project;        
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

    // service의 list 호출
    @GetMapping("/cityList")
    public Object cityList(){
        log.debug("/citylist start");
        List<City> cityList = infoService.getCityList();
        return cityList;
    }
}
