package com.recruitme.web.rest.domain.topic;

import com.recruitme.domain.Student;
import com.recruitme.service.domain.topic.TopicService;
import com.recruitme.service.domain.topic.dto.TopicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Rest Controller for managing {@link Student}
 */
@RestController
@RequestMapping("/api")
public class TopicResource {

    @Autowired
    private TopicService topicService;

    /**
     * To get all topics
     * @param pageable
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}
     */
    @GetMapping("/topics")
    public ResponseEntity<Page<TopicDTO>> getAll(Pageable pageable) {
        Page<TopicDTO> topicDTOList=this.topicService.findAll(pageable);
        return ResponseEntity.ok().body(topicDTOList);
    }

}
