package com.recruitme.service.domain.student.holding.offers.impl;

import com.recruitme.domain.InterviewExperience;
import com.recruitme.domain.Student;
import com.recruitme.domain.StudentHoldingOffers;
import com.recruitme.enums.JobType;
import com.recruitme.enums.error.StudentHoldingOffersError;
import com.recruitme.model.StudentHoldingOffersModel;
import com.recruitme.repository.StudentHoldingOffersRepository;

import com.recruitme.enums.error.DriveError;
import com.recruitme.enums.error.ErrorFor;
import com.recruitme.enums.error.StudentError;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.model.DriveModel;
import com.recruitme.model.StudentModel;

import com.recruitme.repository.StudentRepository;
import com.recruitme.service.domain.student.StudentService;
import com.recruitme.service.domain.student.dto.StudentDTO;
import com.recruitme.service.domain.student.holding.offers.StudentHoldingOffersService;
import com.recruitme.service.domain.student.holding.offers.dto.StudentHoldingOfferResponseDTO;
import com.recruitme.service.domain.student.holding.offers.dto.StudentHoldingOffersDTO;

import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The type Student holding offers service.
 */
@Service
public class StudentHoldingOffersServiceImpl implements StudentHoldingOffersService {
    @Autowired
    private StudentHoldingOffersRepository studentHoldingOffersRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Save.
     *
     * @param studentHoldingOffersDTO the student holding offers dto
     * @throws RecruitmeException the recruitme exception
     */
    @Override
    public void save(StudentHoldingOffersDTO studentHoldingOffersDTO) throws RecruitmeException {
        RecruitmeException recruitmeException = new RecruitmeException();
        recruitmeException = isValidateStudentHoldingOffersDetails(studentHoldingOffersDTO);
        if (recruitmeException != null) throw recruitmeException;
        ModelMapper mapper = new ModelMapper();
        StudentHoldingOffers studentHoldingOffers = mapper.map(studentHoldingOffersDTO, StudentHoldingOffers.class);
        studentHoldingOffers = this.studentHoldingOffersRepository.save(studentHoldingOffers);

        //code to insert data in model
        List<StudentHoldingOffersDTO> studentHoldingOffersDTOList = null;
        if (StudentHoldingOffersModel.studentHoldingOffersDTOMap.containsKey(studentHoldingOffersDTO.getStudentId()) == false) {
            studentHoldingOffersDTOList = new LinkedList<>();
            studentHoldingOffersDTOList.add(studentHoldingOffersDTO);
            StudentHoldingOffersModel.studentHoldingOffersDTOMap.put(studentHoldingOffersDTO.getStudentId(), studentHoldingOffersDTOList);
        } else {
            studentHoldingOffersDTOList = StudentHoldingOffersModel.studentHoldingOffersDTOMap.get(studentHoldingOffersDTO.getStudentId());
            studentHoldingOffersDTOList.add(studentHoldingOffersDTO);
            StudentHoldingOffersModel.studentHoldingOffersDTOMap.put(studentHoldingOffersDTO.getStudentId(), studentHoldingOffersDTOList);
        }
        Student student=studentHoldingOffers.getStudent();
        student=StudentModel.studentIdMap.get(studentHoldingOffersDTO.getStudentId());
        JobType jobType=studentHoldingOffers.getJobType();
        if(JobType.FULLTIME==jobType) student.setIsPlaceForFulltime(true);
        else if(JobType.INTERNSHIP==jobType) student.setIsPlaceForInternship(true);
        else if(JobType.INTERNSHIP_AND_FULLTIME==jobType) {
            student.setIsPlaceForInternship(true);
            student.setIsPlaceForFulltime(true);
        }
        StudentDTO studentDTO=mapper.map(student,StudentDTO.class);
        this.studentRepository.save(student);
    }

    /**
     * Edit.
     *
     * @param studentHoldingOffersDTO the student holding offers dto
     * @throws RecruitmeException the recruitme exception
     */
    @Override
    public void edit(StudentHoldingOffersDTO studentHoldingOffersDTO) throws RecruitmeException {
        RecruitmeException recruitmeException = new RecruitmeException();
        recruitmeException = isValidateStudentHoldingOffersDetails(studentHoldingOffersDTO);
        if (recruitmeException != null) throw recruitmeException;
        ModelMapper mapper = new ModelMapper();
        StudentHoldingOffers studentHoldingOffers = mapper.map(studentHoldingOffersDTO, StudentHoldingOffers.class);
        studentHoldingOffers = this.studentHoldingOffersRepository.save(studentHoldingOffers);
        studentHoldingOffersDTO = mapper.map(studentHoldingOffers, StudentHoldingOffersDTO.class);

        //code to insert data in model
        List<StudentHoldingOffersDTO> studentHoldingOffersDTOList = StudentHoldingOffersModel.studentHoldingOffersDTOMap.get(studentHoldingOffersDTO.getStudentId());
        studentHoldingOffersDTOList.add(studentHoldingOffersDTO);
        StudentHoldingOffersModel.studentHoldingOffersDTOMap.put(studentHoldingOffersDTO.getStudentId(), studentHoldingOffersDTOList);
    }

