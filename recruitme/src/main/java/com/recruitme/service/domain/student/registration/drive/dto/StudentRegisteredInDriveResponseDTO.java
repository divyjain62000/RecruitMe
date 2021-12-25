package com.recruitme.service.domain.student.registration.drive.dto;

import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

@Data
public class StudentRegisteredInDriveResponseDTO extends DataTransferObject {
    private Long id;
    private String studentName;
    private String enrollmentNumber;
    private String seniorSecondaryMarks;
    private String highSchoolMarks;
    private String ugMarks;
    private String pgMarks;
    private String primaryMobileNumber;
    private String secondaryMobileNumber;
    private String primaryEmail;
    private String secondaryEmail;
    private String program;
    private String branch;

}
