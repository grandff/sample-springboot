package com.kjm.test.demo.repository;

public class CitySql {
    public static final String SELECT = "SELECT id, name, countrycode, district, population FROM city WHERE 1=1 ";
    public static final String COUNTRY_CODE_CONDITION = " AND COUNTRYCODE = :countryCode";
    public static final String POPULATION_CONDITION = " AND POPULATION >= :population";
    public static final String INSERT = " INSERT INTO CITY (name, countryCode, district, population) values (:name, :countryCode, :district, :population) ";
    public static final String UPDATE = " UPDATE CITY SET NAME = :name, COUNTRYCODE = :countryCode, DISTRICT = :district, POPULATION = :population WHERE 1=1 ";
    public static final String ID_CONDITION = " AND id = :id ";
    public static final String DELETE = " DELETE FROM CITY where 1=1 ";
}
