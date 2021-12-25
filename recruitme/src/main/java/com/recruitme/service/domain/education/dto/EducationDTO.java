package com.recruitme.service.domain.education.dto;


import com.recruitme.domain.Branch;
import com.recruitme.domain.Program;
import com.recruitme.enums.MarksType;
import com.recruitme.enums.SchoolBoard;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
public class EducationDTO extends DataTransferObject {

    private Integer id;
    private String ugCollegeName;
    private Integer ugProgramId;
    private Integer ugBranchId;
    private Float ugMarks;
    private MarksType ugMarksType;
    private Integer ugPassoutYear;

    private String pgCollegeName;
    private Integer pgProgramId;
    private Integer pgBranchId;
    private Float pgMarks;
    private MarksType pgMarksType;
    private Integer pgPassoutYear;

    private String highSchool;
    private SchoolBoard highSchoolBoard;
    private Float highSchoolMarks;
    private MarksType highSchoolMarksType;
    private Integer highSchoolPassoutYear;

    private String seniorSecondarySchool;
    private SchoolBoard seniorSecondarySchoolBoard;
    private Float seniorSecondarySchoolMarks;
    private MarksType seniorSecondarySchoolMarksType;
    private Integer seniorSecondarySchoolPassoutYear;

    private Boolean isAnyGapInEducation;
    private String reasonForEducationalGap;

    private Short numberOfCurrentBacklog;

    @Override
    public String toString() {
        return "EducationDTO{" +
                "id=" + id +
                ", ugCollegeName='" + ugCollegeName + '\'' +
                ", ugProgramId=" + ugProgramId +
                ", ugBranchId=" + ugBranchId +
                ", ugMarks=" + ugMarks +
                ", ugMarksType=" + ugMarksType +
                ", ugPassoutYear=" + ugPassoutYear +
                ", pgCollegeName='" + pgCollegeName + '\'' +
                ", pgProgramId=" + pgProgramId +
                ", pgBranchId=" + pgBranchId +
                ", pgMarks=" + pgMarks +
                ", pgMarksType=" + pgMarksType +
                ", pgPassoutYear=" + pgPassoutYear +
                ", highSchool='" + highSchool + '\'' +
                ", highSchoolBoard=" + highSchoolBoard +
                ", highSchoolMarks=" + highSchoolMarks +
                ", highSchoolMarksType=" + highSchoolMarksType +
                ", highSchoolPassoutYear=" + highSchoolPassoutYear +
                ", seniorSecondarySchool='" + seniorSecondarySchool + '\'' +
                ", seniorSecondarySchoolBoard=" + seniorSecondarySchoolBoard +
                ", seniorSecondarySchoolMarks=" + seniorSecondarySchoolMarks +
                ", seniorSecondarySchoolMarksType=" + seniorSecondarySchoolMarksType +
                ", seniorSecondarySchoolPassoutYear=" + seniorSecondarySchoolPassoutYear +
                ", isAnyGapInEducation=" + isAnyGapInEducation +
                ", reasonForEducationalGap='" + reasonForEducationalGap + '\'' +
                ", numberOfCurrentBacklog=" + numberOfCurrentBacklog +
                '}';
    }
}
