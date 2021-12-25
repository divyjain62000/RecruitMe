package com.recruitme.model;

import com.recruitme.domain.City;
import com.recruitme.service.domain.city.dto.CityDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * CityModel class contains all DS related to City
 */
public class CityModel {

    public static Map<Integer,List<CityDTO>> cityStateIdMap;

    private CityModel() {}

    static
    {
        cityStateIdMap=new HashMap<>();
    }
}
