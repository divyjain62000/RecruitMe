package com.recruitme.service.domain.student.registration.drive.impl;

import com.recruitme.domain.*;
import com.recruitme.enums.AppConstants;
import com.recruitme.enums.Gender;
import com.recruitme.enums.MarksType;
import com.recruitme.enums.error.*;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.model.BranchModel;
import com.recruitme.model.DriveModel;
import com.recruitme.model.StudentModel;
import com.recruitme.model.StudentRegisteredInDriveModel;
import com.recruitme.repository.StudentRegisteredInDriveRepository;
import com.recruitme.service.domain.student.registration.drive.StudentRegisteredInDriveService;
import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveDTO;
import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveResponseDTO;
import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveSearchDTO;
import com.recruitme.service.file.excel.ExcelFileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Service implementation for managing students registered in drive
 */
@Service
@Slf4j
public class StudentRegisteredInDriveServiceImpl implements StudentRegisteredInDriveService {

    @Autowired
    private StudentRegisteredInDriveRepository studentRegisteredInDriveRepository;

    @Autowired
    private ExcelFileService excelFileService;

    @Override
    public void save(StudentRegisteredInDriveDTO studentRegisteredInDriveDTO) throws RecruitmeException {
        RecruitmeException recruitmeException = new RecruitmeException();

        if (studentRegisteredInDriveDTO == null) {
            recruitmeException.addException(ErrorFor.NEED_ALL_DETAIL_ERR.getErrorFor(), CommonError.NEED_ALL_DETAIL.getCommonError());
            throw recruitmeException;
        }

        recruitmeException = isValidateStudentRegisteredInDriveDetails(studentRegisteredInDriveDTO);
        if (recruitmeException != null) throw recruitmeException;

        recruitmeException = isStudentEligibleToRegistredinDrive(studentRegisteredInDriveDTO);
        if (recruitmeException != null) throw recruitmeException;

        ModelMapper mapper = new ModelMapper();
        StudentRegisteredInDrive studentRegisteredInDrive = mapper.map(studentRegisteredInDriveDTO, StudentRegisteredInDrive.class);
        studentRegisteredInDrive = this.studentRegisteredInDriveRepository.save(studentRegisteredInDrive);

        //code to insert data in model
        List<Long> students = StudentRegisteredInDriveModel.studentRegisteredInDriveMap.get(studentRegisteredInDriveDTO.getDriveId());
        if (students != null) students.add(studentRegisteredInDriveDTO.getStudentId());
        else {
            students = new LinkedList<>();
            students.add(studentRegisteredInDriveDTO.getStudentId());
        }
        StudentRegisteredInDriveModel.studentRegisteredInDriveMap.put(studentRegisteredInDriveDTO.getDriveId(), students);

    }

    private RecruitmeException isValidateStudentRegisteredInDriveDetails(StudentRegisteredInDriveDTO studentRegisteredInDriveDTO) {
        RecruitmeException recruitmeException = new RecruitmeException();
        if (studentRegisteredInDriveDTO.getDriveId() == null) {
            recruitmeException.addException(ErrorFor.DRIVE_ID_ERR.getErrorFor(), DriveError.REQUIRED_DRIVE_ID.getDriveError());
        } else if (DriveModel.driveIdMap.containsKey(studentRegisteredInDriveDTO.getDriveId()) == false) {
            recruitmeException.addException(ErrorFor.DRIVE_ID_ERR.getErrorFor(), DriveError.INVALID_DRIVE_ID.getDriveError());
        }

        if (studentRegisteredInDriveDTO.getStudentId() == null) {
            recruitmeException.addException(ErrorFor.STUDENT_ID_ERR.getErrorFor(), StudentError.STUDENT_ID_REQUIRED.getStudentError());
        } else if (StudentModel.studentIdMap.containsKey(studentRegisteredInDriveDTO.getStudentId()) == false) {
            recruitmeException.addException(ErrorFor.STUDENT_ID_ERR.getErrorFor(), StudentError.INVALID_STUDENT_ID.getStudentError());
        }

        //Search in StudentRegisteredInDriveMap for particular drive if student already registered then his/her name present in list
        if (StudentRegisteredInDriveModel.studentRegisteredInDriveMap.containsKey(studentRegisteredInDriveDTO.getDriveId())) {
            if (StudentRegisteredInDriveModel.studentRegisteredInDriveMap.get(studentRegisteredInDriveDTO.getDriveId()).contains(studentRegisteredInDriveDTO.getStudentId())) {
                recruitmeException.addException(ErrorFor.STUDENT_ID_ERR.name(), StudentRegisteredInDriveError.STUDETNT_ALREADY_REGISTERD.getStudentRegisteredInDrive());
            }
        }

        if (recruitmeException.getExceptions().size() > 0) return recruitmeException;
        return null;
    }

