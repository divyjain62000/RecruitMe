package com.recruitme.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * ConfirmationToken class hold various properties of confirmation token
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:18:00 AM
 */
@Entity
@Setter
@Getter
@Table(name="confirmation_token")
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "token", length = 150, nullable = false)
    private String token;

    @ManyToOne
    private Student student;

    @ManyToOne
    private PlacementCoordinator placementCoordinator;

    @ManyToOne
    private PlacementPreparationFaculty placementPreparationFaculty;


}