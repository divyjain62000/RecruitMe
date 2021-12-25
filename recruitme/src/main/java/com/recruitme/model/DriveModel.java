package com.recruitme.model;

import com.recruitme.domain.Drive;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * DriveModel class contains all DS related to Drive
 */
public class DriveModel {

    public static List<Drive> driveList;
    public static Map<Integer,Drive> driveIdMap;

    private DriveModel() {}

    static
    {
        driveList=new LinkedList<>();
        driveIdMap=new HashMap<>();
    }
}