    private RecruitmeException isStudentEligibleToRegistredinDrive(StudentRegisteredInDriveDTO studentRegisteredInDriveDTO) {
        RecruitmeException recruitmeException = new RecruitmeException();
        Drive drive = DriveModel.driveIdMap.get(studentRegisteredInDriveDTO.getDriveId());
        Student student = StudentModel.studentIdMap.get(studentRegisteredInDriveDTO.getStudentId());
        Education education = student.getEducation();

        if (student.getIsBlacklisted() != null && student.getIsBlacklisted()) {
            recruitmeException.addException(ErrorFor.APPLY_DRIVE_ERR.getErrorFor(), "You cannot apply to this drive beacuse you are " +
                    "blacklisted\n" +
                    "Reason For Blacklist:-\n" +
                    "" + student.getReasonForBlacklist());
        }

        if (drive.getOnlyForPgStudents() && student.getEducation().getPgBranch() == null) {
            recruitmeException.addException(ErrorFor.APPLY_DRIVE_ERR.getErrorFor(), StudentRegisteredInDriveError.ONLY_FOR_PG_STUDENTS.getStudentRegisteredInDrive());
        }

        if (drive.getOnlyForUgStudents() && student.getEducation().getPgBranch() != null) {
            recruitmeException.addException(ErrorFor.APPLY_DRIVE_ERR.getErrorFor(), StudentRegisteredInDriveError.ONLY_FOR_UG_STUDENTS.getStudentRegisteredInDrive());
        }

        if ((drive.getForBothUgPgStudents() || drive.getOnlyForUgStudents()) && (student.getEducation().getPgProgram() == null && drive.getValidPrograms().contains(student.getEducation().getUgProgram().getId()) == false)) {
            recruitmeException.addException(ErrorFor.UG_PROGRAM_ERR.getErrorFor(), StudentRegisteredInDriveError.PROGRAM_NOT_ALLOW.getStudentRegisteredInDrive());
        }

        if ((drive.getForBothUgPgStudents() || drive.getOnlyForPgStudents()) && (student.getEducation().getPgProgram() != null && drive.getValidPrograms().contains(student.getEducation().getPgProgram().getId()) == false)) {
            recruitmeException.addException(ErrorFor.PG_PROGRAM_ERR.getErrorFor(), StudentRegisteredInDriveError.PROGRAM_NOT_ALLOW.getStudentRegisteredInDrive());
        }

        if ((drive.getForBothUgPgStudents() || drive.getOnlyForUgStudents()) && (student.getEducation().getPgBranch() == null && drive.getValidBranches().contains(student.getEducation().getUgBranch().getId()) == false)) {
            recruitmeException.addException(ErrorFor.UG_BRANCH_ERR.getErrorFor(), StudentRegisteredInDriveError.BRANCH_NOT_ALLOW.getStudentRegisteredInDrive());
        }

        if ((drive.getForBothUgPgStudents() || drive.getOnlyForPgStudents()) && drive.getValidBranches().contains(student.getEducation().getPgBranch().getId()) == false) {
            recruitmeException.addException(ErrorFor.PG_BRANCH_ERR.getErrorFor(), StudentRegisteredInDriveError.BRANCH_NOT_ALLOW.getStudentRegisteredInDrive());
        }

        if (education.getSeniorSecondarySchoolMarks() == null) {
            recruitmeException.addException(ErrorFor.REQUIRED_10TH_MARKS_ERR.getErrorFor(), StudentRegisteredInDriveError.REQUIRED_10th_PASS.getStudentRegisteredInDrive());
        } else if (education.getSeniorSecondarySchoolMarksType().equals(MarksType.CGPA.getMarksType()) && education.getSeniorSecondarySchoolMarks() < drive.getRequired10thCGPA()) {
            recruitmeException.addException(ErrorFor.REQUIRED_10TH_MARKS_ERR.name(), StudentRegisteredInDriveError.BELOW_REQUIRED_MARKS_10th.getStudentRegisteredInDrive());
        } else if (education.getSeniorSecondarySchoolMarksType().equals(MarksType.PERCENTAGE.getMarksType()) && education.getSeniorSecondarySchoolMarks() < drive.getRequired10thPercentage()) {
            recruitmeException.addException(ErrorFor.REQUIRED_10TH_MARKS_ERR.name(), StudentRegisteredInDriveError.BELOW_REQUIRED_MARKS_10th.getStudentRegisteredInDrive());
        }

        if (education.getHighSchoolMarks() == null) {
            recruitmeException.addException(ErrorFor.REQUIRED_12TH_MARKS_ERR.getErrorFor(), StudentRegisteredInDriveError.REQUIRED_12th_PASS.getStudentRegisteredInDrive());
        } else if (education.getHighSchoolMarksType().equals(MarksType.CGPA.getMarksType()) && (drive.getRequired12thCGPA() != null && education.getHighSchoolMarks() < drive.getRequired12thCGPA())) {
            recruitmeException.addException(ErrorFor.REQUIRED_12TH_MARKS_ERR.name(), StudentRegisteredInDriveError.BELOW_REQUIRED_MARKS_12th.getStudentRegisteredInDrive());
        } else if (education.getHighSchoolMarksType().equals(MarksType.PERCENTAGE.getMarksType()) && education.getHighSchoolMarks() < drive.getRequired10thPercentage()) {
            recruitmeException.addException(ErrorFor.REQUIRED_12TH_MARKS_ERR.name(), StudentRegisteredInDriveError.BELOW_REQUIRED_MARKS_12th.getStudentRegisteredInDrive());
        }


        if (education.getUgMarks() == null) {
            recruitmeException.addException(ErrorFor.REQUIRED_UG_MARKS_ERR.getErrorFor(), StudentRegisteredInDriveError.UG_MARKS_DETAIL_REQUIRED.getStudentRegisteredInDrive());
        } else if (education.getUgMarksType().equals(MarksType.CGPA.getMarksType()) && education.getUgMarks() < drive.getRequiredUGCGPA()) {
            recruitmeException.addException(ErrorFor.REQUIRED_UG_MARKS_ERR.name(), StudentRegisteredInDriveError.BELOW_REQUIRED_MARKS_UG.getStudentRegisteredInDrive());
        } else if (education.getUgMarksType().equals(MarksType.PERCENTAGE.getMarksType()) && education.getUgMarks() < drive.getRequiredUGPercentage()) {
            recruitmeException.addException(ErrorFor.REQUIRED_UG_MARKS_ERR.name(), StudentRegisteredInDriveError.BELOW_REQUIRED_MARKS_UG.getStudentRegisteredInDrive());
        }

        if ((drive.getForBothUgPgStudents() || drive.getOnlyForPgStudents()) && education.getPgBranch() != null) {
            if (education.getPgMarks() == null) {
                recruitmeException.addException(ErrorFor.REQUIRED_PG_MARKS_ERR.getErrorFor(), StudentRegisteredInDriveError.PG_MARKS_DETAIL_REQUIRED.getStudentRegisteredInDrive());
            } else if (education.getPgMarksType().equals(MarksType.CGPA.getMarksType()) && education.getPgMarks() < drive.getRequiredPGCGPA()) {
                recruitmeException.addException(ErrorFor.REQUIRED_PG_MARKS_ERR.name(), StudentRegisteredInDriveError.BELOW_REQUIRED_MARKS_PG.getStudentRegisteredInDrive());
            } else if (education.getPgMarksType().equals(MarksType.PERCENTAGE.getMarksType()) && education.getUgMarks() < drive.getRequiredPGPercentage()) {
                recruitmeException.addException(ErrorFor.REQUIRED_PG_MARKS_ERR.name(), StudentRegisteredInDriveError.BELOW_REQUIRED_MARKS_PG.getStudentRegisteredInDrive());
            }
        }

        if (drive.getIsOnlyForGirls() == true && student.getGender().equals(Gender.MALE.name())) {
            recruitmeException.addException(ErrorFor.DRIVE_FOR_ERR.name(), StudentRegisteredInDriveError.ONLY_FOR_GIRLS.getStudentRegisteredInDrive());
        }

        if (drive.getIsOnlyForBoys() == true && student.getGender().equals(Gender.FEMALE.name())) {
            recruitmeException.addException(ErrorFor.DRIVE_FOR_ERR.name(), StudentRegisteredInDriveError.ONLY_FOR_BOYS.getStudentRegisteredInDrive());
        }

        if (education.getNumberOfCurrentBacklog() > drive.getMinimumBacklogsAllowed()) {
            recruitmeException.addException(ErrorFor.NUMBER_OF_CURRENT_BACKLOG_ERR.getErrorFor(), StudentRegisteredInDriveError.MORE_MINIMUM_BACKLOGS_THAN_ALLOWED.getStudentRegisteredInDrive());
        }

        if (recruitmeException.getExceptions().size() > 0) return recruitmeException;
        return null;
    }

