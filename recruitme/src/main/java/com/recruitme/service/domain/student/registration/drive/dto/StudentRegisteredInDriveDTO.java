package com.recruitme.service.domain.student.registration.drive.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class StudentRegisteredInDriveDTO implements Serializable {
    private Integer id;
    private Integer driveId;
    private Long studentId;
}