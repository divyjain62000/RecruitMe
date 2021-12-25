package com.recruitme.service.domain.interview.experience;

import com.recruitme.domain.InterviewExperience;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.service.domain.interview.experience.dto.InterviewExperienceDTO;
import com.recruitme.service.domain.interview.experience.dto.InterviewExperienceResponseDTO;
import com.recruitme.service.domain.interview.experience.dto.InterviewExperienceSearchDTO;
import com.recruitme.service.domain.student.dto.StudentDTO;
import com.recruitme.service.domain.student.dto.StudentSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InterviewExperienceService {
    void save(InterviewExperienceDTO interviewExperienceDTO) throws RecruitmeException;
    Page<InterviewExperienceResponseDTO> search(InterviewExperienceSearchDTO interviewExperienceSearchDTO, Pageable pageable);
    void deleteByStudentId(Long studentId);
}
