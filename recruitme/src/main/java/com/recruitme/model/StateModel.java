package com.recruitme.model;

import com.recruitme.domain.State;
import com.recruitme.service.domain.state.dto.StateDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * StateModel class contains all DS related to State
 */
public class StateModel {
    public static Map<Integer,List<StateDTO>> stateCountryIdMap;

    private StateModel() {}

    static
    {
        stateCountryIdMap=new HashMap<>();
    }
}
