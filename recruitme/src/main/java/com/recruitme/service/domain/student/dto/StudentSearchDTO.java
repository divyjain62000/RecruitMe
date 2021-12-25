package com.recruitme.service.domain.student.dto;

import com.recruitme.service.dto.BaseFilterDTO;
import lombok.Data;

/**
 * This class is used as filter dto
 */
@Data
public class StudentSearchDTO extends BaseFilterDTO {

    private String enrollmentNumber;
    private String secondaryEmail;
    private String secondaryMobileNumber;
    private Boolean isPlaceForFulltime;
    private Boolean isPlaceForInternship;
    private Boolean isBlacklisted;
    private Boolean isAnyCriminalCase;

}
