package com.recruitme.service.mail.dto;

import com.recruitme.domain.Student;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ConfirmationTokenDTO extends DataTransferObject {

    private String token;
    private LocalDateTime tokenCreationDateTime;
    private LocalDateTime tokenExpiryDateTime;
    private Long studentId;
    private Long placementCoordinatorId;
    private Long placementPreparationFacultyId;

}
