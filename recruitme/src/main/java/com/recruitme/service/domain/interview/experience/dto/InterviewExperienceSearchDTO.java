package com.recruitme.service.domain.interview.experience.dto;

import com.recruitme.service.dto.BaseFilterDTO;
import lombok.Data;

@Data
public class InterviewExperienceSearchDTO extends BaseFilterDTO {
    private String studentName;
    private String company;
    private Short passoutYear;
}
