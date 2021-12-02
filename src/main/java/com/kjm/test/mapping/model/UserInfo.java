package com.kjm.test.mapping.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfo {
    private String name;
    private String city;
    private Integer age;
    private List<String> cars;
}
