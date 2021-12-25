package com.recruitme.service.domain.student.registration.drive.dto;

import com.recruitme.service.dto.BaseFilterDTO;
import lombok.Data;

@Data
public class StudentRegisteredInDriveSearchDTO extends BaseFilterDTO {
    private Integer driveId;
    private Boolean searchForUgStudents;
    private Boolean searchForPgStudents;
}