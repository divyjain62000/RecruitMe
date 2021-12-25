package com.recruitme.service.domain.education.impl;

import com.recruitme.domain.Education;
import com.recruitme.enums.MarksType;
import com.recruitme.enums.SchoolBoard;
import com.recruitme.enums.error.*;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.model.BranchModel;
import com.recruitme.model.ProgramModel;
import com.recruitme.repository.EducationRepository;
import com.recruitme.service.domain.education.EducationService;
import com.recruitme.service.domain.education.dto.EducationDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EducationServiceImpl implements EducationService {

    @Autowired
    private EducationRepository educationRepository;

    /**
     * method to add education
     * @param educationDTO
     * @throws RecruitmeException
     */
    @Override
    public Integer save(EducationDTO educationDTO) throws RecruitmeException {
        if(educationDTO.getId()==null || educationDTO.getId()!=0) educationDTO.setId(0);
        log.debug("EducationDTO: {}",educationDTO);
        System.out.println("EducationDTO: "+educationDTO);
        RecruitmeException recruitmeException=isValidEducationDetails(educationDTO);
        if(recruitmeException!=null) throw  recruitmeException;
        ModelMapper modelMapper=new ModelMapper();
        Education education=modelMapper.map(educationDTO,Education.class);
        log.debug("Education obj: {}",education);
        education=this.educationRepository.save(education);
        return education.getId();
    }
    /**
     * method to edit education
     * @param educationDTO
     * @throws RecruitmeException
     */
    @Override
    public void edit(EducationDTO educationDTO) throws RecruitmeException {
        RecruitmeException recruitmeException=isValidEducationDetails(educationDTO);
        if(recruitmeException!=null) throw  recruitmeException;
        else recruitmeException=new RecruitmeException();
        if(educationDTO.getId()==null) { //from here
            recruitmeException.addException(ErrorFor.ID_ERR.getErrorFor(), CommonError.ID_REQUIRED.getCommonError());
        }else {
            Optional<Education> education = educationRepository.findById(educationDTO.getId());
            if (education.isPresent() == false)
                recruitmeException.addException(ErrorFor.ID_ERR.getErrorFor(), CommonError.INVALID_ID.getCommonError());
        }
        if(recruitmeException!=null && recruitmeException.getExceptions().size()>0) throw  recruitmeException; //to here
        ModelMapper modelMapper=new ModelMapper();
        Education education=modelMapper.map(educationDTO,Education.class);
        this.educationRepository.save(education);
    }

    @Override
    public EducationDTO findById(Integer id) {
        if(id==null) return null;
        Optional<Education> education=this.educationRepository.findById(id);
        if(education.isPresent()) {
            ModelMapper mapper=new ModelMapper();
            EducationDTO educationDTO=mapper.map(education,EducationDTO.class);
            return educationDTO;
        }
        return null;
    }

    /**
     *
     * @param educationDTO
     * @return the {@link RecruitmeException}
     */
    private RecruitmeException isValidEducationDetails(EducationDTO educationDTO) {
        RecruitmeException recruitmeException=new RecruitmeException();

        //UG Related Verification starts form here
        if(educationDTO.getUgCollegeName()==null || educationDTO.getUgCollegeName().length()==0) {
            recruitmeException.addException(ErrorFor.UG_COLLEGE_NAME_ERR.getErrorFor(), EducationError.COLLEGE_NAME_REQUIRED.getEducationError());
        }

        if(educationDTO.getUgProgramId()==null || educationDTO.getUgProgramId()==0) {
            recruitmeException.addException(ErrorFor.UG_PROGRAM_ERR.getErrorFor(), ProgramError.PROGRAM_NAME_REQUIRED.getProgramError());
        }
        else {
            if(ProgramModel.programIdMap.containsKey(educationDTO.getUgProgramId())==false) {
                recruitmeException.addException(ErrorFor.UG_PROGRAM_ERR.getErrorFor(),ProgramError.PROGRAM_NOT_EXISTS.getProgramError());
            }
        }

        //On front end we always get branch of particular program so there will be never case occur that branch not come under program that is selected by user
        if(educationDTO.getUgBranchId()==null || educationDTO.getUgBranchId()==0) {
            recruitmeException.addException(ErrorFor.UG_BRANCH_ERR.getErrorFor(),BranchError.BRANCH_NAME_REQUIRED.getBranchError());
        }
        else {
            if(BranchModel.branchIdMap.containsKey(educationDTO.getUgBranchId())==false) {
                recruitmeException.addException(ErrorFor.UG_BRANCH_ERR.getErrorFor(),BranchError.BRANCH_NOT_EXISTS.getBranchError());
            }
        }

        if(educationDTO.getUgMarksType()==null) {
            recruitmeException.addException(ErrorFor.UG_MARKS_TYPE_ERR.getErrorFor(), EducationError.MARKS_TYPE_REQUIRED.getEducationError());
        }
        else if(educationDTO.getUgMarksType()==MarksType.CGPA && (educationDTO.getUgMarks()==null || educationDTO.getUgMarks()<0.0f || educationDTO.getUgMarks()>10.0f)) {
            recruitmeException.addException(ErrorFor.UG_MARKS_ERR.getErrorFor(), EducationError.INVALID_MARKS.getEducationError());
        }
        else if(educationDTO.getUgMarksType()==MarksType.PERCENTAGE && (educationDTO.getUgMarks()==null || educationDTO.getUgMarks()<0.0f || educationDTO.getUgMarks()>100.0f)) {
            recruitmeException.addException(ErrorFor.UG_MARKS_ERR.getErrorFor(), EducationError.INVALID_MARKS.getEducationError());
        }
        else if(educationDTO.getUgMarksType()==MarksType.NA) {
            recruitmeException.addException(ErrorFor.UG_MARKS_TYPE_ERR.getErrorFor(), EducationError.NA_NOT_APPLICABLE_FOR_MARKS_TYPE.getEducationError());
        }

        if(educationDTO.getUgPassoutYear()==null) {
            recruitmeException.addException(ErrorFor.UG_PASSOUT_YEAR_ERR.getErrorFor(), EducationError.PASSOUT_YEAR_REQUIRED.getEducationError());
        }
        //UG Related Verification ends here


        /*All PG related check need to apply on front end*/

        //10th Related Verification start form here
        if(educationDTO.getSeniorSecondarySchool()==null || educationDTO.getSeniorSecondarySchool().length()==0) {
            recruitmeException.addException(ErrorFor.SENIOR_SECONDARY_SCHOOL_ERR.getErrorFor(), EducationError.SCHOOL_NAME_REQUIRED.getEducationError());
        }

        if(educationDTO.getSeniorSecondarySchoolBoard()==null) {
            recruitmeException.addException(ErrorFor.SENIOR_SECONDARY_BOARD_TYPE_ERR.getErrorFor(), EducationError.SCHOOL_BOARD_REQUIRED.getEducationError());
        }

        if(educationDTO.getSeniorSecondarySchoolMarksType()==null) {
            recruitmeException.addException(ErrorFor.SENIOR_SECONDARY_SCHOOL_MARKS_TYPE_ERR.getErrorFor(), EducationError.MARKS_TYPE_REQUIRED.getEducationError());
        }
        else if(educationDTO.getSeniorSecondarySchoolMarksType()==MarksType.CGPA && (educationDTO.getSeniorSecondarySchoolMarks()==null || educationDTO.getSeniorSecondarySchoolMarks()<0.0f || educationDTO.getSeniorSecondarySchoolMarks()>10.0f)) {
            recruitmeException.addException(ErrorFor.SENIOR_SECONDARY_SCHOOL_MARKS_ERR.getErrorFor(), EducationError.INVALID_MARKS.getEducationError());
        }
        else if(educationDTO.getSeniorSecondarySchoolMarksType()==MarksType.PERCENTAGE && (educationDTO.getSeniorSecondarySchoolMarks()==null || educationDTO.getSeniorSecondarySchoolMarks()<0.0f || educationDTO.getSeniorSecondarySchoolMarks()>100.0f) ) {
            recruitmeException.addException(ErrorFor.SENIOR_SECONDARY_SCHOOL_MARKS_ERR.getErrorFor(), EducationError.INVALID_MARKS.getEducationError());
        }
        else if(educationDTO.getSeniorSecondarySchoolMarksType()==MarksType.NA) {
            recruitmeException.addException(ErrorFor.SENIOR_SECONDARY_SCHOOL_MARKS_TYPE_ERR.getErrorFor(), EducationError.NA_NOT_APPLICABLE_FOR_MARKS_TYPE.getEducationError());
        }
        if(educationDTO.getSeniorSecondarySchoolPassoutYear()==null) {
            recruitmeException.addException(ErrorFor.SENIOR_SECONDARY_SCHOOL_PASSOUT_YEAR_ERR.getErrorFor(), EducationError.PASSOUT_YEAR_REQUIRED.getEducationError());
        }
        //10th Related Verification ends here

        //12th Related Verification start form here
        if(educationDTO.getHighSchool()==null || educationDTO.getHighSchool().length()==0) {
            recruitmeException.addException(ErrorFor.HIGH_SCHOOL_ERR.getErrorFor(), EducationError.SCHOOL_NAME_REQUIRED.getEducationError());
        }
        if(educationDTO.getHighSchoolBoard()==null) {
            recruitmeException.addException(ErrorFor.HIGH_SCHOOL_BOARD_TYPE_ERR.getErrorFor(), EducationError.SCHOOL_BOARD_REQUIRED.getEducationError());
        }

        if(educationDTO.getHighSchoolMarksType()==null) {
            recruitmeException.addException(ErrorFor.HIGH_SCHOOL_MARKS_TYPE_ERR.getErrorFor(), EducationError.MARKS_TYPE_REQUIRED.getEducationError());
        }
        else if(educationDTO.getHighSchoolMarksType()==MarksType.CGPA && (educationDTO.getHighSchoolMarks()==null || educationDTO.getHighSchoolMarks()<0.0f || educationDTO.getHighSchoolMarks()>10.0f)) {
            recruitmeException.addException(ErrorFor.HIGH_SCHOOL_MARKS_ERR.getErrorFor(), EducationError.INVALID_MARKS.getEducationError());
        }
        else if(educationDTO.getHighSchoolMarksType()==MarksType.PERCENTAGE && (educationDTO.getHighSchoolMarks()==null || educationDTO.getHighSchoolMarks()<0.0f || educationDTO.getHighSchoolMarks()>100.0f) ) {
            recruitmeException.addException(ErrorFor.HIGH_SCHOOL_MARKS_ERR.getErrorFor(), EducationError.INVALID_MARKS.getEducationError());
        }
        else if(educationDTO.getHighSchoolMarksType()==MarksType.NA) {
            recruitmeException.addException(ErrorFor.HIGH_SCHOOL_MARKS_TYPE_ERR.getErrorFor(), EducationError.NA_NOT_APPLICABLE_FOR_MARKS_TYPE.getEducationError());
        }
        if(educationDTO.getHighSchoolPassoutYear()==null) {
            recruitmeException.addException(ErrorFor.HIGH_SCHOOL_PASSOUT_YEAR_ERR.getErrorFor(), EducationError.PASSOUT_YEAR_REQUIRED.getEducationError());
        }
        //12th Related Verification ends here


        //education gap related verification starts from here
        if(educationDTO.getIsAnyGapInEducation()==null) {
            recruitmeException.addException(ErrorFor.IS_ANY_EDUCATIONAL_GAP_ERR.getErrorFor(), EducationError.EDUCATION_GAP_REQUIRED.getEducationError());
        }
        else if(educationDTO.getIsAnyGapInEducation() && (educationDTO.getReasonForEducationalGap()==null || educationDTO.getReasonForEducationalGap().length()==0)) {
            recruitmeException.addException(ErrorFor.REASON_FOR_EDUCATION_GAP_ERR.getErrorFor(), EducationError.REASON_FOR_EDUCATIONAL_GAP_REQUIRED.getEducationError());
        }
        //education gap related verification ends here

        if(educationDTO.getNumberOfCurrentBacklog()==null) {
            recruitmeException.addException(ErrorFor.NUMBER_OF_CURRENT_BACKLOG_ERR.getErrorFor(), EducationError.INVALID_NUMBER_OF_CURRENT_BACKLOG.getEducationError());
        }

        if(recruitmeException.getExceptions().size()==0) return null;
        return recruitmeException;
    }

    @Override
    public void delete(Education education) {
        this.educationRepository.delete(education);
    }



}
