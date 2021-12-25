package com.recruitme.repository.video;

import com.recruitme.domain.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video,Integer> {

    Page<Video> findByTopicId(Integer topicId, Pageable pageable);
}
