package com.kjm.test.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.kjm.test.demo.model.City;

import org.springframework.jdbc.core.RowMapper;

public class CityRowMapper implements RowMapper<City>{
    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException{
        City city = new City();
        city.setId(rs.getInt("id"));
        city.setName(rs.getString("name"));
        city.setCountryCode(rs.getString("countryCode"));
        city.setDistrict(rs.getString("district"));
        city.setPopulation(rs.getInt("population"));

        return city;            
    }
}
