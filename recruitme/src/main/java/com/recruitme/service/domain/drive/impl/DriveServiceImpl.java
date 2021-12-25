package com.recruitme.service.domain.drive.impl;

import com.recruitme.domain.Drive;
import com.recruitme.enums.AppConstants;
import com.recruitme.enums.error.CommonError;
import com.recruitme.enums.error.DriveError;
import com.recruitme.enums.error.ErrorFor;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.model.*;
import com.recruitme.repository.DriveRepository;
import com.recruitme.service.domain.branch.dto.BranchDTO;
import com.recruitme.service.domain.drive.DriveService;
import com.recruitme.service.domain.drive.dto.DriveDTO;
import com.recruitme.service.domain.drive.dto.DriveResponseDTO;
import com.recruitme.service.domain.drive.dto.DriveSearchDTO;
import com.recruitme.service.domain.program.dto.ProgramDTO;
import com.recruitme.service.mail.EmailSenderService;
import com.recruitme.service.mail.dto.MailDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Service implementation for managing Drive
 */
@Service
@Slf4j
public class DriveServiceImpl implements DriveService {

    @Autowired
    private DriveRepository driveRepository;
    @Autowired
    private EmailSenderService emailSenderService;

    /**
     * Method to save {@link Drive}
     *
     * @param driveDTO
     * @throws RuntimeException
     */
    @Override
    public void save(DriveDTO driveDTO) throws RecruitmeException {
        if(driveDTO.getId()==null || driveDTO.getId()==0) driveDTO.setId(0);
        RecruitmeException recruitmeException=isValidDrive(driveDTO);
        if(recruitmeException!=null) throw recruitmeException;

        ModelMapper modelMapper=new ModelMapper();
        Drive drive=modelMapper.map(driveDTO, Drive.class);
        drive=this.driveRepository.save(drive);
        driveDTO=modelMapper.map(drive,DriveDTO.class);

        //code to insert data in model
        DriveModel.driveList.add(drive);
        DriveModel.driveIdMap.put(drive.getId(),drive);

        MailDTO mailDTO=new MailDTO();
        List<String> studentEmailList=new LinkedList<>();
        StudentModel.studentList.forEach((s)->{
            if(s.getPrimaryEmail()!=null) studentEmailList.add(s.getPrimaryEmail());
            if(s.getSecondaryEmail()!=null) studentEmailList.add(s.getSecondaryEmail());
        });
        mailDTO.setTo(studentEmailList);
        mailDTO.setSubject(driveDTO.getDriveName());
        mailDTO.setBody("");
        emailSenderService.sendDriveNotificationEmail(mailDTO,driveDTO);
    }

    /**
     * Method to edit {@link Drive}
     * @param driveDTO
     * @throws RecruitmeException
     */
    @Override
    public void edit(DriveDTO driveDTO) throws RecruitmeException {
        RecruitmeException recruitmeException=isValidDrive(driveDTO);

        if(recruitmeException==null) recruitmeException=new RecruitmeException();
        if(driveDTO.getId()==null) {
              recruitmeException.addException(ErrorFor.ID_ERR.getErrorFor(), CommonError.ID_REQUIRED.getCommonError());
        }else {
            if(DriveModel.driveIdMap.containsKey(driveDTO.getId())==false) {
                recruitmeException.addException(ErrorFor.ID_ERR.getErrorFor(), CommonError.INVALID_ID.getCommonError());
            }
        }
        if(recruitmeException!=null && recruitmeException.getExceptions().size()>0) throw  recruitmeException;
        ModelMapper modelMapper=new ModelMapper();
        Drive drive=modelMapper.map(driveDTO, Drive.class);
        drive=this.driveRepository.save(drive);
        driveDTO=modelMapper.map(drive,DriveDTO.class);

        //code to insert data in model
        DriveModel.driveList.add(drive);

        MailDTO mailDTO=new MailDTO();
        List<String> studentEmailList=new LinkedList<>();
        StudentModel.studentList.forEach((s)->{
            if(s.getPrimaryEmail()!=null) studentEmailList.add(s.getPrimaryEmail());
            if(s.getSecondaryEmail()!=null) studentEmailList.add(s.getSecondaryEmail());
        });
        mailDTO.setTo(studentEmailList);
        mailDTO.setSubject(driveDTO.getDriveName()+" Update!");
        mailDTO.setBody("");
        emailSenderService.sendDriveNotificationEmail(mailDTO,driveDTO);
    }

