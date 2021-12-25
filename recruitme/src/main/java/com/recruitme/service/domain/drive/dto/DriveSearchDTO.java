package com.recruitme.service.domain.drive.dto;

import com.recruitme.service.dto.BaseFilterDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DriveSearchDTO extends BaseFilterDTO {

    private String company;
    private Boolean isOnlyForGirls;
    private Boolean isOnlyForBoys;
    private LocalDateTime lastDateToApply;
    private Boolean isFullTimeVaccancy;
    private Boolean isInternshipVaccancy;

}
