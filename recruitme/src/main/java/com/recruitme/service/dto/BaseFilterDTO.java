package com.recruitme.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * We use this BaseFilterDTO as parent class for other seacrh dto or filter dto
 */
@Data
public class BaseFilterDTO extends DataTransferObject {
    private Integer id;
    private String name;
    private Integer branchId;
    private Integer programId;
    private String primaryMobileNumber;
    private String primaryEmail;
    private String search;
}
