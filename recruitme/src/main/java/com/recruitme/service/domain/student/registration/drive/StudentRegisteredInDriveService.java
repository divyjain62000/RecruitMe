package com.recruitme.service.domain.student.registration.drive;

import com.recruitme.exceptions.RecruitmeException;

import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveDTO;
import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveResponseDTO;
import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;
import java.util.List;


/**
 * Service interface for managing students registered in drive
 */
public interface StudentRegisteredInDriveService {
    void save(StudentRegisteredInDriveDTO studentDTO) throws RecruitmeException;
    List<StudentRegisteredInDriveResponseDTO> findAll(StudentRegisteredInDriveSearchDTO studentRegisteredInDriveSearchDTO) throws RecruitmeException;
    Page<StudentRegisteredInDriveResponseDTO> search(StudentRegisteredInDriveSearchDTO studentRegisteredInDriveSearchDTO, Pageable pageable) throws RecruitmeException;
    ByteArrayInputStream exportStudentRegisterdInDriveExcelFile(StudentRegisteredInDriveSearchDTO studentRegisteredInDriveSearchDTO) throws RecruitmeException;
    void deleteByStudentId(Long studentId);
}
