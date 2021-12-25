package com.recruitme.utility.token;

import com.recruitme.domain.PlacementPreparationFaculty;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorDTO;
import com.recruitme.service.domain.placement.faculty.dto.PlacementPreparationFacultyDTO;
import com.recruitme.service.domain.student.dto.StudentDTO;
import com.recruitme.service.mail.dto.ConfirmationTokenDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class TokenGenerationUtility {

    private TokenGenerationUtility() { }
    public static ConfirmationTokenDTO createTokenForStudent(StudentDTO studentDTO)
    {
        ConfirmationTokenDTO confirmationTokenDTO=new ConfirmationTokenDTO();
        UUID uuid=UUID.randomUUID();
        String token=uuid.toString();
        LocalDateTime tokenCreationDate=LocalDateTime.now();
        LocalDateTime tokenExpiryDate=LocalDateTime.now().plusMinutes(10);
        confirmationTokenDTO.setToken(token);
        confirmationTokenDTO.setTokenCreationDateTime(tokenCreationDate);
        confirmationTokenDTO.setTokenExpiryDateTime(tokenExpiryDate);
        confirmationTokenDTO.setStudentId(studentDTO.getId());
        return confirmationTokenDTO;
    }
    public static ConfirmationTokenDTO createTokenPlacementCoordinator(PlacementCoordinatorDTO placementCoordinatorDTO)
    {
        ConfirmationTokenDTO confirmationTokenDTO=new ConfirmationTokenDTO();
        UUID uuid=UUID.randomUUID();
        String token=uuid.toString();
        LocalDateTime tokenCreationDate=LocalDateTime.now();
        LocalDateTime tokenExpiryDate=LocalDateTime.now().plusMinutes(10);
        confirmationTokenDTO.setToken(token);
        confirmationTokenDTO.setTokenCreationDateTime(tokenCreationDate);
        confirmationTokenDTO.setTokenExpiryDateTime(tokenExpiryDate);
        confirmationTokenDTO.setPlacementCoordinatorId(placementCoordinatorDTO.getId());
        return confirmationTokenDTO;
    }
    public static ConfirmationTokenDTO createTokenPlacementPreparationFaculty(PlacementPreparationFacultyDTO placementPreparationFacultyDTO)
    {
        ConfirmationTokenDTO confirmationTokenDTO=new ConfirmationTokenDTO();
        UUID uuid=UUID.randomUUID();
        String token=uuid.toString();
        LocalDateTime tokenCreationDate=LocalDateTime.now();
        LocalDateTime tokenExpiryDate=LocalDateTime.now().plusMinutes(10);
        confirmationTokenDTO.setToken(token);
        confirmationTokenDTO.setTokenCreationDateTime(tokenCreationDate);
        confirmationTokenDTO.setTokenExpiryDateTime(tokenExpiryDate);
        confirmationTokenDTO.setPlacementPreparationFacultyId(placementPreparationFacultyDTO.getId());
        return confirmationTokenDTO;
    }




}
