package com.kjm.test.demo;

import java.util.Date;
import java.util.List;

import com.kjm.test.demo.model.City;
import com.kjm.test.demo.model.Project;
import com.kjm.test.demo.repository.CityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

    private final CityRepository cityRepository;

    @Autowired
    public InfoService(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }
    
    public Project getProjectInfo(){
        Project project = new Project();
        project.projectName = "kjm";
        project.author = "hello-kjm";
        project.createdDate = new Date();
        return project;
    }   

    public List<City> getCityList() {
        return this.cityRepository.findList();
    }

    // select list where condition
    public List<City> findCityByCodeAndPopulation(String countryCode, int population){
        return this.cityRepository.findByCountryCodeAndPopulation(countryCode, population);
    }
}
