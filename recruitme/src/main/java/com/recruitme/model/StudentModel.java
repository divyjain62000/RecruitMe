package com.recruitme.model;

import com.recruitme.domain.Student;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * StudentModel class contains all DS related to Student
 */
public class StudentModel {
    public static List<Student> studentList;
    public static Map<Long,Student> studentIdMap;
    public static Map<String,Student> studentEnrollmentMap;
    public static Map<String,Student> studentMouReferenceIdMap;
    public static Map<String,Student> studentPrimaryEmailMap;
    public static Map<String,Student> studentPrimaryMobileNumberMap;

    static
    {
        studentList=new LinkedList<>();
        studentIdMap=new HashMap<>();
        studentEnrollmentMap=new HashMap<>();
        studentMouReferenceIdMap=new HashMap<>();
        studentPrimaryEmailMap=new HashMap<>();
        studentPrimaryMobileNumberMap=new HashMap<>();
    }
}