    /**
     * To get all drive or to filter drive
     * @param driveSearchDTO
     * @param pageable
     * @return Page of type {@link DriveDTO}
     */
    @Override
    public Page<DriveDTO> search(DriveSearchDTO driveSearchDTO, Pageable pageable) {
        Set<DriveDTO> driveDTOSet=new HashSet<>();
        ModelMapper mapper=new ModelMapper();
        if(driveSearchDTO.getId()!=null) {
            if(DriveModel.driveIdMap.containsKey(driveSearchDTO.getId())) {
                Drive drive=DriveModel.driveIdMap.get(driveSearchDTO.getId());
                DriveDTO driveDTO=mapper.map(drive,DriveDTO.class);
                driveDTOSet.add(driveDTO);
            }
        }
        List<Drive> driveDTOS=this.driveRepository.findAll();
        boolean flag=false;
        if(driveSearchDTO.getName()!=null || driveSearchDTO.getCompany()!=null || driveSearchDTO.getIsOnlyForGirls()!=null || driveSearchDTO.getIsOnlyForBoys()!=null || driveSearchDTO.getLastDateToApply()!=null || driveSearchDTO.getIsFullTimeVaccancy()!=null || driveSearchDTO.getIsInternshipVaccancy()!=null) {
            flag=true;
            driveDTOS.forEach((drive)->{
                String driveName=drive.getDriveName();
                String lastDateToApply=drive.getLastDateToApply().toString();
                String company=drive.getCompany();
                if(driveSearchDTO.getName()!=null && (driveName.toLowerCase().contains(driveSearchDTO.getName().toLowerCase()) || (driveSearchDTO.getSearch()!=null && driveName.toLowerCase().contains(driveSearchDTO.getSearch())))) {
                    DriveDTO driveDTO=mapper.map(drive,DriveDTO.class);
                    driveDTOSet.add(driveDTO);
                }
                if(driveSearchDTO.getCompany()!=null && (company.toLowerCase().contains(driveSearchDTO.getCompany().toLowerCase()) || (driveSearchDTO.getSearch()!=null && company.toLowerCase().contains(driveSearchDTO.getSearch())))) {
                    DriveDTO driveDTO=mapper.map(drive,DriveDTO.class);
                    driveDTOSet.add(driveDTO);
                }
                if(driveSearchDTO.getIsOnlyForGirls()!=null && drive.getIsOnlyForGirls()==driveSearchDTO.getIsOnlyForGirls()) {
                    DriveDTO driveDTO=mapper.map(drive,DriveDTO.class);
                    driveDTOSet.add(driveDTO);
                }
                if(driveSearchDTO.getIsOnlyForBoys()!=null && drive.getIsOnlyForBoys()==driveSearchDTO.getIsOnlyForBoys()) {
                    DriveDTO driveDTO=mapper.map(drive,DriveDTO.class);
                    driveDTOSet.add(driveDTO);
                }
                if(driveSearchDTO.getLastDateToApply()!=null && (lastDateToApply.toLowerCase().equals(driveSearchDTO.getLastDateToApply().toString().toLowerCase()) || (driveSearchDTO.getSearch()!=null && lastDateToApply.toLowerCase().equals(driveSearchDTO.getSearch())))) {
                    DriveDTO driveDTO=mapper.map(drive,DriveDTO.class);
                    driveDTOSet.add(driveDTO);
                }
                if(driveSearchDTO.getIsFullTimeVaccancy()!=null && drive.getIsFullTimeVaccancy()==driveSearchDTO.getIsFullTimeVaccancy()) {
                    DriveDTO driveDTO=mapper.map(drive,DriveDTO.class);
                    driveDTOSet.add(driveDTO);
                }
                if(driveSearchDTO.getIsInternshipVaccancy()!=null && drive.getIsInternshipVaccancy()==driveSearchDTO.getIsInternshipVaccancy()) {
                    DriveDTO driveDTO=mapper.map(drive,DriveDTO.class);
                    driveDTOSet.add(driveDTO);
                }
            });
        }
        List<DriveDTO> driveDTOList=new ArrayList<>(driveDTOSet);
        long driveDTOListSize=driveDTOList.size();
        if(driveDTOListSize==0 && flag==false) {
            driveDTOS.forEach((drive)->{
                DriveDTO driveDTO=mapper.map(drive,DriveDTO.class);
                driveDTOList.add(driveDTO);
            });
        }
        return new PageImpl<DriveDTO>(driveDTOList,pageable,driveDTOListSize);
    }

