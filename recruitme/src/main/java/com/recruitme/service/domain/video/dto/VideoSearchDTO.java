package com.recruitme.service.domain.video.dto;

import com.recruitme.service.dto.BaseFilterDTO;
import lombok.Data;

@Data
public class VideoSearchDTO extends BaseFilterDTO {
    String title;
    Integer topicId;
    String topic;
}
