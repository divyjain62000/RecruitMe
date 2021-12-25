package com.recruitme.service.domain.state.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recruitme.domain.Address;
import com.recruitme.domain.City;
import com.recruitme.domain.Country;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class StateDTO extends DataTransferObject {

    private Integer id;
    private String name;
    private Integer countryId;
}