    @Cacheable
    @Override
    public List<DriveResponseDTO> findAll() {
        List<DriveResponseDTO> driveResponseDTOList=new LinkedList<>();
        DriveModel.driveList.forEach((drive)->{
            DriveResponseDTO driveResponseDTO=new DriveResponseDTO();
            driveResponseDTO.setId(drive.getId());
            driveResponseDTO.setDriveName(drive.getDriveName());
            driveResponseDTO.setCompany(drive.getCompany());
            String salary=getSalary(drive);
            driveResponseDTO.setSalary(salary);
            String validProgram=getValidProgram(drive.getValidPrograms());
            driveResponseDTO.setValidPrograms(validProgram);
            String validBranch=getValidBranch(drive.getValidBranches());
            driveResponseDTO.setValidBranches(validBranch);
            driveResponseDTO.setRequired10thCGPA(drive.getRequired10thCGPA());
            driveResponseDTO.setRequired10thPercentage(drive.getRequired10thPercentage());
            driveResponseDTO.setRequired12thCGPA(drive.getRequired12thCGPA());
            driveResponseDTO.setRequired12thPercentage(drive.getRequired12thPercentage());
            driveResponseDTO.setRequiredUGCGPA(drive.getRequiredUGCGPA());
            driveResponseDTO.setRequiredUGPercentage(drive.getRequiredUGPercentage());
            driveResponseDTO.setRequiredPGCGPA(drive.getRequiredPGCGPA());
            driveResponseDTO.setRequiredPGPercentage(drive.getRequiredPGPercentage());
            driveResponseDTO.setMinimumBacklogsAllowed(drive.getMinimumBacklogsAllowed());
            driveResponseDTO.setDriveForGender(drive.getIsOnlyForBoys()?"Boys":(drive.getIsOnlyForGirls()?"Girls":"Both Boys and Girls"));
            driveResponseDTO.setLastDateToApply(drive.getLastDateToApply());
            driveResponseDTO.setVacancyType((drive.getIsFullTimeVaccancy() && drive.getIsInternshipVaccancy())?"Fulltime & Internship":(drive.getIsFullTimeVaccancy()?"Fulltime":"Internship"));
            driveResponseDTO.setDriveForGraduation(drive.getOnlyForPgStudents()?"PG":(drive.getOnlyForUgStudents()?"UG":"Both PG and UG"));
            String ugPassoutYearAllow=getUgPassoutYearAllow(drive.getUgPassoutYearAllow());
            driveResponseDTO.setUgPassoutYearAllow(ugPassoutYearAllow);
            String pgPassoutYearAllow=getPgPassoutYearAllow(drive.getPgPassoutYearAllow());
            driveResponseDTO.setPgPassoutYearAllow(pgPassoutYearAllow);
            driveResponseDTO.setMinGapAllowInEducation(drive.getMinGapAllowInEducation());
            driveResponseDTO.setOtherDelatils(drive.getOtherDelatils());
            driveResponseDTOList.add(driveResponseDTO);
        });
        return  driveResponseDTOList;
    }