    @Override
    public List<StudentRegisteredInDriveResponseDTO> findAll(StudentRegisteredInDriveSearchDTO studentRegisteredInDriveSearchDTO) throws RecruitmeException {
        validateStudentRegisteredInDriveSearchDTO(studentRegisteredInDriveSearchDTO);

        List<Long> studentIdList = StudentRegisteredInDriveModel.studentRegisteredInDriveMap.get(studentRegisteredInDriveSearchDTO.getDriveId());
        List<StudentRegisteredInDriveResponseDTO> studentRegisteredInDriveResponseDTOList = new LinkedList<>();
        if (studentIdList != null) {
            studentIdList.forEach((studentId) -> {
                Student student = StudentModel.studentIdMap.get(studentId);
                Education education = student.getEducation();
                StudentRegisteredInDriveResponseDTO studentRegisteredInDriveResponseDTO = this.filterData(student, education, studentRegisteredInDriveSearchDTO);
                studentRegisteredInDriveResponseDTOList.add(studentRegisteredInDriveResponseDTO);
            });
        }
        return studentRegisteredInDriveResponseDTOList;
    }

    @Override
    public Page<StudentRegisteredInDriveResponseDTO> search(StudentRegisteredInDriveSearchDTO studentRegisteredInDriveSearchDTO, Pageable pageable) throws RecruitmeException {
        List<StudentRegisteredInDriveResponseDTO> studentRegisteredInDriveResponseDTOList = this.findAll(studentRegisteredInDriveSearchDTO);
        int studentRegisteredInDriveResponseDTOListSize = studentRegisteredInDriveResponseDTOList.size();
        return new PageImpl<StudentRegisteredInDriveResponseDTO>(studentRegisteredInDriveResponseDTOList, pageable, studentRegisteredInDriveResponseDTOListSize);
    }

