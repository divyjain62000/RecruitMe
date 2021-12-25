package com.recruitme.service.domain.student.impl;

import com.recruitme.domain.*;
import com.recruitme.enums.Gender;
import com.recruitme.enums.error.*;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.model.StudentModel;
import com.recruitme.repository.*;
import com.recruitme.service.domain.address.AddressService;
import com.recruitme.service.domain.education.EducationService;
import com.recruitme.service.domain.interview.experience.InterviewExperienceService;
import com.recruitme.service.domain.parents.ParentsService;
import com.recruitme.service.domain.student.StudentService;
import com.recruitme.service.domain.student.dto.StudentDTO;
import com.recruitme.service.domain.student.dto.StudentResponseDTO;
import com.recruitme.service.domain.student.dto.StudentSearchDTO;
import com.recruitme.service.domain.student.holding.offers.StudentHoldingOffersService;
import com.recruitme.service.domain.student.registration.drive.StudentRegisteredInDriveService;
import com.recruitme.service.mail.EmailSenderService;
import com.recruitme.service.mail.dto.ConfirmationTokenDTO;
import com.recruitme.service.mail.dto.MailDTO;
import com.recruitme.utility.encryption.EncryptionUtility;
import com.recruitme.utility.token.TokenGenerationUtility;
import com.recruitme.utility.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