    private String getSalary(Drive drive) {
        if(drive.getMaxPackage()!=drive.getMinPackage())  return drive.getMinPackage()+" "+AppConstants.DASH.getAppConstants()+" "+drive.getMaxPackage();
        return String.valueOf(drive.getMinPackage());
    }


    private String getValidProgram(List<Integer> validPrograms) {
        String validProgram="";
        int validProgramsSize=validPrograms.size();
        for(int i=0;i<validProgramsSize;i++) {
            ProgramDTO programDTO=ProgramModel.programIdMap.get(validPrograms.get(i));
            validProgram+=programDTO.getProgramCode();
            if(i!=validProgramsSize-1) validProgram+=",";
        }
        return validProgram;
    }


    private String getValidBranch(List<Integer> validBranches) {
        String validBranch="";
        int validBranchesSize=validBranches.size();
        for(int i=0;i<validBranchesSize;i++) {
            BranchDTO branchDTO=BranchModel.branchIdMap.get(validBranches.get(i));
            validBranch+=branchDTO.getBranchCode();
            if(i!=validBranchesSize-1) validBranch+=",";
        }
        return validBranch;
    }

    private String getUgPassoutYearAllow(List<Integer> ugPassoutYearAllow) {
        String passoutYearAllowed="";
        int size=ugPassoutYearAllow.size();
        for(int i=0;i<size;i++) {
            passoutYearAllowed+=ugPassoutYearAllow.get(i)+"";
            if(i!=size-1) passoutYearAllowed+=",";
        }
        return passoutYearAllowed;
    }

    private String getPgPassoutYearAllow(List<Integer> pgPassoutYearAllow) {
        if(pgPassoutYearAllow==null) return AppConstants.DASH.getAppConstants();
        String passoutYearAllowed="";
        int size=pgPassoutYearAllow.size();
        for(int i=0;i<size;i++) {
            passoutYearAllowed+=pgPassoutYearAllow.get(i)+"";
            if(i!=size-1) passoutYearAllowed+=",";
        }
        return passoutYearAllowed;
    }