    private void validateStudentRegisteredInDriveSearchDTO(StudentRegisteredInDriveSearchDTO studentRegisteredInDriveSearchDTO) throws RecruitmeException {
        RecruitmeException recruitmeException = new RecruitmeException();
        if (studentRegisteredInDriveSearchDTO == null) {
            recruitmeException.addException(ErrorFor.DRIVE_ID_ERR.getErrorFor(), DriveError.DRIVE_NAME_REQUIRED.getDriveError());
            throw recruitmeException;
        }
        if (studentRegisteredInDriveSearchDTO.getDriveId() == null) {
            recruitmeException.addException(ErrorFor.DRIVE_ID_ERR.getErrorFor(), DriveError.DRIVE_NAME_REQUIRED.getDriveError());
        } else if (DriveModel.driveIdMap.containsKey(studentRegisteredInDriveSearchDTO.getDriveId()) == false) {
            recruitmeException.addException(ErrorFor.DRIVE_ID_ERR.getErrorFor(), DriveError.INVALID_DRIVE_ID.getDriveError());
        }
        if (studentRegisteredInDriveSearchDTO.getBranchId() == null) {
            recruitmeException.addException(ErrorFor.BRANCH_ERR.getErrorFor(), BranchError.BRANCH_NAME_REQUIRED.getBranchError());
        } else if (BranchModel.branchIdMap.containsKey(studentRegisteredInDriveSearchDTO.getBranchId()) == false) {
            recruitmeException.addException(ErrorFor.BRANCH_ERR.getErrorFor(), BranchError.BRANCH_NOT_EXISTS.getBranchError());
        }

        if (studentRegisteredInDriveSearchDTO.getSearchForUgStudents() == null && studentRegisteredInDriveSearchDTO.getSearchForPgStudents() == null) {
            recruitmeException.addException(ErrorFor.STUDENT_REGISTERED_DRIVE_SEARCH_CRITERIA_ERR.getErrorFor(), StudentRegisteredInDriveError.SEARCH_CRETERIA_NOT_SELECTED.getStudentRegisteredInDrive());
        }

        if (recruitmeException.getExceptions().size() > 0) throw recruitmeException;

    }

