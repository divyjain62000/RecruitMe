package com.recruitme.web.rest.domain.video;

import com.google.api.services.drive.model.File;
import com.recruitme.enums.error.GlobalError;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.video.VideoService;
import com.recruitme.service.domain.video.dto.VideoMetaDataDTO;
import com.recruitme.service.domain.video.dto.VideoSearchDTO;
import com.recruitme.service.google.drive.GoogleDriveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/video")
@Slf4j
public class VideoResource {
    @Autowired
    private VideoService videoService;

    @Autowired
    private GoogleDriveService googleDriveService;

    /**
     * To upload video
     * @param
     * @return
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public ResponseEntity<ActionResponse> uploadVideo(@RequestParam("videoFile")MultipartFile videoFile,@RequestParam("topicId") Integer topicId,@RequestParam("title") String title) {
        VideoMetaDataDTO videoMetaDataDTO=new VideoMetaDataDTO();
        videoMetaDataDTO.setTopicId(topicId);
        videoMetaDataDTO.setTitle(title);
        log.debug("Request to upload video: {}",videoMetaDataDTO);
        ActionResponse response=new ActionResponse();
        try {
            File uploadedfile=this.googleDriveService.upload(videoFile);
            this.videoService.save(uploadedfile,videoMetaDataDTO);
            response.setSuccessful(true);
            response.setException(false);
            return ResponseEntity.ok().body(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/search")
    public Page<VideoMetaDataDTO> search(@RequestBody VideoSearchDTO videoSearchDTO, Pageable pageable) {
        Page<VideoMetaDataDTO> videoMetaDataDTOPage= this.videoService.search(videoSearchDTO,pageable);
        return videoMetaDataDTOPage;
    }

    @GetMapping("/")
    public List<VideoMetaDataDTO> getAll() {
        List<VideoMetaDataDTO> videoMetaDataDTOList= this.videoService.findAll();
        return videoMetaDataDTOList;
    }

}
