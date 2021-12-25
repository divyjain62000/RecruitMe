package com.recruitme.model;

import com.recruitme.domain.Branch;
import com.recruitme.service.domain.branch.dto.BranchDTO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * BranchModel class contains all DS related to {@link Branch}
 */
public class BranchModel {
    public static List<BranchDTO> branchList;
    public static Map<Integer,BranchDTO> branchIdMap;
    public static Map<Integer,List<BranchDTO>> programIdBranchesMap;

    private BranchModel() {}

    static
    {
        branchList=new LinkedList<>();
        branchIdMap=new HashMap<>();
        programIdBranchesMap=new HashMap<>();
    }

}
