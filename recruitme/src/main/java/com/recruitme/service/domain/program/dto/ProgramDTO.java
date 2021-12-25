package com.recruitme.service.domain.program.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recruitme.domain.*;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class ProgramDTO extends DataTransferObject {

    private Integer id;
    private String name;
    private String programCode;
}
