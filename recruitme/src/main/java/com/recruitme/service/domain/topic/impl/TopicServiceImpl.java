package com.recruitme.service.domain.topic.impl;

import com.recruitme.domain.Topic;
import com.recruitme.repository.topic.TopicRepository;
import com.recruitme.service.domain.topic.TopicService;
import com.recruitme.service.domain.topic.dto.TopicDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public Page<TopicDTO> findAll(Pageable pageable) {
        List<Topic> topicList=topicRepository.findAll();
        List<TopicDTO> topicDTOList=new LinkedList<>();
        ModelMapper mapper=new ModelMapper();
        topicList.forEach((topic)->{
            TopicDTO topicDTO=mapper.map(topic,TopicDTO.class);
            topicDTOList.add(topicDTO);
        });
        int topicDTOListSize= topicDTOList.size();
        return new PageImpl<>(topicDTOList,pageable,topicDTOListSize);
    }

    @Override
    public TopicDTO findByName(String name) {
        Topic topic=this.topicRepository.findByName(name);
        ModelMapper mapper=new ModelMapper();
        TopicDTO topicDTO=mapper.map(topic,TopicDTO.class);
        return topicDTO;
    }

    @Override
    public Topic findById(Integer id) {
        Optional<Topic> topic=this.topicRepository.findById(id);
        if(!topic.isPresent()) return null;
        /*ModelMapper mapper=new ModelMapper();
        TopicDTO topicDTO=mapper.map(topic,TopicDTO.class);
        return topicDTO;*/
        return topic.get();
    }


}
