package com.recruitme.service.domain.address.dto;

import com.recruitme.domain.City;
import com.recruitme.domain.Country;
import com.recruitme.domain.State;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class AddressDTO extends DataTransferObject {

    private Integer id;
    private String addressLine1;
    private String addressLine2;
    private Integer countryId;
    private Integer stateId;
    private Integer cityId;
    private String pinCode;

}
