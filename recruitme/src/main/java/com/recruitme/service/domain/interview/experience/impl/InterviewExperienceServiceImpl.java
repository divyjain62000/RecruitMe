package com.recruitme.service.domain.interview.experience.impl;

import com.recruitme.domain.InterviewExperience;
import com.recruitme.enums.error.CommonError;
import com.recruitme.enums.error.ErrorFor;
import com.recruitme.enums.error.InterviewExperienceError;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.model.InterviewExperienceModel;
import com.recruitme.model.StudentModel;
import com.recruitme.repository.interview.experience.InterviewExperienceRepository;
import com.recruitme.service.domain.interview.experience.InterviewExperienceService;
import com.recruitme.service.domain.interview.experience.dto.InterviewExperienceDTO;
import com.recruitme.service.domain.interview.experience.dto.InterviewExperienceResponseDTO;
import com.recruitme.service.domain.interview.experience.dto.InterviewExperienceSearchDTO;
import com.recruitme.service.domain.interview.experience.mapper.InterviewExperienceMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class InterviewExperienceServiceImpl implements InterviewExperienceService {

    @Autowired
    private InterviewExperienceRepository interviewExperienceRepository;

    @Override
    public void save(InterviewExperienceDTO interviewExperienceDTO) throws RecruitmeException {
        RecruitmeException recruitmeException=new RecruitmeException();
        if(interviewExperienceDTO==null) recruitmeException.addException(ErrorFor.NEED_ALL_DETAIL_ERR.getErrorFor(), CommonError.NEED_ALL_DETAIL.getCommonError());
        if(recruitmeException.getExceptions().size()>0) throw recruitmeException;
        recruitmeException=isValidInterviewExperience(interviewExperienceDTO);
        if(recruitmeException!=null && recruitmeException.getExceptions().size()>0) throw recruitmeException;
        ModelMapper mapper=new ModelMapper();
        InterviewExperience interviewExperience=mapper.map(interviewExperienceDTO,InterviewExperience.class);
        interviewExperienceRepository.save(interviewExperience);
        InterviewExperienceModel.interviewExperienceList.add(interviewExperience);
    }

    @Override
    public Page<InterviewExperienceResponseDTO> search(InterviewExperienceSearchDTO interviewExperienceSearchDTO, Pageable pageable) {
        Set<InterviewExperienceResponseDTO> interviewExperienceResponseDTOSet=new HashSet<>();
        InterviewExperienceMapper interviewExperienceMapper=new InterviewExperienceMapper();
        if(interviewExperienceSearchDTO.getId()!=null) {
            InterviewExperienceModel.interviewExperienceList.forEach((interviewExperience)->{
                if(interviewExperience.getId()==interviewExperienceSearchDTO.getId()) {
                    InterviewExperienceResponseDTO interviewExperienceResponseDTO=interviewExperienceMapper.toDTO(interviewExperience);
                    interviewExperienceResponseDTOSet.add(interviewExperienceResponseDTO);
                }
            });
        }
        boolean flag=false;
        if(interviewExperienceSearchDTO.getStudentName()!=null || interviewExperienceSearchDTO.getCompany()!=null || interviewExperienceSearchDTO.getPassoutYear()!=null) {
            flag=true;
            InterviewExperienceModel.interviewExperienceList.forEach((interviewExperience)->{
                String studentName=interviewExperience.getStudent().getName();
                String company=interviewExperience.getCompany();
                Short passoutYear=interviewExperience.getPassoutYear();
                if(interviewExperienceSearchDTO.getStudentName()!=null && (studentName!=null && studentName.toLowerCase().contains(interviewExperienceSearchDTO.getStudentName().toLowerCase()) || (interviewExperienceSearchDTO.getSearch()!=null && studentName.toLowerCase().contains(interviewExperienceSearchDTO.getSearch())))) {
                    InterviewExperienceResponseDTO interviewExperienceResponseDTO=interviewExperienceMapper.toDTO(interviewExperience);
                    interviewExperienceResponseDTOSet.add(interviewExperienceResponseDTO);
                }
                if(interviewExperienceSearchDTO.getCompany()!=null && (company.toLowerCase().contains(interviewExperienceSearchDTO.getCompany().toLowerCase()) || (interviewExperienceSearchDTO.getSearch()!=null && company.toLowerCase().contains(interviewExperienceSearchDTO.getSearch())))) {
                    InterviewExperienceResponseDTO interviewExperienceResponseDTO=interviewExperienceMapper.toDTO(interviewExperience);
                    interviewExperienceResponseDTOSet.add(interviewExperienceResponseDTO);
                }
                if(interviewExperienceSearchDTO.getPassoutYear()!=null && (passoutYear.equals(interviewExperienceSearchDTO.getPassoutYear())) || (interviewExperienceSearchDTO.getSearch()!=null && passoutYear.toString().equals(interviewExperienceSearchDTO.getSearch()))) {
                    InterviewExperienceResponseDTO interviewExperienceResponseDTO=interviewExperienceMapper.toDTO(interviewExperience);
                    interviewExperienceResponseDTOSet.add(interviewExperienceResponseDTO);
                }
            });
        }
        List<InterviewExperienceResponseDTO> interviewExperienceResponseDTOList=new ArrayList<>(interviewExperienceResponseDTOSet);
        long interviewExperienceResponseDTOListSize=interviewExperienceResponseDTOList.size();
        if(interviewExperienceResponseDTOListSize==0 && flag==false) {
            InterviewExperienceModel.interviewExperienceList.forEach((interviewExperience)->{
                InterviewExperienceResponseDTO interviewExperienceResponseDTO=interviewExperienceMapper.toDTO(interviewExperience);
                interviewExperienceResponseDTOList.add(interviewExperienceResponseDTO);
            });
        }
        return new PageImpl<>(interviewExperienceResponseDTOList,pageable,interviewExperienceResponseDTOListSize);
    }

    private RecruitmeException isValidInterviewExperience(InterviewExperienceDTO interviewExperienceDTO) {
        RecruitmeException recruitmeException=new RecruitmeException();
        if(interviewExperienceDTO.getStudentId()==null) {
            recruitmeException.addException(ErrorFor.STUDENT_ID_ERR.getErrorFor(), InterviewExperienceError.STUDENT_ID_REQUIRED.getInterviewExperienceError());
        }
        else if(StudentModel.studentIdMap.containsKey(interviewExperienceDTO.getStudentId())==false){
            recruitmeException.addException(ErrorFor.STUDENT_ID_ERR.getErrorFor(), InterviewExperienceError.INVALID_STUDENT_ID.getInterviewExperienceError());
        }
        if(interviewExperienceDTO.getCompany()==null) {
            recruitmeException.addException(ErrorFor.COMPANY_ERR.getErrorFor(),InterviewExperienceError.COMPANY_NAME_REQUIRED.getInterviewExperienceError());
        }
        if(interviewExperienceDTO.getPassoutYear()==null) {
            recruitmeException.addException(ErrorFor.PASSOUT_YEAR_ERR.getErrorFor(), InterviewExperienceError.PASSOUT_YEAR_REQUIRED.getInterviewExperienceError());
        }else if(interviewExperienceDTO.getPassoutYear().equals(0) || interviewExperienceDTO.getPassoutYear()<2020){
            recruitmeException.addException(ErrorFor.PASSOUT_YEAR_ERR.getErrorFor(), InterviewExperienceError.INVALID_PASSOUT_YEAR.getInterviewExperienceError());
        }
        if(interviewExperienceDTO.getExperience()==null || interviewExperienceDTO.getExperience().length()==0) {
            recruitmeException.addException(ErrorFor.EXPERIENCE_ERR.getErrorFor(), InterviewExperienceError.EXPERIENCE_REQUIRED.getInterviewExperienceError());
        }
        if(interviewExperienceDTO.getSalary()==null) {
            recruitmeException.addException(ErrorFor.SALARY_ERR.getErrorFor(), InterviewExperienceError.SALARY_REQUIRED.getInterviewExperienceError());
        }
        else if(interviewExperienceDTO.getSalary().compareTo(BigDecimal.ZERO)<0) {
            recruitmeException.addException(ErrorFor.SALARY_ERR.getErrorFor(), InterviewExperienceError.INVALID_SALARY.getInterviewExperienceError());
        }
        if((interviewExperienceDTO.getIsInternshipVacancy()==null && interviewExperienceDTO.getIsFullTimeVacancy()==null) || (interviewExperienceDTO.getIsInternshipVacancy()==false && interviewExperienceDTO.getIsFullTimeVacancy()==false)) {
            recruitmeException.addException(ErrorFor.VACANCY_TYPE_ERR.getErrorFor(),InterviewExperienceError.VACANCY_TYPE_REQUIRED.getInterviewExperienceError());
        }
        if(interviewExperienceDTO.getUgStudent()==null && interviewExperienceDTO.getPgStudent()==null) {
            recruitmeException.addException(ErrorFor.GRADUATION_TYPE_ERR.getErrorFor(),InterviewExperienceError.GRADUATION_TYPE_REQUIRED.getInterviewExperienceError());
        }
        if(recruitmeException.getExceptions().size()>0) return recruitmeException;
        return null;
    }

    public void deleteByStudentId(Long studentId) {
        List<InterviewExperience> interviewExperiences=this.interviewExperienceRepository.findAllByStudentId(studentId);
        this.interviewExperienceRepository.deleteAll(interviewExperiences);
    }

}


