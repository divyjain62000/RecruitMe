package com.recruitme.service.domain.country.dto;

import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
@Data
public class CountryDTO extends DataTransferObject {

    private Integer id;
    private String countryCode;
    private String name;
    private String phoneCode;
}
