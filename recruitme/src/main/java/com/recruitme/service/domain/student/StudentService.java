package com.recruitme.service.domain.student;

import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.service.domain.student.dto.StudentDTO;
import com.recruitme.service.domain.student.dto.StudentResponseDTO;
import com.recruitme.service.domain.student.dto.StudentSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing student
 */
public interface StudentService {
    void save(StudentDTO studentDTO) throws RecruitmeException;
    void edit(StudentDTO studentDTO) throws RecruitmeException;
    Page<StudentDTO> search(StudentSearchDTO studentSearchDTO, Pageable pageable);
    StudentResponseDTO findById(Long id);
    Page<StudentResponseDTO> findAll(Pageable pageable);
    void blacklistStudent(Long id,String blacklistReason) throws RecruitmeException;
    void unblacklistStudent(Long id) throws RecruitmeException;
    void delete(Long id);
}
