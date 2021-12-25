package com.recruitme.service.domain.branch.dto;

import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

@Data
public class BranchDTO  extends DataTransferObject {

    private Integer id;
    private String name;
    private String branchCode;
    private Integer programId;
}