package com.recruitme.service.domain.interview.experience.mapper;

import com.recruitme.domain.Education;
import com.recruitme.domain.InterviewExperience;
import com.recruitme.domain.Student;
import com.recruitme.model.StudentModel;
import com.recruitme.service.domain.interview.experience.dto.InterviewExperienceResponseDTO;


public class InterviewExperienceMapper {

    /**
     * To convert {@link InterviewExperience} to {@link InterviewExperienceResponseDTO}
     * @param interviewExperience
     * @return {@link InterviewExperienceResponseDTO}
     */
    public InterviewExperienceResponseDTO toDTO(InterviewExperience interviewExperience) {

        InterviewExperienceResponseDTO interviewExperienceResponseDTO = new InterviewExperienceResponseDTO();
        interviewExperienceResponseDTO.setId(interviewExperience.getId());
        interviewExperienceResponseDTO.setCompany(interviewExperience.getCompany());
        interviewExperienceResponseDTO.setPassoutYear(interviewExperience.getPassoutYear());
        interviewExperienceResponseDTO.setSalary(interviewExperience.getSalary());
        interviewExperienceResponseDTO.setIsFullTimeVaccany(interviewExperience.getIsFullTimeVacancy());
        interviewExperienceResponseDTO.setIsInternshipVaccancy(interviewExperience.getIsInternshipVacancy());
        interviewExperienceResponseDTO.setExperience(interviewExperience.getExperience());
        Student student= StudentModel.studentIdMap.get(interviewExperience.getStudent().getId());
        Education education= student.getEducation();
        if(education.getPgBranch()!=null) {
            interviewExperienceResponseDTO.setProgram(education.getPgProgram().getProgramCode());
            interviewExperienceResponseDTO.setBranch(education.getPgBranch().getBranchCode());
        }else if(education.getUgBranch()!=null) {
            interviewExperienceResponseDTO.setProgram(education.getUgProgram().getProgramCode());
            interviewExperienceResponseDTO.setBranch(education.getUgBranch().getBranchCode());
        }
        interviewExperienceResponseDTO.setStudentName(student.getName());
        return interviewExperienceResponseDTO;
    }

}
