package com.recruitme.model;

import com.recruitme.domain.StudentRegisteredInDrive;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StudentRegisteredInDriveModel {
    public static Map<Integer,List<Long>> studentRegisteredInDriveMap; //key -> drive id and value -> List of student id who apply for particular drive

    private StudentRegisteredInDriveModel() {}

    static
    {
        studentRegisteredInDriveMap=new HashMap<>();
    }
}
