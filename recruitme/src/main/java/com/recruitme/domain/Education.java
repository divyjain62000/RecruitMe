package com.recruitme.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Education class hold various properties of education
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:45:00 AM
 */

@Entity
@Setter
@Getter
@Table(name="education")
@NoArgsConstructor
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false,length=200)
    private String ugCollegeName;
    @ManyToOne
    private Program ugProgram;
    @ManyToOne
    private Branch ugBranch;
    @Column(nullable = false)
    private Float ugMarks;
    @Column(nullable = false,length=10)
    private String ugMarksType;
    @Column(nullable = false)
    private Integer ugPassoutYear;

    @Column(nullable = true,length=200)
    private String pgCollegeName;
    @ManyToOne
    private Program pgProgram;
    @ManyToOne
    private Branch pgBranch;
    @Column
    private Float pgMarks;
    @Column(nullable = true,length=10)
    private String pgMarksType;
    @Column
    private Integer pgPassoutYear;


    @Column(nullable = false,length=200)
    private String highSchool;
    @Column(nullable = false,length=20)
    private String highSchoolBoard;
    @Column(nullable = false)
    private Float highSchoolMarks;
    @Column(nullable = false,length=10)
    private String highSchoolMarksType;
    @Column(nullable = false)
    private Integer highSchoolPassoutYear;


    @Column(nullable = false,length=200)
    private String seniorSecondarySchool;
    @Column(nullable = false,length=20)
    private String seniorSecondarySchoolBoard;
    @Column(nullable = false)
    private Float seniorSecondarySchoolMarks;
    @Column(nullable = false,length=10)
    private String seniorSecondarySchoolMarksType;
    @Column(nullable = false)
    private Integer seniorSecondarySchoolPassoutYear;

    @Column(nullable = false)
    private Boolean isAnyGapInEducation;

    @Column
    private String reasonForEducationalGap;

    @Column(nullable = false)
    private Short numberOfCurrentBacklog;


    @JsonIgnore
    @OneToMany(mappedBy = "education")
    private List<Student> studentList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Education education = (Education) o;
        return getId().equals(education.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", ugCollegeName='" + ugCollegeName + '\'' +
                ", ugProgram=" + ugProgram +
                ", ugBranch=" + ugBranch +
                ", ugMarks=" + ugMarks +
                ", ugMarksType='" + ugMarksType + '\'' +
                ", ugPassoutYear=" + ugPassoutYear +
                ", pgCollegeName='" + pgCollegeName + '\'' +
                ", pgProgram=" + pgProgram +
                ", pgBranch=" + pgBranch +
                ", pgMarks=" + pgMarks +
                ", pgMarksType='" + pgMarksType + '\'' +
                ", pgPassoutYear=" + pgPassoutYear +
                ", highSchool='" + highSchool + '\'' +
                ", highSchoolBoard='" + highSchoolBoard + '\'' +
                ", highSchoolMarks=" + highSchoolMarks +
                ", highSchoolMarksType='" + highSchoolMarksType + '\'' +
                ", highSchoolPassoutYear=" + highSchoolPassoutYear +
                ", seniorSecondarySchool='" + seniorSecondarySchool + '\'' +
                ", seniorSecondarySchoolBoard='" + seniorSecondarySchoolBoard + '\'' +
                ", seniorSecondarySchoolMarks=" + seniorSecondarySchoolMarks +
                ", seniorSecondarySchoolMarksType='" + seniorSecondarySchoolMarksType + '\'' +
                ", seniorSecondarySchoolPassoutYear=" + seniorSecondarySchoolPassoutYear +
                ", isAnyGapInEducation=" + isAnyGapInEducation +
                ", reasonForEducationalGap='" + reasonForEducationalGap + '\'' +
                ", numberOfCurrentBacklog='" + numberOfCurrentBacklog + '\'' +
                ", studentList=" + studentList +
                '}';
    }
}
