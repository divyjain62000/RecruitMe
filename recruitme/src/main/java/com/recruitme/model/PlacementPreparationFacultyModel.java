package com.recruitme.model;

import com.recruitme.domain.PlacementPreparationFaculty;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * PlacementPreparationFacultyModel class contains all DS related to PlacementPreparationFaculty
 */
public class PlacementPreparationFacultyModel {

    public static List<PlacementPreparationFaculty> placementPreparationFacultyList;
    public static Map<Long,PlacementPreparationFaculty> placementPreparationFacultyIdMap;
    public static Map<String,PlacementPreparationFaculty> placementPreparationFacultyEmailMap;
    public static Map<String,PlacementPreparationFaculty> placementPreparationFacultyMobileMap;

    private PlacementPreparationFacultyModel() {}

    static
    {
        placementPreparationFacultyList=new LinkedList<>();
        placementPreparationFacultyIdMap=new HashMap<>();
        placementPreparationFacultyEmailMap=new HashMap<>();
        placementPreparationFacultyMobileMap=new HashMap<>();
    }

}
