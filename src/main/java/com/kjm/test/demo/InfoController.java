package com.kjm.test.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kjm.test.demo.model.City;
import com.kjm.test.demo.model.FileData;
import com.kjm.test.demo.model.Project;
import com.kjm.test.demo.storage.StorageService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("info")
public class InfoController {

    // service 생성자 주입
    // 생성자 주입하려는 service에 service annotation이 꼭 있어야함
    private InfoService infoService;
    private StorageService storageService;

    @Autowired
    public InfoController(InfoService infoService, StorageService storageService){
        this.infoService = infoService;
        this.storageService = storageService;
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

    // GetMapping - PathVariable
    // localhost:8000/info/cityAdd/TEST/TST/Seoul/100
    @GetMapping("cityAdd/{name}/{countryCode}/{district}/{population}")    
    public Object cityAdd(
        @PathVariable(value="name") String name,
        @PathVariable(value="countryCode") String ctCode,
        @PathVariable(value="district") String district,
        @PathVariable(value="population") int population
    ){
        log.debug("name = {}, ctCode = {}, district = {}, population = {}" ,name, ctCode, district, population);
        return "ok";
    }

    // GetMapping - RequestParam
    // localhost:8000/info/cityAdd?name=TEST&countryCode=TST&district=Seoul&population=100
    @GetMapping("cityAdd")
    public Object cityAdd2(
        @RequestParam(value="name", required = true) String name,
        @RequestParam(value="countryCode", required = true) String ctCode,
        @RequestParam(value="district", required = true) String district,
        @RequestParam(value="population", required=false, defaultValue = "0") int population        
    ){
        log.debug("name = {}, ctCode = {}, district = {}, population = {}" ,name, ctCode, district, population);
        return "ok";
    }

    // 파라미터로 받는 필드들이 City Class에 다 있으므로 간단히 쓸 수도 있음
    @GetMapping(value = "cityAddSimple")
    public Object cityAdd(City city){
        log.debug("city = {}", city.toString());
        return "ok";
    }

    // PostMapping
    @PostMapping(value="cityAdd")
    public ResponseEntity<City> cityAdd3(@RequestBody City city){
        log.debug("city = {}", city.toString());
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    /*
        리턴을 굳이 안보내고 싶다면
        <City> -> <Void>
        ResponseEntity<>(city, HttpStatus.OK) -> ResponseEntity<>(HttpStatus.OK) 
    */

    // Query Params PostMapping with try catch
    // entity 타입이 string 이여야함
    @PostMapping(value="cityAddParam")
    public ResponseEntity<String> cityAdd4(String name, String countryCode, String district, Integer population){
        try{
            log.debug(name);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);    
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    // insert
    /*
        countryCode에는 Foreign key가 걸려있으므로 이미 있는 code를 입력해야함
        ex)
        URL : localhost:8000/info/cityInsert
        BODY : 
        {
            "name" : "TEST",
            "countryCode" : "KOR",
            "district" : "Seoul",
            "population" : 100
        }
    */
    @PostMapping(value="cityInsert")
    public ResponseEntity<City> cityInsert(@RequestBody City city){
        try{
            log.debug("city = {}", city.toString());
            return new ResponseEntity<>(infoService.insert(city), HttpStatus.OK);
        }catch(Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // update
    /*
        id가 꼭 포함되어야함
        ex)
        URL : localhost:8000/info/cityUpdate
        BODY :
        {
            "id" : 4081,
            "name" : "TEST2",
            "countryCode" : "KOR",
            "district" : "LA",
            "population" : 500
        }
    */
    @PostMapping(value = "cityUpdate")
    public ResponseEntity<String> cityUpdate(@RequestBody City city){
        try{
            log.debug("city = {}", city.toString());
            Integer result = infoService.updateById(city);
            return new ResponseEntity<>(String.format("%d updated", result), HttpStatus.OK);
        }catch(Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // delete 
    @ResponseBody
    @PostMapping(value ="cityDelete")
    public ResponseEntity<String> cityDelete(@RequestParam(value="id") Integer id){
        try{
            log.debug("city id = {}", id);
            Integer result = infoService.deleteById(id);
            return new ResponseEntity<>(String.format("%d deleted", result), HttpStatus.OK);
        }catch(Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // file upload
    // class에 @RestController가 있으면 @ResponseBody 생략 가능
    // form data name은 multipartfile 매개변수 이름과 동일해야함
    @PostMapping(value="uploadFile")
    public ResponseEntity<String> uploadFile(MultipartFile file) throws IllegalStateException, IOException{
        if(!file.isEmpty()){
            log.debug("file org name = {}", file.getOriginalFilename());
            log.debug("file content type = {}", file.getContentType());
            file.transferTo(new File(file.getOriginalFilename()));
        }else{
            return new ResponseEntity<>("file is empty", HttpStatus.INTERNAL_SERVER_ERROR);    
        }

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    // file upload with service
    @PostMapping(value="upload")
    public ResponseEntity<String> upload(MultipartFile file) throws IllegalStateException, IOException, NullPointerException{
        if(!file.isEmpty()){
            log.debug("file org name = {}", file.getOriginalFilename());
            log.debug("file content type = {}", file.getContentType());
            storageService.store(file);   
        }else{
            return new ResponseEntity<>("file is empty", HttpStatus.INTERNAL_SERVER_ERROR);    
        }        
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    // file download
    @GetMapping(value="download")
    public ResponseEntity<Resource> serveFile(@RequestParam(value="filename") String filename){
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    // file all delete
    @PostMapping(value = "deleteAll")
    public ResponseEntity<String> deleteAll(){
        storageService.deleteAll();
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    // file delete
    @PostMapping(value ="delete")
    public ResponseEntity<String> delete(@RequestParam(value="filename") String filename){
        storageService.delete(filename);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    // file list
    @GetMapping("fileList")
    public ResponseEntity<List<FileData>> getListFiles(){
        List<FileData> fileInfos = storageService.loadAll()
        .map(path -> {
            FileData data = new FileData();
            String filename = path.getFileName().toString();
            data.setFilename(filename);
            data.setUrl(MvcUriComponentsBuilder.fromMethodName(InfoController.class, "serveFile", filename).build().toString());
            try {
                data.setSize(Files.size(path));
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            return data;
        })
        .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }
}
