package com.recruitme.service.domain.topic;

import com.recruitme.domain.Topic;
import com.recruitme.service.domain.topic.dto.TopicDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Service interface for managing Topic
 */
public interface TopicService {
    Page<TopicDTO> findAll(Pageable pageable);
    TopicDTO findByName(String topic);
    Topic findById(Integer id);
}
