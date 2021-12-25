package com.recruitme.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * InterviewExperience class hold various properties of interview experience
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:30:00 AM
 */
@Entity
@Setter
@Getter
@Table(name="interview_experience")
@NoArgsConstructor
public class InterviewExperience {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private  Student student;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private Short passoutYear;

    @Column(nullable = false)
    private BigDecimal salary;

    @Column(nullable = false)
    private Boolean isFullTimeVacancy;

    @Column(nullable = false)
    private Boolean isInternshipVacancy;

    @Column(nullable=false,length=5000)
    private String experience;

    @Column(nullable = false)
    private boolean ugStudent=false;

    @Column(nullable = false)
    private boolean pgStudent=false;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterviewExperience that = (InterviewExperience) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "InterviewExperience{" +
                "id=" + id +
                ", student=" + student +
                ", company='" + company + '\'' +
                ", passoutYear=" + passoutYear +
                ", salary=" + salary +
                ", isFullTimeVacancy=" + isFullTimeVacancy +
                ", isInternshipVacancy=" + isInternshipVacancy +
                ", experience='" + experience + '\'' +
                '}';
    }
}
