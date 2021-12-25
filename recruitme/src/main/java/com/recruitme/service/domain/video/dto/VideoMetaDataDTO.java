package com.recruitme.service.domain.video.dto;

import com.recruitme.domain.PlacementPreparationFaculty;
import com.recruitme.domain.Topic;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class VideoMetaDataDTO extends DataTransferObject {

    private Integer id;
    private String videoExternalId;
    private String title;
    private Integer topicId;
    private Long uploadById;
}
