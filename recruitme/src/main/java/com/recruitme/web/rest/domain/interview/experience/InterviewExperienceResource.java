package com.recruitme.web.rest.domain.interview.experience;

import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.interview.experience.InterviewExperienceService;
import com.recruitme.service.domain.interview.experience.dto.InterviewExperienceDTO;
import com.recruitme.service.domain.interview.experience.dto.InterviewExperienceResponseDTO;
import com.recruitme.service.domain.interview.experience.dto.InterviewExperienceSearchDTO;
import com.recruitme.service.domain.student.dto.StudentDTO;
import com.recruitme.service.domain.student.dto.StudentSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.recruitme.domain.InterviewExperience;

/**
 * Rest Controller for managing {@link InterviewExperience}
 */
@RestController
@RequestMapping("/api/interview-experience")
public class InterviewExperienceResource {

    @Autowired
    private InterviewExperienceService interviewExperienceService;

    @PostMapping("/add")
    public ResponseEntity<ActionResponse> addInterviewExperience(@RequestBody InterviewExperienceDTO interviewExperienceDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.interviewExperienceService.save(interviewExperienceDTO);
            response.setSuccessful(true);
            response.setException(false);
            return ResponseEntity.ok().body(response);
        } catch (RecruitmeException recruitmeException) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(recruitmeException.getExceptions());
            return ResponseEntity.ok().body(response);
        }
        catch (Exception exception) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(exception.getStackTrace());
            exception.getStackTrace(); // later on change to log
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * To get all interview experience or to get filter interview experience
     * @param interviewExperienceSearchDTO
     * @param pageable
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}
     */
    @PostMapping("/search")
    public ResponseEntity<Page<InterviewExperienceResponseDTO>> searchInterviewExperience(@RequestBody InterviewExperienceSearchDTO interviewExperienceSearchDTO, Pageable pageable) {
        Page<InterviewExperienceResponseDTO> interviewExperienceList=this.interviewExperienceService.search(interviewExperienceSearchDTO,pageable);
        return ResponseEntity.ok().body(interviewExperienceList);
    }

}