/**
 * Service implementation for managing Student
 */
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentsRepository parentsRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ParentsService parentsService;

    @Autowired
    private StudentRegisteredInDriveService studentRegisteredInDriveService;

    @Autowired
    private StudentHoldingOffersService studentHoldingOffersService;

    @Autowired
    private InterviewExperienceService interviewExperienceService;


    /**
     * To add student
     *
     * @param studentDTO
     * @throws RecruitmeException
     */
    @Override
    public void save(StudentDTO studentDTO) throws RecruitmeException {
        log.debug("Student DTO: {}", studentDTO);
        RecruitmeException recruitmeException = new RecruitmeException();
        if (studentDTO == null) {
            recruitmeException.addException(ErrorFor.NEED_ALL_DETAIL_ERR.getErrorFor(), StudentError.STUDENT_DETAIL_REQUIRED.getStudentError());
            throw recruitmeException;
        }
        if (studentDTO.getId() == null || studentDTO.getId() != 0) studentDTO.setId(Long.valueOf(0));

        String password = studentDTO.getPassword();
        String passwordKey = EncryptionUtility.getKey();
        String encryptedPassword = EncryptionUtility.encrypt(password, passwordKey);
        studentDTO.setPassword(encryptedPassword);
        studentDTO.setPasswordKey(passwordKey);
        recruitmeException = isValidStudentDetails(studentDTO);

        if (recruitmeException == null) recruitmeException = new RecruitmeException();

        if (StudentModel.studentEnrollmentMap.containsKey(studentDTO.getEnrollmentNumber())) {
            recruitmeException.addException(ErrorFor.ENROLLEMENT_ERR.getErrorFor(), StudentError.ENROLLMENT_EXISTS.getStudentError());
        }
        if (StudentModel.studentMouReferenceIdMap.containsKey(studentDTO.getMouCompanyReferenceId())) {
            recruitmeException.addException(ErrorFor.MOU_COMAPANY_REFERENCE_ID_ERR.getErrorFor(), StudentError.MOU_COMAPANY_REFERENCE_ID_EXISTS.getStudentError());
        }
        if (StudentModel.studentPrimaryEmailMap.containsKey(studentDTO.getPrimaryEmail())) {
            recruitmeException.addException(ErrorFor.PRIMARY_EMAIL_ERR.getErrorFor(), CommonError.EMAIL_ID_EXISTS.getCommonError());
        }
        if (StudentModel.studentPrimaryMobileNumberMap.containsKey(studentDTO.getPrimaryMobileNumber())) {
            recruitmeException.addException(ErrorFor.PRIMARY_MOBILE_ERR.getErrorFor(), CommonError.MOBILE_NUMBER_EXISTS.getCommonError());
        }
        if (recruitmeException.getExceptions().size() > 0) {
            System.out.println("recruitmeException,recruitmeExceptionrecruitmeExceptionrecruitmeException");
            throw recruitmeException;
        }

        ModelMapper modelMapper = new ModelMapper();
        Student student = modelMapper.map(studentDTO, Student.class);
        student = this.studentRepository.save(student);
        studentDTO = modelMapper.map(student, StudentDTO.class);

        //Code to insert data in model
        StudentModel.studentList.add(student);
        StudentModel.studentIdMap.put(student.getId(), student);
        StudentModel.studentEnrollmentMap.put(student.getEnrollmentNumber(), student);
        StudentModel.studentPrimaryEmailMap.put(student.getPrimaryEmail(), student);
        StudentModel.studentPrimaryMobileNumberMap.put(student.getPrimaryMobileNumber(), student);
        StudentModel.studentMouReferenceIdMap.put(student.getMouCompanyReferenceId(), student);
        log.debug("Data inserted in data model");

        //gnerating token for email verification
        ConfirmationTokenDTO confirmationTokenDTO = TokenGenerationUtility.createTokenForStudent(studentDTO);
        ConfirmationToken confirmationToken = modelMapper.map(confirmationTokenDTO, ConfirmationToken.class);
        confirmationToken = this.confirmationTokenRepository.save(confirmationToken);
        confirmationTokenDTO = modelMapper.map(confirmationToken, ConfirmationTokenDTO.class);
        log.debug("Comfirmation token");

        MailDTO mailDTO = new MailDTO();
        mailDTO.setSubject("Verification Email");
        Map<String, Object> emailMap = new HashMap<>();
        emailMap.put("name", studentDTO.getName());
        mailDTO.setEmailMap(emailMap);
        List<String> studentEmails = new ArrayList<>();
        studentEmails.add(studentDTO.getPrimaryEmail());
        mailDTO.setTo(studentEmails);
        emailSenderService.sendVerificationEmail(mailDTO, confirmationTokenDTO);
    }


    /**
     * Method to edit student details
     *
     * @param studentDTO
     * @throws RecruitmeException
     */
    @Override
    public void edit(StudentDTO studentDTO) throws RecruitmeException {
        log.debug("Student DTO: {}", studentDTO);
        RecruitmeException recruitmeException = new RecruitmeException();
        if (studentDTO == null) {
            recruitmeException.addException(ErrorFor.NEED_ALL_DETAIL_ERR.getErrorFor(), StudentError.STUDENT_DETAIL_REQUIRED.getStudentError());
            throw recruitmeException;
        }
        recruitmeException = isValidStudentDetails(studentDTO);

        if (recruitmeException == null) recruitmeException = new RecruitmeException();

        if (recruitmeException.getExceptions().size() > 0) {
            System.out.println("recruitmeException,recruitmeExceptionrecruitmeExceptionrecruitmeException");
            throw recruitmeException;
        }

        ModelMapper modelMapper = new ModelMapper();
        Student student = modelMapper.map(studentDTO, Student.class);
        student = this.studentRepository.save(student);
        studentDTO = modelMapper.map(student, StudentDTO.class);

        //Code to insert data in model
        int studentListSize = StudentModel.studentList.size();
        for (int i = 0; i < studentListSize; i++) {
            Student tmpStudent = StudentModel.studentList.get(i);
            if (tmpStudent.getId() == student.getId()) {
                StudentModel.studentList.set(i, student);
                break;
            }
        }
        StudentModel.studentIdMap.put(student.getId(), student);
        StudentModel.studentEnrollmentMap.put(student.getEnrollmentNumber(), student);
        StudentModel.studentPrimaryEmailMap.put(student.getPrimaryEmail(), student);
        StudentModel.studentPrimaryMobileNumberMap.put(student.getPrimaryMobileNumber(), student);
        StudentModel.studentMouReferenceIdMap.put(student.getMouCompanyReferenceId(), student);

        log.debug("Data inserted in data model");

    }


    /**
     * To get all student or to filter student
     *
     * @param studentSearchDTO
     * @param pageable
     * @return Page of type {@link StudentDTO}
     */
    @Override
    public Page<StudentDTO> search(StudentSearchDTO studentSearchDTO, Pageable pageable) {
        Set<StudentDTO> studentDTOSet = new HashSet<>();
        ModelMapper mapper = new ModelMapper();
        if (studentSearchDTO.getId() != null) {
            log.debug("StudentModel.studentIdMap.containsKey(studentSearchDTO.getId()): {}", StudentModel.studentIdMap.containsKey(studentSearchDTO.getId()));
            if (StudentModel.studentIdMap.containsKey(studentSearchDTO.getId())) {
                Student student = StudentModel.studentIdMap.get(studentSearchDTO.getId());
                StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                studentDTOSet.add(studentDTO);
            }
        }
        boolean flag = false;
        if (studentSearchDTO.getName() != null || studentSearchDTO.getBranchId() != null || studentSearchDTO.getProgramId() != null || studentSearchDTO.getPrimaryEmail() != null || studentSearchDTO.getPrimaryMobileNumber() != null) {
            flag = true;
            StudentModel.studentList.forEach((student) -> {
                String name = student.getName();
                String ugBranchId = student.getEducation().getUgBranch().getId().toString();
                String ugProgramId = student.getEducation().getUgProgram().getId().toString();
                String pgBranchId = student.getEducation().getPgBranch().getId().toString();
                String pgProgramId = student.getEducation().getPgProgram().getId().toString();
                String primaryMobileNumber = student.getPrimaryMobileNumber();
                String primaryEmail = student.getPrimaryEmail().toLowerCase();
                String enrollmentNumber = student.getEnrollmentNumber().toLowerCase();
                String secondaryEmail = null;
                if (student.getSecondaryEmail() != null) secondaryEmail = student.getSecondaryEmail().toLowerCase();
                String secondaryMobileNumber = null;
                if (student.getSecondaryMobileNumber() != null)
                    secondaryMobileNumber = student.getSecondaryMobileNumber();
                if (studentSearchDTO.getName() != null && (name.toLowerCase().contains(studentSearchDTO.getName().toLowerCase()) || (studentSearchDTO.getSearch() != null && name.toLowerCase().contains(studentSearchDTO.getSearch())))) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }
                if ((studentSearchDTO.getBranchId() != null && ugBranchId != null) && (ugBranchId.equals(studentSearchDTO.getBranchId().toString()) || (studentSearchDTO.getSearch() != null && ugBranchId.equals(studentSearchDTO.getSearch())))) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }
                if ((studentSearchDTO.getProgramId() != null && ugProgramId != null) && (ugProgramId.equals(studentSearchDTO.getProgramId().toString()) || (studentSearchDTO.getSearch() != null && ugProgramId.equals(studentSearchDTO.getSearch())))) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }

                if ((studentSearchDTO.getBranchId() != null && pgBranchId != null) && (pgBranchId.equals(studentSearchDTO.getBranchId().toString()) || (studentSearchDTO.getSearch() != null && pgBranchId.equals(studentSearchDTO.getSearch())))) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }
                if ((studentSearchDTO.getProgramId() != null && pgProgramId != null) && (pgProgramId.equals(studentSearchDTO.getProgramId().toString()) || (studentSearchDTO.getSearch() != null && pgProgramId.equals(studentSearchDTO.getSearch())))) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }


                if (studentSearchDTO.getPrimaryMobileNumber() != null && (primaryMobileNumber.contains(studentSearchDTO.getPrimaryMobileNumber()) || (studentSearchDTO.getSearch() != null && primaryMobileNumber.contains(studentSearchDTO.getSearch().toLowerCase())))) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }
                if (studentSearchDTO.getPrimaryEmail() != null && (primaryEmail.contains(studentSearchDTO.getPrimaryEmail().toLowerCase()) || (studentSearchDTO.getSearch() != null && primaryEmail.contains(studentSearchDTO.getSearch().toLowerCase())))) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }
                if (studentSearchDTO.getEnrollmentNumber() != null && (enrollmentNumber.contains(studentSearchDTO.getEnrollmentNumber().toLowerCase()) || (studentSearchDTO.getSearch() != null && enrollmentNumber.contains(studentSearchDTO.getSearch().toLowerCase())))) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }
                if ((secondaryEmail != null && studentSearchDTO.getSecondaryEmail() != null) && (secondaryEmail.contains(studentSearchDTO.getSecondaryEmail().toLowerCase()) || (secondaryEmail.contains(studentSearchDTO.getSearch().toLowerCase())))) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }
                if ((secondaryMobileNumber != null && studentSearchDTO.getSecondaryMobileNumber() != null) && (secondaryMobileNumber.contains(studentSearchDTO.getSecondaryMobileNumber()) || (secondaryMobileNumber.contains(studentSearchDTO.getSearch())))) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }
                if (studentSearchDTO.getIsPlaceForFulltime() != null && student.getIsPlaceForFulltime() == studentSearchDTO.getIsPlaceForFulltime()) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }
                if (studentSearchDTO.getIsPlaceForInternship() != null && student.getIsPlaceForInternship() == studentSearchDTO.getIsPlaceForInternship()) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }
                if (studentSearchDTO.getIsBlacklisted() != null && student.getIsBlacklisted() == studentSearchDTO.getIsBlacklisted()) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }
                if (studentSearchDTO.getIsAnyCriminalCase() != null && student.getIsAnyCriminalCase() == studentSearchDTO.getIsAnyCriminalCase()) {
                    StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                    studentDTOSet.add(studentDTO);
                }
            });
        }
        List<StudentDTO> studentDTOList = new ArrayList<>(studentDTOSet);
        long studentDTOListSize = studentDTOList.size();
        if (studentDTOListSize == 0 && flag == false) {
            StudentModel.studentList.forEach((student) -> {
                StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                studentDTOList.add(studentDTO);
            });
        }

        return new PageImpl<StudentDTO>(studentDTOList, pageable, studentDTOListSize);
    }


    /**
     * To validate student details
     *
     * @param studentDTO
     * @return {@link RecruitmeException}
     */
    private RecruitmeException isValidStudentDetails(StudentDTO studentDTO) {
        RecruitmeException recruitmeException = recruitmeException = new RecruitmeException();
        if (studentDTO.getEnrollmentNumber() == null || studentDTO.getEnrollmentNumber().length() == 0) {
            recruitmeException.addException(ErrorFor.ENROLLEMENT_ERR.getErrorFor(), StudentError.ENROLLMENT_REQUIRED.getStudentError());
        }
        if (studentDTO.getPasswordKey() == null || studentDTO.getPasswordKey().length() == 0) {
            recruitmeException.addException(ErrorFor.PASSWORD_ERR.getErrorFor(), CommonError.PASSWORD_REQUIRED.getCommonError());
        }
        if (studentDTO.getPassword() == null || studentDTO.getPassword().length() == 0) {
            recruitmeException.addException(ErrorFor.PASSWORD_ERR.getErrorFor(), CommonError.PASSWORD_REQUIRED.getCommonError());
        }
        if (studentDTO.getName() == null || studentDTO.getName().length() == 0) {
            recruitmeException.addException(ErrorFor.NAME_ERR.getErrorFor(), CommonError.NAME_REQUIRED.getCommonError());
        }
        if (studentDTO.getPrimaryEmail() == null || studentDTO.getPrimaryEmail().length() == 0) {
            recruitmeException.addException(ErrorFor.PRIMARY_EMAIL_ERR.getErrorFor(), CommonError.PRIMARY_EMAIL_REQUIRED.getCommonError());
        } else if (Validator.isValidEmail(studentDTO.getPrimaryEmail()) == false) {
            recruitmeException.addException(ErrorFor.PRIMARY_EMAIL_ERR.getErrorFor(), CommonError.INVALID_EMAIL_ID.getCommonError());
        }

        if (studentDTO.getSecondaryEmail() != null && Validator.isValidEmail(studentDTO.getSecondaryEmail()) == false) {
            recruitmeException.addException(ErrorFor.SECONDARY_EMAIL_ERR.getErrorFor(), CommonError.INVALID_EMAIL_ID.getCommonError());
        }

        if (studentDTO.getPrimaryMobileNumber() == null || studentDTO.getPrimaryMobileNumber().length() <= 0) {
            recruitmeException.addException(ErrorFor.PRIMARY_MOBILE_ERR.getErrorFor(), CommonError.PRIMARY_MOBILE_NUMBER_REQUIRED.getCommonError());
        } else if (Validator.isValidMobileNumber(studentDTO.getPrimaryMobileNumber()) == false) {
            recruitmeException.addException(ErrorFor.PRIMARY_MOBILE_ERR.getErrorFor(), CommonError.INVALID_MOBILE_NUMBER.getCommonError());
        }

        if (studentDTO.getSecondaryMobileNumber() != null && studentDTO.getSecondaryMobileNumber().length() > 0 && Validator.isValidMobileNumber(studentDTO.getSecondaryMobileNumber()) == false) {
            recruitmeException.addException(ErrorFor.SECONDARY_MOBILE_ERR.getErrorFor(), CommonError.INVALID_MOBILE_NUMBER.getCommonError());
        }

        if (studentDTO.getAddressId() == null || studentDTO.getAddressId() == 0) {
            recruitmeException.addException(ErrorFor.ADDRESS_ERR.getErrorFor(), AddressError.ADDRESS_DETAIL_REQURIED.getAddressError());
        } else {
            Optional<Address> address = this.addressRepository.findById(studentDTO.getAddressId());
            if (!address.isPresent()) {
                recruitmeException.addException(ErrorFor.ADDRESS_ERR.getErrorFor(), AddressError.ADDRESS_DETAIL_REQURIED.getAddressError());
            }
        }

        if (studentDTO.getParentsId() == null || studentDTO.getParentsId() == 0) {
            recruitmeException.addException(ErrorFor.PARENTS_ERR.getErrorFor(), CommonError.PARENTS_DETAIL_REQUIRED.getCommonError());
        } else {
            Optional<Parents> parents = this.parentsRepository.findById(studentDTO.getParentsId());
            if (!parents.isPresent()) {
                recruitmeException.addException(ErrorFor.PARENTS_ERR.getErrorFor(), CommonError.PARENTS_DETAIL_REQUIRED.getCommonError());
            }
        }

        if (studentDTO.getIsPlaceForFulltime() == null) {
            studentDTO.setIsPlaceForFulltime(false);
        }

        if (studentDTO.getIsPlaceForInternship() == null) {
            studentDTO.setIsPlaceForInternship(false);
        }

        if (studentDTO.getIsBlacklisted() == null) {
            studentDTO.setIsBlacklisted(false);
        }

        if (studentDTO.getIsValidPrimaryEmail() == null) {
            studentDTO.setIsValidPrimaryEmail(false);
        }

        if (studentDTO.getIsValidSecondaryEmail() == null) {
            studentDTO.setIsValidSecondaryEmail(false);
        }

        if (studentDTO.getIsValidPrimaryMobileNumber() == null) {
            studentDTO.setIsValidPrimaryMobileNumber(false);
        }

        if (studentDTO.getIsValidSecondaryMobileNumber() == null) {
            studentDTO.setIsValidSecondaryMobileNumber(false);
        }

        if (studentDTO.getGender() == null) {
            recruitmeException.addException(ErrorFor.GENDER_ERR.getErrorFor(), CommonError.GENDER_REQUIRED.getCommonError());
        } else if (!(studentDTO.getGender() == Gender.MALE.getGender() || studentDTO.getGender() == Gender.FEMALE.getGender() || studentDTO.getGender() == Gender.OTHER.getGender())) {
            recruitmeException.addException(ErrorFor.GENDER_ERR.getErrorFor(), CommonError.GENDER_REQUIRED.getCommonError());
        }

        if (studentDTO.getIsIndian() == null) {
            recruitmeException.addException(ErrorFor.IS_INDIAN_ERR.getErrorFor(), StudentError.NATIONALITY_REQUIRED.getStudentError());
        }

        if (studentDTO.getIsAnyCriminalCase() == null) {
            recruitmeException.addException(ErrorFor.IS_ANY_CRIMINAL_CASE_ERR.getErrorFor(), StudentError.CRIMINAL_CASE_DETAIL_REQUIRED.getStudentError());
        } else if (studentDTO.getCriminalCaseDescription() != null && studentDTO.getCriminalCaseDescription().length() == 0 && studentDTO.getIsAnyCriminalCase() == true) {
            recruitmeException.addException(ErrorFor.IS_ANY_CRIMINAL_CASE_ERR.getErrorFor(), StudentError.CRIMINAL_CASE_DETAIL_REQUIRED.getStudentError());
        }

        if (studentDTO.getIsDisability() == null) {
            recruitmeException.addException(ErrorFor.IS_DISABILITY_ERR.getErrorFor(), StudentError.DISABILITY_DETAIL_REQUIRED.getStudentError());
        } else if (studentDTO.getDisability() != null && studentDTO.getDisability().length() == 0 && studentDTO.getIsDisability() == true) {
            recruitmeException.addException(ErrorFor.IS_DISABILITY_ERR.getErrorFor(), StudentError.DISABILITY_DETAIL_REQUIRED.getStudentError());
        }

        if (studentDTO.getMouCompanyReferenceId() == null || studentDTO.getMouCompanyReferenceId().length() == 0) {
            recruitmeException.addException(ErrorFor.MOU_COMAPANY_REFERENCE_ID_ERR.getErrorFor(), StudentError.MOU_COMAPANY_REFERENCE_ID_REQUIRED.getStudentError());
        }

        if (studentDTO.getEducationId() == null) {
            recruitmeException.addException(ErrorFor.EDUCATION_ERR.getErrorFor(), EducationError.EDUCATION_DETAIL_REQUIRED.getEducationError());
        } else {
            Optional<Education> education = this.educationRepository.findById(studentDTO.getEducationId());
            if (!education.isPresent()) {
                recruitmeException.addException(ErrorFor.EDUCATION_ERR.getErrorFor(), EducationError.EDUCATION_DETAIL_REQUIRED.getEducationError());
            }
        }

        if (recruitmeException.getExceptions().size() > 0) return recruitmeException;
        return null;
    }

    @Override
    public StudentResponseDTO findById(Long id) {
        Student student = StudentModel.studentIdMap.get(id);
        ModelMapper mapper = new ModelMapper();
        StudentResponseDTO studentResponseDTO = mapper.map(student, StudentResponseDTO.class);
        return studentResponseDTO;
    }

    @Override
    public Page<StudentResponseDTO> findAll(Pageable pageable) {
        List<StudentResponseDTO> studentResponseDTOList = new LinkedList<>();
        ModelMapper mapper = new ModelMapper();
        StudentModel.studentList.forEach((student) -> {
            StudentResponseDTO studentResponseDTO = mapper.map(student, StudentResponseDTO.class);
            studentResponseDTOList.add(studentResponseDTO);
        });
        int studentResponseDTOListSize = studentResponseDTOList.size();
        return new PageImpl<StudentResponseDTO>(studentResponseDTOList, pageable, studentResponseDTOListSize);
    }

    @Override
    public void blacklistStudent(Long id, String blacklistReason) throws RecruitmeException {
        RecruitmeException recruitmeException = new RecruitmeException();
        if (id == null) {
            recruitmeException.addException(ErrorFor.STUDENT_ID_ERR.getErrorFor(), StudentError.STUDENT_ID_REQUIRED.getStudentError());
        } else if (StudentModel.studentIdMap.containsKey(id) == false) {
            recruitmeException.addException(ErrorFor.STUDENT_ID_ERR.getErrorFor(), StudentError.INVALID_STUDENT_ID.getStudentError());
        }
        if (blacklistReason == null || blacklistReason.length() == 0) {
            recruitmeException.addException(ErrorFor.BLACKLIST_REASON_ERR.getErrorFor(), StudentError.BLACKLIST_REASON_REQUIRED.getStudentError());
        }
        if (recruitmeException.getExceptions().size() > 0) throw recruitmeException;
        Student student = StudentModel.studentIdMap.get(id);
        student.setIsBlacklisted(true);
        student.setReasonForBlacklist(blacklistReason);
        ModelMapper mapper = new ModelMapper();
        StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
        this.edit(studentDTO);
    }

    @Override
    public void unblacklistStudent(Long id) throws RecruitmeException {
        RecruitmeException recruitmeException = new RecruitmeException();
        if (id == null) {
            recruitmeException.addException(ErrorFor.STUDENT_ID_ERR.getErrorFor(), StudentError.STUDENT_ID_REQUIRED.getStudentError());
        } else if (StudentModel.studentIdMap.containsKey(id) == false) {
            recruitmeException.addException(ErrorFor.STUDENT_ID_ERR.getErrorFor(), StudentError.INVALID_STUDENT_ID.getStudentError());
        }
        if (recruitmeException.getExceptions().size() > 0) throw recruitmeException;
        Student student = StudentModel.studentIdMap.get(id);
        student.setIsBlacklisted(false);
        student.setReasonForBlacklist(null);
        ModelMapper mapper = new ModelMapper();
        StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
        this.edit(studentDTO);
    }

    @Override
    public void delete(Long id) {
        Student student=StudentModel.studentIdMap.get(id);
        List<ConfirmationToken> confirmationTokenList=this.confirmationTokenRepository.findAllByStudentId(student.getId());
        if(confirmationTokenList!=null && confirmationTokenList.size()>0) this.confirmationTokenRepository.deleteAll(confirmationTokenList);
        this.studentRepository.delete(student);
        this.parentsService.delete(student.getParents());
        this.educationService.delete(student.getEducation());
        this.addressService.delete(student.getAddress());
        this.interviewExperienceService.deleteByStudentId(student.getId());
        this.studentRegisteredInDriveService.deleteByStudentId(student.getId());
        this.studentHoldingOffersService.deleteByStudentId(student.getId());

        
        //removing data from model
        StudentModel.studentIdMap.remove(student.getId());
        StudentModel.studentPrimaryEmailMap.remove(student.getPrimaryEmail());
        StudentModel.studentEnrollmentMap.remove(student.getEnrollmentNumber());
        StudentModel.studentPrimaryMobileNumberMap.remove(student.getPrimaryMobileNumber());
        StudentModel.studentMouReferenceIdMap.remove(student.getMouCompanyReferenceId());
        StudentModel.studentList.remove(student);
    }

}
