package com.recruitme;

import com.recruitme.domain.*;
import com.recruitme.model.*;
import com.recruitme.repository.*;
import com.recruitme.repository.interview.experience.InterviewExperienceRepository;
import com.recruitme.service.domain.branch.dto.BranchDTO;
import com.recruitme.service.domain.city.dto.CityDTO;
import com.recruitme.service.domain.country.dto.CountryDTO;
import com.recruitme.service.domain.program.dto.ProgramDTO;
import com.recruitme.service.domain.state.dto.StateDTO;
import com.recruitme.service.domain.student.holding.offers.dto.StudentHoldingOffersDTO;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RecruitmeApplication {

	@Autowired
	private ProgramRepository programRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private PlacementCoordinatorRepository placementCoordinatorRepository;
	@Autowired
	private PlacementPreparationFacultyRepository placementPreparationFacultyRepository;
	@Autowired
	private DriveRepository driveRepository;
	@Autowired
	private InterviewExperienceRepository interviewExperienceRepository;
	@Autowired
	private StudentRegisteredInDriveRepository studentRegisteredInDriveRepository;
	@Autowired
	private StudentHoldingOffersRepository studentHoldingOffersRepository;

	public static void main(String[] args) {
		SpringApplication.run(RecruitmeApplication.class, args);
		System.out.println("-----------------------------------------------");
		System.out.println("Application Started");
		System.out.println("Backend URL: http://localhost:8080/");
		System.out.println("Frontend URL: http://localhost:3000/");
		System.out.println("-----------------------------------------------");
	}

	@Bean
	void populateDS()
	{

		//code to populate Drive related DS
		DriveModel.driveList=this.driveRepository.findAll();
		DriveModel.driveList.forEach((drive)->{
			Integer driveId=drive.getId();
			DriveModel.driveIdMap.put(driveId,drive);
		});


		//code to populate Program related DS
		List<Program> programList=this.programRepository.findAll();
		programList.forEach((program)->{
			Integer programId=program.getId();
			ModelMapper mapper=new ModelMapper();
			ProgramDTO programDTO=mapper.map(program,ProgramDTO.class);
			ProgramModel.programIdMap.put(programId,programDTO);
			ProgramModel.programList.add(programDTO);
		});

		//code to populate Branch related DS
		List<Branch> branchList=this.branchRepository.findAll();
		branchList.forEach((branch)->{
			Integer branchId=branch.getId();
			Integer programId=branch.getProgram().getId();
			if(BranchModel.programIdBranchesMap.containsKey(programId)==false) {
				ModelMapper mapper=new ModelMapper();
				BranchDTO branchDTO=mapper.map(branch,BranchDTO.class);
				List<BranchDTO> branchDTOList=new LinkedList<>();
				branchDTOList.add(branchDTO);
				BranchModel.programIdBranchesMap.put(programId,branchDTOList);
				BranchModel.branchIdMap.put(branchId,branchDTO);
				BranchModel.branchList.add(branchDTO);
			}else {
				ModelMapper mapper=new ModelMapper();
				BranchDTO branchDTO=mapper.map(branch,BranchDTO.class);
				List<BranchDTO> branchDTOList=BranchModel.programIdBranchesMap.get(programId);
				branchDTOList.add(branchDTO);
				BranchModel.programIdBranchesMap.put(programId,branchDTOList);
				BranchModel.branchIdMap.put(branchId,branchDTO);
				BranchModel.branchList.add(branchDTO);
			}
		});

		//code to populate Country related DS
		List<Country> countryList=this.countryRepository.findAll();
		countryList.forEach((country)->{
			ModelMapper mapper=new ModelMapper();
			CountryDTO countryDTO=mapper.map(country,CountryDTO.class);
			CountryModel.countryIdMap.put(country.getId(),countryDTO);
			CountryModel.countryList.add(countryDTO);
		});

		//code to populate State related DS
		List<State> stateList=this.stateRepository.findAll();
		stateList.forEach((state)->{
			Integer countryId=state.getCountry().getId();
			if(StateModel.stateCountryIdMap.containsKey(countryId)==false) {
				List<StateDTO> states=new LinkedList<>();
				ModelMapper mapper=new ModelMapper();
				StateDTO stateDTO=mapper.map(state,StateDTO.class);
				states.add(stateDTO);
				StateModel.stateCountryIdMap.put(countryId,states);
			}
			else {
				List<StateDTO> states=StateModel.stateCountryIdMap.get(countryId);
				ModelMapper mapper=new ModelMapper();
				StateDTO stateDTO=mapper.map(state,StateDTO.class);
				states.add(stateDTO);
				StateModel.stateCountryIdMap.put(countryId,states);
			}
		});

		//code to populate City related DS
		List<City> cityList=this.cityRepository.findAll();
		cityList.forEach((city)->{
			Integer stateId=city.getState().getId();
			if(CityModel.cityStateIdMap.containsKey(stateId)==false) {
				List<CityDTO> cities=new LinkedList<>();
				ModelMapper mapper=new ModelMapper();
				CityDTO cityDTO=mapper.map(city,CityDTO.class);
				cities.add(cityDTO);
				CityModel.cityStateIdMap.put(stateId,cities);
			}
			else {
				List<CityDTO> cities=CityModel.cityStateIdMap.get(stateId);
				ModelMapper mapper=new ModelMapper();
				CityDTO cityDTO=mapper.map(city,CityDTO.class);
				cities.add(cityDTO);
				CityModel.cityStateIdMap.put(stateId,cities);
			}
		});

		//Code to populate Student realted DS
		StudentModel.studentList=this.studentRepository.findAll();
		StudentModel.studentList.forEach((student)->{
				Long id=student.getId();
				String enrollmentNumber=student.getEnrollmentNumber();
				String primaryMobileNumber=student.getPrimaryMobileNumber();
				String primaryEmail=student.getPrimaryEmail();
				String mouCompanyReferenceId=student.getMouCompanyReferenceId();
				System.out.println("Student Id: "+id);
				StudentModel.studentIdMap.put(id,student);
				StudentModel.studentEnrollmentMap.put(enrollmentNumber,student);
				StudentModel.studentMouReferenceIdMap.put(mouCompanyReferenceId,student);
				StudentModel.studentPrimaryEmailMap.put(primaryEmail,student);
				StudentModel.studentPrimaryMobileNumberMap.put(primaryMobileNumber,student);
		});

		//Code to populate PlacementCoordinator related DS
		PlacementCoordinatorModel.placementCoordinatorList=this.placementCoordinatorRepository.findAll();
		PlacementCoordinatorModel.placementCoordinatorList.forEach((placementCoordinator)->{
			Long id=placementCoordinator.getId();
			String email=placementCoordinator.getPrimaryEmail();
			String mobileNumber=placementCoordinator.getPrimaryMobileNumber();
			PlacementCoordinatorModel.placementCoordinatorIdMap.put(id,placementCoordinator);
			PlacementCoordinatorModel.placementCoordinatorEmailMap.put(email,placementCoordinator);
			PlacementCoordinatorModel.placementCoordinatorMobileMap.put(mobileNumber,placementCoordinator);
		});

		//Code to populate PlacementPreparationFaculty related DS
		PlacementPreparationFacultyModel.placementPreparationFacultyList=this.placementPreparationFacultyRepository.findAll();
		PlacementPreparationFacultyModel.placementPreparationFacultyList.forEach((placementPreparationFaculty)->{
			Long id=placementPreparationFaculty.getId();
			String email=placementPreparationFaculty.getPrimaryEmail();
			String mobileNumber=placementPreparationFaculty.getPrimaryMobileNumber();
			PlacementPreparationFacultyModel.placementPreparationFacultyIdMap.put(id,placementPreparationFaculty);
			PlacementPreparationFacultyModel.placementPreparationFacultyEmailMap.put(email,placementPreparationFaculty);
			PlacementPreparationFacultyModel.placementPreparationFacultyMobileMap.put(mobileNumber,placementPreparationFaculty);
		});

		//Code to populate InterviewExperience related DS
		InterviewExperienceModel.interviewExperienceList=this.interviewExperienceRepository.findAll();

		//Code to populate StudentRegisteredInDrive related DS
		List<StudentRegisteredInDrive> studentRegisteredInDriveList=this.studentRegisteredInDriveRepository.findAll();
		studentRegisteredInDriveList.forEach((registeredStudent)->{
			Integer driveId=registeredStudent.getDrive().getId();
			Long studentId=registeredStudent.getStudent().getId();
			if(StudentRegisteredInDriveModel.studentRegisteredInDriveMap.containsKey(driveId)==false) {
				List<Long> studentIdList=new LinkedList<>();
				studentIdList.add(studentId);
				StudentRegisteredInDriveModel.studentRegisteredInDriveMap.put(driveId,studentIdList);
			}else {
				List<Long> studentIdList=StudentRegisteredInDriveModel.studentRegisteredInDriveMap.get(driveId);
				studentIdList.add(studentId);
				StudentRegisteredInDriveModel.studentRegisteredInDriveMap.put(driveId,studentIdList);
			}
		});

		//Code to populate StudentHoldingOffers related DS
		List<StudentHoldingOffers> studentHoldingOffersList=this.studentHoldingOffersRepository.findAll();
		studentHoldingOffersList.forEach((studentHoldingOffers)->{
			Long studentId=studentHoldingOffers.getStudent().getId();
			ModelMapper mapper=new ModelMapper();
			if(StudentHoldingOffersModel.studentHoldingOffersDTOMap.containsKey(studentId)==false) {
				List<StudentHoldingOffersDTO> studentHoldingOffersDTOList=new LinkedList<>();
				StudentHoldingOffersDTO studentHoldingOffersDTO=mapper.map(studentHoldingOffers,StudentHoldingOffersDTO.class);
				studentHoldingOffersDTOList.add(studentHoldingOffersDTO);
				StudentHoldingOffersModel.studentHoldingOffersDTOMap.put(studentId,studentHoldingOffersDTOList);
			}else {
				List<StudentHoldingOffersDTO> studentHoldingOffersDTOList=StudentHoldingOffersModel.studentHoldingOffersDTOMap.get(studentId);
				StudentHoldingOffersDTO studentHoldingOffersDTO=mapper.map(studentHoldingOffers,StudentHoldingOffersDTO.class);
				studentHoldingOffersDTOList.add(studentHoldingOffersDTO);
				StudentHoldingOffersModel.studentHoldingOffersDTOMap.put(studentId,studentHoldingOffersDTOList);
			}
		});

	}

}
