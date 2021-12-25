package com.recruitme.repository.topic;

import com.recruitme.domain.Topic;
import com.recruitme.service.domain.topic.dto.TopicDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicRepository extends JpaRepository<Topic,Integer> {
    Topic findByName(String name);
}
