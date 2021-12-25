package com.recruitme.model;

import com.recruitme.domain.Country;
import com.recruitme.service.domain.country.dto.CountryDTO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * CountryModel class contains all DS related to Country
 */
public class CountryModel {

    public static List<CountryDTO> countryList;
    public static Map<Integer,CountryDTO> countryIdMap;

    private CountryModel() {}

    static
    {
        countryList=new LinkedList<>();
        countryIdMap=new HashMap<>();
    }
}
