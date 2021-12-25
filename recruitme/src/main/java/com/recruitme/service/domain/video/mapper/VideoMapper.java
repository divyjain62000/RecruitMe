package com.recruitme.service.domain.video.mapper;

import com.recruitme.domain.Video;
import com.recruitme.service.domain.video.dto.VideoMetaDataDTO;

public class VideoMapper {

    public VideoMetaDataDTO toVideoMetaDataDTO(Video video) {
        VideoMetaDataDTO videoMetaDataDTO=new VideoMetaDataDTO();
        videoMetaDataDTO.setId(video.getId());
        videoMetaDataDTO.setTitle(video.getTitle());
        videoMetaDataDTO.setVideoExternalId(video.getVideoExternalId());
        videoMetaDataDTO.setTopicId(video.getTopic().getId());
        videoMetaDataDTO.setUploadById(video.getUploadBy().getId());
        return videoMetaDataDTO;
    }

}
