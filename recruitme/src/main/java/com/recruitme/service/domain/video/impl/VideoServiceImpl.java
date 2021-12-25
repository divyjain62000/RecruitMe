package com.recruitme.service.domain.video.impl;

import com.google.api.services.drive.model.File;

import com.recruitme.domain.Topic;
import com.recruitme.domain.Video;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.model.PlacementPreparationFacultyModel;
import com.recruitme.repository.video.VideoRepository;
import com.recruitme.service.domain.topic.TopicService;
import com.recruitme.service.domain.topic.dto.TopicDTO;
import com.recruitme.service.domain.video.VideoService;
import com.recruitme.service.domain.video.dto.VideoMetaDataDTO;
import com.recruitme.service.domain.video.dto.VideoSearchDTO;
import com.recruitme.service.domain.video.mapper.VideoMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

//Validataion during video save will apply from frontend

@Service
@Slf4j
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private TopicService topicService;

    @Override
    public void save(File uploadedFile,VideoMetaDataDTO videoMetaDataDTO)  throws RecruitmeException {
        Video video=new Video();
        video.setVideoExternalId(uploadedFile.getId());
        video.setTitle(videoMetaDataDTO.getTitle());
        video.setFileName(uploadedFile.getName());
        video.setFileFormat(uploadedFile.getFullFileExtension());
        video.setUploadDate(LocalDateTime.now());
        video.setUploadBy(PlacementPreparationFacultyModel.placementPreparationFacultyIdMap.get(videoMetaDataDTO.getUploadById()));
        Topic topic=this.topicService.findById(videoMetaDataDTO.getTopicId());
        video.setTopic(topic);
        this.videoRepository.save(video);
    }

    @Cacheable
    @Override
    public Page<VideoMetaDataDTO> search(VideoSearchDTO videoSearchDTO, Pageable pageable) {
        Page<Video> videoPage;
        if(videoSearchDTO.getTopicId()!=null)  videoPage=this.videoRepository.findByTopicId(videoSearchDTO.getTopicId(),pageable);
        else videoPage=this.videoRepository.findAll(pageable);
        Set<VideoMetaDataDTO> videoMetaDataDTOSet=new HashSet<>();
        Set<VideoMetaDataDTO> videoMetaDataDTOS=new HashSet<>();
        VideoMapper videoMapper=new VideoMapper();
            AtomicBoolean flag= new AtomicBoolean(false);
            videoPage.getContent().forEach((video) -> {
                if ((videoSearchDTO.getTitle() != null && video.getTitle().toLowerCase().contains(videoSearchDTO.getTitle().toLowerCase())) || (videoSearchDTO.getSearch() != null && video.getTitle().toLowerCase().contains(videoSearchDTO.getSearch().toLowerCase()))) {
                    flag.set(true);
                    VideoMetaDataDTO videoMetaDataDTO = videoMapper.toVideoMetaDataDTO(video);
                    videoMetaDataDTOSet.add(videoMetaDataDTO);
                }
                if ((videoSearchDTO.getTopic() != null && video.getTopic().getName().toLowerCase().contains(videoSearchDTO.getTopic().toLowerCase())) || (videoSearchDTO.getSearch() != null && video.getTopic().getName().toLowerCase().contains(videoSearchDTO.getSearch().toLowerCase()))) {
                    flag.set(true);
                    VideoMetaDataDTO videoMetaDataDTO = videoMapper.toVideoMetaDataDTO(video);
                    videoMetaDataDTOSet.add(videoMetaDataDTO);
                }
                videoMetaDataDTOS.add(videoMapper.toVideoMetaDataDTO(video));
            });
            log.debug("Video Lits Size {}",videoPage.getContent().size());
            log.debug("Video Lits Size {}",videoMetaDataDTOSet.size());
            if(flag.get()==false) {
                List<VideoMetaDataDTO> videoMetaDataDTOList;
                videoMetaDataDTOList=new LinkedList<>(videoMetaDataDTOS);
                int videoMetaDataDTOListSize=videoMetaDataDTOList.size();
                return new PageImpl<VideoMetaDataDTO>(videoMetaDataDTOList,pageable,videoMetaDataDTOListSize);
            }
        List<VideoMetaDataDTO> videoMetaDataDTOList;
        videoMetaDataDTOList=new LinkedList<>(videoMetaDataDTOSet);
        int videoMetaDataDTOListSize=videoMetaDataDTOList.size();
        return new PageImpl<VideoMetaDataDTO>(videoMetaDataDTOList,pageable,videoMetaDataDTOListSize);
    }

    public List<VideoMetaDataDTO> findAll() {
        List<Video> videoList=this.videoRepository.findAll();
        List<VideoMetaDataDTO> videoMetaDataDTOList=new LinkedList<>();
        VideoMapper videoMapper=new VideoMapper();
        videoList.forEach((video)->{
            VideoMetaDataDTO videoMetaDataDTO = videoMapper.toVideoMetaDataDTO(video);
            videoMetaDataDTOList.add(videoMetaDataDTO);
        });
        return videoMetaDataDTOList;
    }


}