    private StudentRegisteredInDriveResponseDTO filterData(Student student, Education education, StudentRegisteredInDriveSearchDTO studentRegisteredInDriveSearchDTO) {
        StudentRegisteredInDriveResponseDTO studentRegisteredInDriveResponseDTO = new StudentRegisteredInDriveResponseDTO();
        boolean flag = false;
        if (studentRegisteredInDriveSearchDTO.getSearchForUgStudents() && education.getUgBranch() != null && education.getUgBranch().getId() == studentRegisteredInDriveSearchDTO.getBranchId()) {
            flag = true;
            studentRegisteredInDriveResponseDTO.setId(this.getStudentId(student, education));
            studentRegisteredInDriveResponseDTO.setStudentName(this.getStudentName(student, education));
            studentRegisteredInDriveResponseDTO.setEnrollmentNumber(this.getStudentEnrollment(student, education));
            studentRegisteredInDriveResponseDTO.setSeniorSecondaryMarks(this.getSeniorSecondarySchoolMarks(student, education));
            studentRegisteredInDriveResponseDTO.setHighSchoolMarks(this.getHighSchoolMarks(student, education));
            studentRegisteredInDriveResponseDTO.setUgMarks(this.getUgMarks(student, education));
            studentRegisteredInDriveResponseDTO.setPrimaryEmail(this.getPrimaryEmail(student, education));
            studentRegisteredInDriveResponseDTO.setSecondaryEmail(this.getSecondaryEmail(student, education));
            studentRegisteredInDriveResponseDTO.setPrimaryMobileNumber(this.getPrimaryMobileNumber(student, education));
            studentRegisteredInDriveResponseDTO.setSecondaryMobileNumber(this.getSecondaryMobileNumber(student, education));
            studentRegisteredInDriveResponseDTO.setBranch(this.getUgBranchCode(student, education));
            studentRegisteredInDriveResponseDTO.setProgram(this.getUgProgramCode(student, education));
        }
        if (studentRegisteredInDriveSearchDTO.getSearchForPgStudents() && education.getPgBranch() != null && education.getPgBranch().getId() == studentRegisteredInDriveSearchDTO.getBranchId()) {
            flag = true;
            studentRegisteredInDriveResponseDTO.setStudentName(this.getStudentName(student, education));
            studentRegisteredInDriveResponseDTO.setSeniorSecondaryMarks(this.getSeniorSecondarySchoolMarks(student, education));
            studentRegisteredInDriveResponseDTO.setHighSchoolMarks(this.getHighSchoolMarks(student, education));
            studentRegisteredInDriveResponseDTO.setUgMarks(this.getUgMarks(student, education));
            studentRegisteredInDriveResponseDTO.setPgMarks(this.getPgMarks(student, education));
            studentRegisteredInDriveResponseDTO.setPrimaryEmail(this.getPrimaryEmail(student, education));
            studentRegisteredInDriveResponseDTO.setSecondaryEmail(this.getSecondaryEmail(student, education));
            studentRegisteredInDriveResponseDTO.setPrimaryMobileNumber(this.getPrimaryMobileNumber(student, education));
            studentRegisteredInDriveResponseDTO.setSecondaryMobileNumber(this.getSecondaryMobileNumber(student, education));
            studentRegisteredInDriveResponseDTO.setBranch(this.getUgBranchCode(student, education));
            studentRegisteredInDriveResponseDTO.setProgram(this.getUgProgramCode(student, education));
            studentRegisteredInDriveResponseDTO.setBranch(this.getPgBranchCode(student, education));
            studentRegisteredInDriveResponseDTO.setProgram(this.getPgProgramCode(student, education));
        }
        return studentRegisteredInDriveResponseDTO;
    }

