package com.kjm.test.demo.repository;

public class CitySql {
    public static final String SELECT = "SELECT id, name, countrycode, district, population FROM city WHERE 1=1 ";
    public static final String COUNTRY_CODE_CONDITION = " AND COUNTRYCODE = :countryCode";
    public static final String POPULATION_CONDITION = " AND POPULATION >= :population";
}
