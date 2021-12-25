package com.recruitme.service.domain.topic.dto;

import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

@Data
public class TopicDTO extends DataTransferObject {
    Integer id;
    String name;
}
