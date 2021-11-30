package com.kjm.test.demo;

import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kjm.test.demo.model.City;
import com.kjm.test.demo.model.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("info")
public class InfoController {

    // service 생성자 주입
    // 생성자 주입하려는 service에 service annotation이 꼭 있어야함
    private InfoService infoService;

    @Autowired
    public InfoController(InfoService infoService){
        this.infoService = infoService;
    }

    @GetMapping("/project")
    public Object projectInfo(){
        log.debug("/info start");
        Project project = infoService.getProjectInfo();        
        return project;        
    }

    // gson을 이용해 json 리턴
    @GetMapping("/custom")
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

    // PathVariable 예제
    /*
        ex : localhost:8000/info/cityListByCode/KOR/3000000
        ctCode = KOR
        population = 3000000
    */
    @GetMapping("cityListByCode/{countryCode}/{population}")
    public Object cityListByCode(@PathVariable("countryCode") String ctCode, @PathVariable("population") int population){
        log.debug("countryCode = {}, population = {}", ctCode, population);
        List<City> cityList = infoService.findCityByCodeAndPopulation(ctCode, population);
        return cityList;
    }

    // RequestParam 예제
    /*
        ex : http://localhost:8000/info/cityListByCode?countryCode=KOR
        countryCode = KOR
        population = 0
    */
    @GetMapping("cityListByCode")
    public Object cityListByCodeRequest(@RequestParam("countryCode") String ctCode, @RequestParam(value="population", required=false, defaultValue = "0") int population){
        log.debug("countryCode = {}, population = {}", ctCode, population);
        List<City> cityList = infoService.findCityByCodeAndPopulation(ctCode, population);
        return cityList;
    }
}
