package com.recruitme.service.domain.parents.dto;

import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class ParentsDTO extends DataTransferObject {

    private Integer id;
    private String motherName;
    private String fatherName;
    private String mobileNumber;

}
