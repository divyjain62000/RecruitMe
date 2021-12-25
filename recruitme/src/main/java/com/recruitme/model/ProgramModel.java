package com.recruitme.model;

import com.recruitme.domain.Program;
import com.recruitme.service.domain.program.dto.ProgramDTO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * ProgramModel class contains all DS related to Program
 */
public class ProgramModel {
    public static List<ProgramDTO> programList;
    public static Map<Integer,ProgramDTO> programIdMap;
    private ProgramModel() {}
    static
    {
        programList=new LinkedList<>();
        programIdMap=new HashMap<>();
    }
}
