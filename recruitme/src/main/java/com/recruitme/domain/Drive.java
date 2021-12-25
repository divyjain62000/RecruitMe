package com.recruitme.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * Drive class hold various properties of drive
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:22:00 AM
 */
@Entity
@Setter
@Getter
@Table(name="drive")
@NoArgsConstructor
public class Drive {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false,length = 120)
    private String driveName;

    @Column(nullable = false,length=100)
    private String company;

    @Column(nullable = false)
    private BigDecimal minPackage;

    @Column(nullable = false)
    private BigDecimal maxPackage;

    @Column(nullable = false)
    @ElementCollection(targetClass=Integer.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> validBranches;

    @Column(nullable = false)
    @ElementCollection(targetClass=Integer.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> validPrograms;

    @Column(name="required_10th_cgpa")
    private Float required10thCGPA;

    @Column(name="required_10th_percentage")
    private Float required10thPercentage;

    @Column(name="required_12th_cgpa")
    private Float required12thCGPA;

    @Column(name="required_12th_percentage",nullable = false)
    private Float required12thPercentage;

    @Column(name="required_ug_cgpa")
    private Float requiredUGCGPA;

    @Column(name="require_ug_percentage")
    private Float requiredUGPercentage;

    @Column(name="required_pg_cgpa")
    private Float requiredPGCGPA;

    @Column(name="require_pg_percentage")
    private Float requiredPGPercentage;

    @Column(nullable = false)
    private Short minimumBacklogsAllowed;

    @Column(nullable = false)
    private Boolean isOnlyForGirls=false;

    @Column(nullable = false)
    private Boolean isOnlyForBoys=false;

    @Column(nullable = false)
    private LocalDateTime lastDateToApply;

    @Column(nullable = false)
    private Boolean isFullTimeVaccancy=false;

    @Column(nullable = false)
    private Boolean isInternshipVaccancy=false;

    @Column(nullable = false)
    private Boolean onlyForUgStudents=false;

    @Column(nullable = false)
    private Boolean onlyForPgStudents=false;

    @Column(nullable = false)
    private Boolean forBothUgPgStudents;

    @Column
    @ElementCollection(targetClass = Integer.class,fetch = FetchType.EAGER)
    private List<Integer> ugPassoutYearAllow;

    @Column
    @ElementCollection(targetClass = Integer.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> pgPassoutYearAllow;

    @ManyToOne
    private PlacementCoordinator uploadedBy;

    @Column(nullable = false)
    private Integer minGapAllowInEducation;

    @Column
    private String otherDelatils;

    @JsonIgnore
    @OneToMany(mappedBy = "drive")
    private List<StudentRegisteredInDrive> studentRegisteredInDriveList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drive drive = (Drive) o;
        return getId().equals(drive.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Drive{" +
                "id=" + id +
                ", driveName='" + driveName + '\'' +
                ", company='" + company + '\'' +
                ", minPackage=" + minPackage +
                ", maxPackage=" + maxPackage +
                ", validBranches=" + validBranches +
                ", validPrograms=" + validPrograms +
                ", required10thCGPA=" + required10thCGPA +
                ", required10thPercentage=" + required10thPercentage +
                ", required12thCGPA=" + required12thCGPA +
                ", required12thPercentage=" + required12thPercentage +
                ", requiredUGCGPA=" + requiredUGCGPA +
                ", requiredUGPercentage=" + requiredUGPercentage +
                ", requiredPGCGPA=" + requiredPGCGPA +
                ", requiredPGPercentage=" + requiredPGPercentage +
                ", minimumBacklogsAllowed=" + minimumBacklogsAllowed +
                ", isOnlyForGirls=" + isOnlyForGirls +
                ", isOnlyForBoys=" + isOnlyForBoys +
                ", lastDateToApply=" + lastDateToApply +
                ", isFullTimeVaccancy=" + isFullTimeVaccancy +
                ", isInternshipVaccancy=" + isInternshipVaccancy +
                ", onlyForUgStudents=" + onlyForUgStudents +
                ", onlyForPgStudents=" + onlyForPgStudents +
                ", forBothUgPgStudents=" + forBothUgPgStudents +
                ", ugPassoutYearAllow=" + ugPassoutYearAllow +
                ", pgPassoutYearAllow=" + pgPassoutYearAllow +
                ", uploadedBy=" + uploadedBy +
                ", minGapAllowInEducation=" + minGapAllowInEducation +
                ", otherDelatils='" + otherDelatils + '\'' +
                ", studentRegisteredInDriveList=" + studentRegisteredInDriveList +
                '}';
    }
}