    private Long getStudentId(Student student, Education education) {
        if (student.getId() != null) return student.getId();
        else return null;
    }

    private String getStudentName(Student student, Education education) {
        if (student.getName() != null) return student.getName();
        else return AppConstants.DASH.getAppConstants();
    }

    private String getStudentEnrollment(Student student, Education education) {
        if (student.getEnrollmentNumber() != null) return student.getEnrollmentNumber();
        else return AppConstants.DASH.getAppConstants();
    }

    private String getSeniorSecondarySchoolMarks(Student student, Education education) {
        if (education.getSeniorSecondarySchoolMarksType() != null && education.getSeniorSecondarySchoolMarksType().equals(MarksType.CGPA.getMarksType()) && education.getSeniorSecondarySchoolMarks() != null) {
            return (education.getSeniorSecondarySchoolMarks() + " " + MarksType.CGPA.name());
        } else {
            return (education.getSeniorSecondarySchoolMarks() + " %");
        }
    }

    private String getHighSchoolMarks(Student student, Education education) {
        if (education.getHighSchoolMarksType() != null && education.getHighSchoolMarksType().equals(MarksType.CGPA.getMarksType()) && education.getHighSchoolMarks() != null) {
            return (education.getHighSchoolMarks() + " " + MarksType.CGPA.name());
        } else {
            return (education.getHighSchoolMarks() + " %");
        }
    }

    private String getUgMarks(Student student, Education education) {
        if (education.getUgMarksType() != null && education.getUgMarksType().equals(MarksType.CGPA.getMarksType()) && education.getUgMarks() != null) {
            return (education.getUgMarks() + " " + MarksType.CGPA.name());
        } else {
            return (education.getUgMarks() + " %");
        }
    }

    private String getPgMarks(Student student, Education education) {
        if (education.getPgMarksType() != null && education.getPgMarksType().equals(MarksType.CGPA.getMarksType()) && education.getPgMarks() != null) {
            return (education.getPgMarks() + " " + MarksType.CGPA.name());
        } else {
            return (education.getPgMarks() + " %");
        }
    }

    private String getPrimaryEmail(Student student, Education education) {
        if (student.getPrimaryEmail() != null) {
            return student.getPrimaryEmail();
        } else {
            return AppConstants.DASH.getAppConstants();
        }
    }

    private String getSecondaryEmail(Student student, Education education) {
        if (student.getSecondaryEmail() != null) {
            return student.getSecondaryEmail();
        } else {
            return AppConstants.DASH.getAppConstants();
        }
    }

    private String getPrimaryMobileNumber(Student student, Education education) {
        if (student.getPrimaryMobileNumber() != null) {
            return student.getPrimaryMobileNumber();
        } else {
            Parents parents = student.getParents();
            if (parents != null) {
                if (parents.getMobileNumber() != null) {
                    return parents.getMobileNumber();
                } else {
                    return AppConstants.DASH.getAppConstants();
                }
            } else {
                return AppConstants.DASH.getAppConstants();
            }
        }
    }

