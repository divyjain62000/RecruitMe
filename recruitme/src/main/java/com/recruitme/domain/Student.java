package com.recruitme.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Student class hold various properties of student
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01 2021 11:45:00 AM
 */

@Entity
@Setter
@Getter
@Table(name="student")
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,length=30,unique = true)
    private String enrollmentNumber;

    @Column(nullable = false,length=255)
    private String passwordKey;

    @Column(nullable = false,length=255)
    private String password;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(nullable = false,unique = true,length = 50)
    private String primaryEmail;

    @Column(length = 50,unique = true)
    private String secondaryEmail;

    @Column(nullable = false,length = 10)
    private String primaryMobileNumber;

    @Column(length=10)
    private String secondaryMobileNumber;

    @ManyToOne
    private Address address;

    @ManyToOne
    private Parents parents;

    @Column(nullable = false)
    private Boolean isPlaceForFulltime;

    @Column(nullable = false)
    private Boolean isPlaceForInternship;

    @Column(nullable = false)
    private Boolean isBlacklisted;

    @Column(length=300)
    private String reasonForBlacklist;

    @Column(nullable = false)
    private Boolean isValidPrimaryEmail;

    @Column(nullable = false)
    private Boolean isValidSecondaryEmail;

    @Column(nullable = false)
    private Boolean isValidPrimaryMobileNumber;

    @Column(nullable = false)
    private Boolean isValidSecondaryMobileNumber;

    @Column(nullable = false,length=1)
    private Character gender;

    @Column(nullable = false)
    private Boolean isIndian;

    @Column(nullable = false,length=20)
    private String mouCompanyReferenceId;

    @ManyToOne
    private Education education;

    @Column(nullable = false)
    private Boolean isAnyCriminalCase;

    @Column(length = 300)
    private String criminalCaseDescription;

    @Column(nullable = false)
    private Boolean isDisability;

    @Column(length=70)
    private String disability;


    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<InterviewExperience> interviewExperienceList;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<StudentHoldingOffers> studentHoldingOffersList;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<StudentRegisteredInDrive> studentRegisteredInDrive;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<ConfirmationToken> confirmationTokenList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return getId().equals(student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", enrollmentNumber='" + enrollmentNumber + '\'' +
                ", passwordKey='" + passwordKey + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", primaryEmail='" + primaryEmail + '\'' +
                ", secondaryEmail='" + secondaryEmail + '\'' +
                ", primaryMobileNumber='" + primaryMobileNumber + '\'' +
                ", secondaryMobileNumber='" + secondaryMobileNumber + '\'' +
                ", address=" + address +
                ", parents=" + parents +
                ", isPlaceForFulltime=" + isPlaceForFulltime +
                ", isPlaceForInternship=" + isPlaceForInternship +
                ", isBlacklisted=" + isBlacklisted +
                ", reasonForBlacklist='" + reasonForBlacklist + '\'' +
                ", isValidPrimaryEmail=" + isValidPrimaryEmail +
                ", isValidSecondaryEmail=" + isValidSecondaryEmail +
                ", isValidPrimaryMobileNumber=" + isValidPrimaryMobileNumber +
                ", isValidSecondaryMobileNumber=" + isValidSecondaryMobileNumber +
                ", gender=" + gender +
                ", isIndian=" + isIndian +
                ", mouCompanyReferenceId='" + mouCompanyReferenceId + '\'' +
                ", education=" + education +
                ", isAnyCriminalCase=" + isAnyCriminalCase +
                ", criminalCaseDescription='" + criminalCaseDescription + '\'' +
                ", isDisability=" + isDisability +
                ", disability='" + disability + '\'' +
                ", interviewExperienceList=" + interviewExperienceList +
                ", studentHoldingOffersList=" + studentHoldingOffersList +
                ", studentRegisteredInDrive=" + studentRegisteredInDrive +
                ", confirmationTokenList=" + confirmationTokenList +
                '}';
    }
}
