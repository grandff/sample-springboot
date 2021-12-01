package com.kjm.test.demo.repository;

import java.util.List;

import com.kjm.test.demo.model.City;

import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CityRepository {
    // spring jdbc
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    // row mapper
    private final CityRowMapper cityRowMapper;
    
    public CityRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.cityRowMapper = new CityRowMapper();
    }

    public List<City> findList(){        
        // vscode인지 내가 문제인지 모르겠지만 groovy에서 못찾아서 그냥 java 파일로 처리함
        log.debug("findList query = {}", CitySql.SELECT);
        // 해당 쿼리는 파라미터를 넘길 필요가 없으므로 EmptySqlParameterSource.INSTANCE를 던짐
        // 조회된 데이터를 cityRowMapper를 통해 맵핑 처리
        return namedParameterJdbcTemplate.query(CitySql.SELECT, EmptySqlParameterSource.INSTANCE, this.cityRowMapper);       
    }

    public List<City> findByCountryCodeAndPopulation(String countryCode, int population){
        String query = CitySql.SELECT + CitySql.COUNTRY_CODE_CONDITION + CitySql.POPULATION_CONDITION;   
        SqlParameterSource param = new MapSqlParameterSource("countryCode", countryCode).addValue("population", population);
        return namedParameterJdbcTemplate.query(query, param, this.cityRowMapper);
    }

    // insert
    public City insert(City city){        
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 자동 생성되는 id 값을 가져오기 위함??
        SqlParameterSource parameterSource = new MapSqlParameterSource("name", city.getName())
        .addValue("countryCode", city.getCountryCode())
        .addValue("district", city.getDistrict())
        .addValue("population", city.getPopulation());

        int result = namedParameterJdbcTemplate.update(CitySql.INSERT, parameterSource, keyHolder);

        log.debug("{} inserted, new id = {}", result, keyHolder.getKey());
        city.setId(keyHolder.getKey().intValue());  // return 시켜주는 city에 id 값 설정
        return city;
    }

    // update
    public Integer updateById(City city){
        String query = CitySql.UPDATE + CitySql.ID_CONDITION;
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", city.getId())
        .addValue("name", city.getName())
        .addValue("countryCode", city.getCountryCode())
        .addValue("district", city.getDistrict())
        .addValue("population", city.getPopulation());
        return namedParameterJdbcTemplate.update(query, parameterSource);
    }

    // delete
    public Integer deleteById(Integer id){
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.update(CitySql.DELETE + CitySql.ID_CONDITION, parameterSource);
    }
}