    private RecruitmeException isValidDrive(DriveDTO driveDTO) {
        RecruitmeException recruitmeException=new RecruitmeException();

        if(driveDTO.getDriveName()==null || driveDTO.getDriveName().length()==0) {
            recruitmeException.addException(ErrorFor.DRIVE_NAME_ERR.getErrorFor(),DriveError.DRIVE_NAME_REQUIRED.getDriveError());
        }

        if(driveDTO.getCompany()==null || driveDTO.getCompany().length()==0) {
            recruitmeException.addException(ErrorFor.COMPANY_ERR.getErrorFor(), DriveError.COMPANY_REQUIRED.getDriveError());
        }

        if(driveDTO.getMinPackage()==null) {
            recruitmeException.addException(ErrorFor.MIN_PACKAGE_ERR.getErrorFor(), DriveError.MIN_PACKAGE_REQUIRED.getDriveError());
        }
        else if(driveDTO.getMinPackage().compareTo(new BigDecimal(0))<=0) {
            recruitmeException.addException(ErrorFor.MIN_PACKAGE_ERR.getErrorFor(), DriveError.INVALID_MIN_PACKAGE.getDriveError());
        }

        if(driveDTO.getMaxPackage()==null) {
            recruitmeException.addException(ErrorFor.MAX_PACKAGE_ERR.getErrorFor(), DriveError.MAX_PACKAGE_REQUIRED.getDriveError());
        }
        else if(driveDTO.getMaxPackage().compareTo(new BigDecimal(0))<=0) {
            recruitmeException.addException(ErrorFor.MAX_PACKAGE_ERR.getErrorFor(), DriveError.INVALID_MAX_PACKAGE.getDriveError());
        }

        //min package > max package
        if((driveDTO.getMinPackage()!=null && driveDTO.getMaxPackage()!=null) && driveDTO.getMaxPackage().compareTo(driveDTO.getMinPackage())<0) {
            recruitmeException.addException(ErrorFor.MIN_PACKAGE_ERR.getErrorFor(), DriveError.MIN_PACKAGE_LESS_THAN_MAX_PACKAGE.getDriveError());
            recruitmeException.addException(ErrorFor.MAX_PACKAGE_ERR.getErrorFor(), DriveError.MIN_PACKAGE_LESS_THAN_MAX_PACKAGE.getDriveError());
        }

        List<Integer> validProgramList=driveDTO.getValidPrograms();
        if(validProgramList==null || validProgramList.size()<=0) {
            recruitmeException.addException(ErrorFor.VALID_PROGRAM_LIST_ERR.getErrorFor(),DriveError.VALID_PROGRAM_LIST_REQUIRED.getDriveError());
        }
        else {
            for(Integer programId: validProgramList){
                if(ProgramModel.programIdMap.containsKey(programId)==false) {
                    recruitmeException.addException(ErrorFor.VALID_PROGRAM_LIST_ERR.getErrorFor(), DriveError.INVALID_VALID_PROGRAM_LIST.getDriveError());
                    break;
                }
            }
        }

        List<Integer> validBranchList=driveDTO.getValidBranches();
        if(validBranchList==null || validBranchList.size()<=0) {
            recruitmeException.addException(ErrorFor.VALID_BRANCH_LIST_ERR.getErrorFor(), DriveError.VALID_BRANCH_LIST_REQUIRED.getDriveError());
        }
        else {
            for(Integer branchId: validBranchList) {
                if(BranchModel.branchIdMap.containsKey(branchId)==false) {
                    recruitmeException.addException(ErrorFor.VALID_BRANCH_LIST_ERR.getErrorFor(), DriveError.INVALID_VALID_BRANCH_LIST.getDriveError());
                }
            }
        }

        //10th marks validation
        if(driveDTO.getRequired10thCGPA()==null && driveDTO.getRequired10thPercentage()==null) {
            recruitmeException.addException(ErrorFor.REQUIRED_10TH_CGPA_ERR.getErrorFor(),DriveError.REQUIRED_10TH_CGPA.getDriveError());
            recruitmeException.addException(ErrorFor.REQUIRED_10TH_PERCENTAGE_ERR.getErrorFor(),DriveError.REQUIRED_10TH_PERCENTAGE.getDriveError());
        }else if(driveDTO.getRequired10thCGPA()==null && driveDTO.getRequired10thPercentage()!=null) {
            recruitmeException.addException(ErrorFor.REQUIRED_10TH_CGPA_ERR.getErrorFor(),DriveError.REQUIRED_10TH_CGPA.getDriveError());
        }else if(driveDTO.getRequired10thCGPA()!=null && driveDTO.getRequired10thPercentage()==null) {
            recruitmeException.addException(ErrorFor.REQUIRED_10TH_PERCENTAGE_ERR.getErrorFor(),DriveError.REQUIRED_10TH_PERCENTAGE.getDriveError());
        } else {
            if(driveDTO.getRequired10thCGPA()<0.00f || driveDTO.getRequired10thCGPA()>10.00f) {
                recruitmeException.addException(ErrorFor.REQUIRED_10TH_CGPA_ERR.getErrorFor(),DriveError.INVALID_MARKS.getDriveError());
            }
            if(driveDTO.getRequired10thPercentage()<0.00f || driveDTO.getRequired10thPercentage()>100.00f) {
                recruitmeException.addException(ErrorFor.REQUIRED_10TH_PERCENTAGE_ERR.getErrorFor(),DriveError.INVALID_MARKS.getDriveError());
            }
        }


        //12th marks validation
        if(driveDTO.getRequired12thCGPA()==null && driveDTO.getRequired12thPercentage()==null) {
            recruitmeException.addException(ErrorFor.REQUIRED_12TH_CGPA_ERR.getErrorFor(),DriveError.REQUIRED_12TH_CGPA.getDriveError());
            recruitmeException.addException(ErrorFor.REQUIRED_12TH_PERCENTAGE_ERR.getErrorFor(),DriveError.REQUIRED_12TH_PERCENTAGE.getDriveError());
        }else if(driveDTO.getRequired12thCGPA()==null && driveDTO.getRequired12thPercentage()!=null) {
            recruitmeException.addException(ErrorFor.REQUIRED_12TH_CGPA_ERR.getErrorFor(),DriveError.REQUIRED_12TH_CGPA.getDriveError());
        }else if(driveDTO.getRequired12thCGPA()!=null && driveDTO.getRequired12thPercentage()==null) {
            recruitmeException.addException(ErrorFor.REQUIRED_12TH_PERCENTAGE_ERR.getErrorFor(),DriveError.REQUIRED_12TH_PERCENTAGE.getDriveError());
        } else {
            if(driveDTO.getRequired12thCGPA()<0.00f || driveDTO.getRequired12thCGPA()>10.00f) {
                recruitmeException.addException(ErrorFor.REQUIRED_12TH_CGPA_ERR.getErrorFor(),DriveError.INVALID_MARKS.getDriveError());
            }
            if(driveDTO.getRequired12thPercentage()<0.00f || driveDTO.getRequired12thPercentage()>100.00f) {
                recruitmeException.addException(ErrorFor.REQUIRED_12TH_PERCENTAGE_ERR.getErrorFor(),DriveError.INVALID_MARKS.getDriveError());
            }
        }


        //UG marks validation
        if(driveDTO.getRequiredUGCGPA()==null && driveDTO.getRequiredUGPercentage()==null) {
            recruitmeException.addException(ErrorFor.REQUIRED_UG_CGPA_ERR.getErrorFor(),DriveError.REQUIRED_UG_CGPA.getDriveError());
            recruitmeException.addException(ErrorFor.REQUIRED_UG_PERCENTAGE_ERR.getErrorFor(),DriveError.REQUIRED_UG_PERCENTAGE.getDriveError());
        }else if(driveDTO.getRequiredUGCGPA()==null && driveDTO.getRequiredUGPercentage()!=null) {
            recruitmeException.addException(ErrorFor.REQUIRED_UG_CGPA_ERR.getErrorFor(),DriveError.REQUIRED_UG_CGPA.getDriveError());
        }else if(driveDTO.getRequiredUGCGPA()!=null && driveDTO.getRequiredUGPercentage()==null) {
            recruitmeException.addException(ErrorFor.REQUIRED_UG_PERCENTAGE_ERR.getErrorFor(),DriveError.REQUIRED_UG_PERCENTAGE.getDriveError());
        } else {
            if(driveDTO.getRequiredUGCGPA()<0.00f || driveDTO.getRequiredUGCGPA()>10.00f) {
                recruitmeException.addException(ErrorFor.REQUIRED_UG_CGPA_ERR.getErrorFor(),DriveError.INVALID_MARKS.getDriveError());
            }
            if(driveDTO.getRequiredUGPercentage()<0.00f || driveDTO.getRequiredUGPercentage()>100.00f) {
                recruitmeException.addException(ErrorFor.REQUIRED_UG_PERCENTAGE_ERR.getErrorFor(),DriveError.INVALID_MARKS.getDriveError());
            }
        }


        //PG marks validation
        if(driveDTO.getForBothUgPgStudents()==true || driveDTO.getOnlyForPgStudents()==true) {
            if (driveDTO.getRequiredPGCGPA() == null && driveDTO.getRequiredPGPercentage() == null) {
                recruitmeException.addException(ErrorFor.REQUIRED_PG_CGPA_ERR.getErrorFor(), DriveError.REQUIRED_PG_CGPA.getDriveError());
                recruitmeException.addException(ErrorFor.REQUIRED_PG_PERCENTAGE_ERR.getErrorFor(), DriveError.REQUIRED_PG_PERCENTAGE.getDriveError());
            }
            if (driveDTO.getRequiredPGCGPA() == null && driveDTO.getRequiredPGPercentage() != null) {
                recruitmeException.addException(ErrorFor.REQUIRED_PG_CGPA_ERR.getErrorFor(), DriveError.REQUIRED_PG_CGPA.getDriveError());
            } else if (driveDTO.getRequiredPGCGPA() != null && driveDTO.getRequiredPGPercentage() == null) {
                recruitmeException.addException(ErrorFor.REQUIRED_PG_PERCENTAGE_ERR.getErrorFor(), DriveError.REQUIRED_PG_PERCENTAGE.getDriveError());
            } else if (driveDTO.getRequiredPGCGPA() != null && driveDTO.getRequiredPGPercentage() != null) {
                if (driveDTO.getRequiredPGCGPA() < 0.00f || driveDTO.getRequiredPGCGPA() > 10.00f) {
                    recruitmeException.addException(ErrorFor.REQUIRED_PG_CGPA_ERR.getErrorFor(), DriveError.INVALID_MARKS.getDriveError());
                }
                if (driveDTO.getRequiredPGPercentage() < 0.00f || driveDTO.getRequiredPGPercentage() > 100.00f) {
                    recruitmeException.addException(ErrorFor.REQUIRED_PG_PERCENTAGE_ERR.getErrorFor(), DriveError.INVALID_MARKS.getDriveError());
                }
            }
        }

        if(driveDTO.getMinimumBacklogsAllowed()==null) {
            recruitmeException.addException(ErrorFor.MINIMUM_BACKLOG_ALLOWED_ERR.getErrorFor(),DriveError.MINIMUM_BACKLOG_ALLOWED_REQUIRED.getDriveError());
        }
        else if(driveDTO.getMinimumBacklogsAllowed()<0) {
            recruitmeException.addException(ErrorFor.MINIMUM_BACKLOG_ALLOWED_ERR.getErrorFor(),DriveError.INVALID_MINIMUM_BACKLOG_ALLOWED.getDriveError());
        }

        if(driveDTO.getIsOnlyForBoys()==null || driveDTO.getIsOnlyForGirls()==null) {
            recruitmeException.addException(ErrorFor.DRIVE_FOR_GENDER_ERR.getErrorFor(), DriveError.DRIVE_FOR_GENDER_REQUIRED.getDriveError());
        }

     /*if(driveDTO.getIsOnlyForBoys()==null) {
            driveDTO.setIsOnlyForBoys(false);
        }
        if(driveDTO.getIsOnlyForGirls()==null) {
            driveDTO.setIsOnlyForGirls(false);
        }*/

        if(driveDTO.getLastDateToApply()==null) {
            recruitmeException.addException(ErrorFor.LAST_DATE_TO_APPLY_ERR.getErrorFor(), DriveError.LAST_DATE_TO_APPLY_REQUIRED.getDriveError());
        }
        else if(LocalDateTime.now().isBefore(driveDTO.getLastDateToApply())==false) {
            recruitmeException.addException(ErrorFor.LAST_DATE_TO_APPLY_ERR.getErrorFor(), DriveError.INVALID_LAST_DATE_TO_APPLY.getDriveError());
        }

        if(driveDTO.getIsFullTimeVaccancy()==null && driveDTO.getIsInternshipVaccancy()==null) {
            recruitmeException.addException(ErrorFor.VACANCY_TYPE_ERR.getErrorFor(),DriveError.VACANCY_TYPE_REQUIRED.getDriveError());
        }else if(driveDTO.getIsFullTimeVaccancy()!=null && driveDTO.getIsFullTimeVaccancy()==false && driveDTO.getIsInternshipVaccancy()!=null && driveDTO.getIsInternshipVaccancy()==false) {
            recruitmeException.addException(ErrorFor.VACANCY_TYPE_ERR.getErrorFor(),DriveError.VACANCY_TYPE_REQUIRED.getDriveError());
        }



        if(driveDTO.getOnlyForUgStudents()==null && driveDTO.getOnlyForPgStudents()==null && driveDTO.getForBothUgPgStudents()==null) {
            recruitmeException.addException(ErrorFor.DRIVE_FOR_GRADUATION_ERR.getErrorFor(), DriveError.DRIVE_FOR_GRADUATION_REQUIRED.getDriveError());
        }else {
            if(driveDTO.getOnlyForUgStudents()==null && driveDTO.getOnlyForPgStudents()==null && driveDTO.getForBothUgPgStudents()!=null) {
                driveDTO.setOnlyForUgStudents(false);
                driveDTO.setOnlyForPgStudents(false);
            }
            if(driveDTO.getOnlyForUgStudents()==null && driveDTO.getOnlyForPgStudents()!=null && driveDTO.getForBothUgPgStudents()==null) {
                driveDTO.setOnlyForUgStudents(false);
                driveDTO.setForBothUgPgStudents(false);
            }
            if(driveDTO.getOnlyForUgStudents()!=null && driveDTO.getOnlyForPgStudents()==null && driveDTO.getForBothUgPgStudents()==null) {
                driveDTO.setOnlyForPgStudents(false);
                driveDTO.setForBothUgPgStudents(false);
            }
        }

        log.debug("BOTH PG AND UG: {}",driveDTO.getForBothUgPgStudents());
        log.debug("UG: {}",driveDTO.getOnlyForUgStudents());

        if(driveDTO.getForBothUgPgStudents()==true || driveDTO.getOnlyForUgStudents()==true) {
            if (driveDTO.getUgPassoutYearAllow() == null || driveDTO.getUgPassoutYearAllow().size() <= 0) {
                recruitmeException.addException(ErrorFor.UG_PASSOUT_YEAR_ALLOW_ERR.getErrorFor(), DriveError.UG_PASSOUT_YEAR_ALLOW_REQUIRED.getDriveError());
            }
        }

        if(driveDTO.getForBothUgPgStudents()==true || driveDTO.getOnlyForPgStudents()==true){
            if(driveDTO.getPgPassoutYearAllow()==null || driveDTO.getPgPassoutYearAllow().size()<=0) {
                    recruitmeException.addException(ErrorFor.PG_PASSOUT_YEAR_ALLOW_ERR.getErrorFor(), DriveError.PG_PASSOUT_YEAR_ALLOW_REQUIRED.getDriveError());
                }
            }

        if(driveDTO.getMinGapAllowInEducation()==null) {
            recruitmeException.addException(ErrorFor.MINIMUM_GAP_ALLOWED_IN_EDUCATION_ERR.getErrorFor(), DriveError.MINIMUM_GAP_ALLOWED_IN_EDUCATION_REQUIRED.getDriveError());
        }else if(driveDTO.getMinGapAllowInEducation()<0) {
            recruitmeException.addException(ErrorFor.MINIMUM_GAP_ALLOWED_IN_EDUCATION_ERR.getErrorFor(), DriveError.INVALID_MINIMUM_GAP_ALLOWED_IN_EDUCATION.getDriveError());
        }

        if(driveDTO.getUploadedById()==null) {
            recruitmeException.addException(ErrorFor.UPLOAD_BY_ID_ERR.getErrorFor(), DriveError.UPLOAD_BY_ID_REQUIRED.getDriveError());
        } else if(PlacementCoordinatorModel.placementCoordinatorIdMap.containsKey(driveDTO.getUploadedById())==false) {
            log.debug("Uploaded by id: {}",driveDTO.getUploadedById());
            recruitmeException.addException(ErrorFor.UPLOAD_BY_ID_ERR.getErrorFor(), DriveError.INVALID_UPLOAD_BY_ID.getDriveError());
        }

        if(recruitmeException.getExceptions().size()!=0) return recruitmeException;
        return null;
    }
}