    private RecruitmeException isValidateStudentHoldingOffersDetails(StudentHoldingOffersDTO studentHoldingOffersDTO) {
        RecruitmeException recruitmeException = new RecruitmeException();
        if (studentHoldingOffersDTO.getStudentId() == null) {
            recruitmeException.addException(ErrorFor.STUDENT_ID_ERR.getErrorFor(), StudentError.STUDENT_ID_REQUIRED.getStudentError());
        } else if (StudentModel.studentIdMap.containsKey(studentHoldingOffersDTO.getStudentId()) == false) {
            recruitmeException.addException(ErrorFor.STUDENT_ID_ERR.getErrorFor(), StudentError.INVALID_STUDENT_ID.getStudentError());
        }

        if (studentHoldingOffersDTO.getCompany() == null || studentHoldingOffersDTO.getCompany().length() == 0) {
            recruitmeException.addException(ErrorFor.COMPANY_ERR.getErrorFor(), StudentHoldingOffersError.COMPANY_NAME_REQUIRED.getStudentHoldingOfferErr());
        }

        if (studentHoldingOffersDTO.getJobType() == null) {
            recruitmeException.addException(ErrorFor.JOB_TYPE_ERR.getErrorFor(), StudentHoldingOffersError.JOB_TYPE_REQUIRED.getStudentHoldingOfferErr());
        }

        if (studentHoldingOffersDTO.getSalary() == null) {
            recruitmeException.addException(ErrorFor.SALARY_ERR.getErrorFor(), StudentHoldingOffersError.SALARY_REQUIRED.getStudentHoldingOfferErr());
        } else if (studentHoldingOffersDTO.getSalary().compareTo(BigDecimal.ZERO) == -1) {
            recruitmeException.addException(ErrorFor.SALARY_ERR.getErrorFor(), StudentHoldingOffersError.INVALID_SALARY.getStudentHoldingOfferErr());
        }

        if (recruitmeException.getExceptions().size() > 0) return recruitmeException;
        return null;
    }

    /**
     * Delete by student id.
     *
     * @param studId the student id
     */
    @Override
    public void deleteByStudentId(Long studId) {
        List<StudentHoldingOffers> studentHoldingOffersList1 = this.studentHoldingOffersRepository.findAllByStudentId(studId);
        this.studentHoldingOffersRepository.deleteAll(studentHoldingOffersList1);

        //to again populate DS after deletion
        List<StudentHoldingOffers> studentHoldingOffersList = this.studentHoldingOffersRepository.findAll();
        studentHoldingOffersList.forEach((studentHoldingOffers) -> {
            Long studentId = studentHoldingOffers.getStudent().getId();
            ModelMapper mapper = new ModelMapper();
            if (StudentHoldingOffersModel.studentHoldingOffersDTOMap.containsKey(studentId) == false) {
                List<StudentHoldingOffersDTO> studentHoldingOffersDTOList = new LinkedList<>();
                StudentHoldingOffersDTO studentHoldingOffersDTO = mapper.map(studentHoldingOffers, StudentHoldingOffersDTO.class);
                studentHoldingOffersDTOList.add(studentHoldingOffersDTO);
                StudentHoldingOffersModel.studentHoldingOffersDTOMap.put(studentId, studentHoldingOffersDTOList);
            } else {
                List<StudentHoldingOffersDTO> studentHoldingOffersDTOList = StudentHoldingOffersModel.studentHoldingOffersDTOMap.get(studentId);
                StudentHoldingOffersDTO studentHoldingOffersDTO = mapper.map(studentHoldingOffers, StudentHoldingOffersDTO.class);
                studentHoldingOffersDTOList.add(studentHoldingOffersDTO);
                StudentHoldingOffersModel.studentHoldingOffersDTOMap.put(studentId, studentHoldingOffersDTOList);
            }
        });
    }


    /**
     * Find all student holding offers.
     *
     * @return the list
     */
    @Override
    public List<StudentHoldingOfferResponseDTO> findAll() {
        Map<Long, List<StudentHoldingOffersDTO>> studentHoldingOfferDTOMap=StudentHoldingOffersModel.studentHoldingOffersDTOMap;
        List<StudentHoldingOfferResponseDTO> studentHoldingOfferResponseDTOList=new LinkedList<>();
        for(var entry:studentHoldingOfferDTOMap.entrySet()) {
            Student student=StudentModel.studentIdMap.get(entry.getKey());
            List<StudentHoldingOffersDTO> studentHoldingOffersDTOList=entry.getValue();
            StudentHoldingOfferResponseDTO studentHoldingOfferResponseDTO=new StudentHoldingOfferResponseDTO();
            studentHoldingOfferResponseDTO.setStudent(student);
            studentHoldingOfferResponseDTO.setStudentHoldingOffersDTOList(studentHoldingOffersDTOList);
            studentHoldingOfferResponseDTOList.add(studentHoldingOfferResponseDTO);
        }
        return studentHoldingOfferResponseDTOList;
    }

    /**
     * Find student holding offers by student id.
     *
     * @param studentId the student id
     * @return the list
     */
    @Override
    public List<StudentHoldingOffersDTO> findByStudentId(Long studentId) {
        if(StudentHoldingOffersModel.studentHoldingOffersDTOMap.containsKey(studentId)==false) return new LinkedList<StudentHoldingOffersDTO>();
        return StudentHoldingOffersModel.studentHoldingOffersDTOMap.get(studentId);
    }

}