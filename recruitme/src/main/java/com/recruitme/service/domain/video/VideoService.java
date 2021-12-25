package com.recruitme.service.domain.video;

import com.google.api.services.drive.model.File;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.service.domain.video.dto.VideoMetaDataDTO;
import com.recruitme.service.domain.video.dto.VideoSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VideoService {
    void save(File uploadedFile,VideoMetaDataDTO videoMetaDataDTO) throws RecruitmeException;
    Page<VideoMetaDataDTO> search(VideoSearchDTO videoSearchDTO, Pageable pageable) ;
    List<VideoMetaDataDTO> findAll();
}
