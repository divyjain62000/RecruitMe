package com.recruitme.model;

import com.recruitme.domain.PlacementCoordinator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * PlacementCoordinatorModel class contains all DS related to PlacementCoordinator
 */
public class PlacementCoordinatorModel {

    public static List<PlacementCoordinator> placementCoordinatorList;
    public static Map<Long,PlacementCoordinator> placementCoordinatorIdMap;
    public static Map<String,PlacementCoordinator> placementCoordinatorEmailMap;
    public static Map<String,PlacementCoordinator> placementCoordinatorMobileMap;

    private PlacementCoordinatorModel() {}

    static
    {
        placementCoordinatorList=new LinkedList<>();
        placementCoordinatorIdMap=new HashMap<>();
        placementCoordinatorEmailMap=new HashMap<>();
        placementCoordinatorMobileMap=new HashMap<>();
    }

}
