package com.recruitme.service.file.excel;

import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveResponseDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ExcelFileService {

    ByteArrayInputStream exportStudentRegisteredInDriveExcelFile(List<String> header, List<StudentRegisteredInDriveResponseDTO> data, int columns,String fileName);

}