    private String getSecondaryMobileNumber(Student student, Education education) {
        if (student.getSecondaryMobileNumber() != null) {
            return student.getSecondaryMobileNumber();
        } else {
            Parents parents = student.getParents();
            if (parents != null) {
                if (parents.getMobileNumber() != null) {
                    return parents.getMobileNumber();
                } else {
                    return AppConstants.DASH.getAppConstants();
                }
            } else {
                return AppConstants.DASH.getAppConstants();
            }
        }
    }

    private String getUgBranchCode(Student student, Education education) {
        if (education.getUgBranch() != null && education.getUgBranch().getName() != null) {
            return education.getUgBranch().getBranchCode();
        } else {
            return AppConstants.DASH.getAppConstants();
        }
    }

    private String getUgProgramCode(Student student, Education education) {
        if (education.getUgProgram() != null && education.getUgProgram().getName() != null) {
            return education.getUgProgram().getProgramCode();
        } else {
            return AppConstants.DASH.getAppConstants();
        }
    }


    private String getPgBranchCode(Student student, Education education) {
        if (education.getPgBranch() != null && education.getPgBranch().getName() != null) {
            return education.getPgBranch().getBranchCode();
        } else {
            return AppConstants.DASH.getAppConstants();
        }
    }

    private String getPgProgramCode(Student student, Education education) {
        if (education.getPgProgram() != null && education.getPgProgram().getName() != null) {
            return education.getPgProgram().getProgramCode();
        } else {
            return AppConstants.DASH.getAppConstants();
        }
    }

    public ByteArrayInputStream exportStudentRegisterdInDriveExcelFile(StudentRegisteredInDriveSearchDTO studentRegisteredInDriveSearchDTO) throws RecruitmeException {
        List<StudentRegisteredInDriveResponseDTO> data = this.findAll(studentRegisteredInDriveSearchDTO);
        List<String> header = new LinkedList<>();
        header.add("Name");
        header.add("Primary Email");
        header.add("Secondary Email");
        header.add("Primary Mobile Number");
        header.add("Secondary Mobile Number");
        header.add("Branch");
        header.add("Program");
        header.add("10th Marks");
        header.add("12th Marks");
        header.add("UG Marks");
        int columns = 10;
        if (studentRegisteredInDriveSearchDTO.getSearchForPgStudents() == true) {
            columns++;
            header.add("PG Marks");
        }
        String fileName = BranchModel.branchIdMap.get(studentRegisteredInDriveSearchDTO.getBranchId()).getBranchCode()
                + AppConstants.UNDERSCORE.getAppConstants() + DriveModel.driveIdMap.get(studentRegisteredInDriveSearchDTO.getDriveId()).getCompany()
                + AppConstants.UNDERSCORE.getAppConstants() + DriveModel.driveIdMap.get(studentRegisteredInDriveSearchDTO.getDriveId()).getDriveName()
                + AppConstants.UNDERSCORE.getAppConstants() + "StudentList";
        ByteArrayInputStream file = this.excelFileService.exportStudentRegisteredInDriveExcelFile(header, data, columns, fileName);
        return file;
    }

    public void deleteByStudentId(Long studentId) {
        List<StudentRegisteredInDrive> studentRegisteredInDrives=this.studentRegisteredInDriveRepository.findAllByStudentId(studentId);
        this.studentRegisteredInDriveRepository.deleteAll(studentRegisteredInDrives);

        //to again populate DS after deleting student
        List<StudentRegisteredInDrive> studentRegisteredInDriveList=this.studentRegisteredInDriveRepository.findAll();
        studentRegisteredInDriveList.forEach((registeredStudent)->{
            Integer driveId=registeredStudent.getDrive().getId();
            Long studId=registeredStudent.getStudent().getId();
            if(StudentRegisteredInDriveModel.studentRegisteredInDriveMap.containsKey(driveId)==false) {
                List<Long> studentIdList=new LinkedList<>();
                studentIdList.add(studId);
                StudentRegisteredInDriveModel.studentRegisteredInDriveMap.put(driveId,studentIdList);
            }else {
                List<Long> studentIdList=StudentRegisteredInDriveModel.studentRegisteredInDriveMap.get(driveId);
                studentIdList.add(studId);
                StudentRegisteredInDriveModel.studentRegisteredInDriveMap.put(driveId,studentIdList);
            }
        });
    }

}

