package com.recruitme.web.rest.domain.education;

import com.recruitme.domain.Student;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.education.EducationService;
import com.recruitme.service.domain.education.dto.EducationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Rest Controller for managing {@link Student}
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class EducationResource {

    @Autowired
    private EducationService educationService;

    /**
     * To add education
     *
     * @param educationDTO
     *  @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/add-education")
    public ResponseEntity<ActionResponse> addEducation(@RequestBody EducationDTO educationDTO) {
        ActionResponse response=new ActionResponse();
        try {
            Integer educationId=this.educationService.save(educationDTO);
            response.setSuccessful(true);
            response.setResult(educationId);
            response.setException(false);
            return ResponseEntity.ok().body(response);
        } catch (RecruitmeException recruitmeException) {
            log.debug("Recruitme Exception");
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(recruitmeException.getExceptions());
            return ResponseEntity.ok().body(response);
        }
        catch (Exception exception) {
            log.debug("exception");
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(exception.getStackTrace());
            exception.printStackTrace(); // later on change to log
            return ResponseEntity.internalServerError().body(response);
        }
    }
    /**
     * To edit education
     *
     * @param educationDTO
     *  @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/edit-education")
    public ResponseEntity<ActionResponse> editEducation(@RequestBody EducationDTO educationDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.educationService.edit(educationDTO);
            response.setSuccessful(true);
            response.setException(false);
            return ResponseEntity.ok().body(response);
        } catch (RecruitmeException recruitmeException) {
            log.debug("Exception Recruitme");
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(recruitmeException.getExceptions());
            return ResponseEntity.ok().body(response);
        }
        catch (Exception exception) {
            log.debug("Exception");
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(exception.getStackTrace());
            exception.printStackTrace(); // later on change to log
            return ResponseEntity.internalServerError().body(response);
        }
    }
    @GetMapping("/get-education/{id}")
    public ResponseEntity<ActionResponse> findById(@PathVariable(name="id") Integer id) {
        ActionResponse actionResponse=new ActionResponse();
        try {
            EducationDTO educationDTO=this.educationService.findById(id);
            actionResponse.setSuccessful(true);
            actionResponse.setResult(educationDTO);
            actionResponse.setException(false);
            return ResponseEntity.ok().body(actionResponse);
        }catch (Exception exception) {
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(exception.getStackTrace());
            exception.getStackTrace(); // later on change to log
            return ResponseEntity.ok().body(actionResponse);
        }
    }
}
