package com.kjm.test.demo.repository;

class CitySql {
    public static final String SELECT = """
        SELECT id, name, countrycode, district, population FROM city LIMIT 1000;
    """; 
}