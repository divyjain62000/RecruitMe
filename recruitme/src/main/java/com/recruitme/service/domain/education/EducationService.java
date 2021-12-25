package com.recruitme.service.domain.education;

import com.recruitme.domain.Education;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.service.domain.education.dto.EducationDTO;

/**
 * Service interface for managing Education
 */
public interface EducationService {

    Integer save(EducationDTO educationDTO) throws RecruitmeException;
    void edit(EducationDTO educationDTO) throws RecruitmeException;
    EducationDTO findById(Integer id);
    void delete(Education education);

}
