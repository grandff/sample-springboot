package com.kjm.test.demo;

import java.util.Date;

import com.kjm.test.demo.model.Project;

import org.springframework.stereotype.Service;

@Service
public class InfoService {
    public Project getProjectInfo(){
        Project project = new Project();
        project.projectName = "kjm";
        project.author = "hello-kjm";
        project.createdDate = new Date();
        return project;
    }   
}
