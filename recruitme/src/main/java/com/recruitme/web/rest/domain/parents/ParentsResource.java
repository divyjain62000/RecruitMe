package com.recruitme.web.rest.domain.parents;

import com.recruitme.domain.Student;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.parents.ParentsService;
import com.recruitme.service.domain.parents.dto.ParentsDTO;
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
public class ParentsResource {

    @Autowired
    private ParentsService parentsService;

    /**
     * To add parents
     * @param parentsDTO
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/add-parents")
    public ResponseEntity<ActionResponse> addParents(@RequestBody ParentsDTO parentsDTO) {
        ActionResponse response=new ActionResponse();
        try {
            Integer parentsId=this.parentsService.save(parentsDTO);
            response.setSuccessful(true);
            response.setResult(parentsId);
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
     * To edit parents
     * @param parentsDTO
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/edit-parents")
    public ResponseEntity<ActionResponse> editParents(@RequestBody ParentsDTO parentsDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.parentsService.edit(parentsDTO);
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
    @GetMapping("/get-parents/{id}")
    public ResponseEntity<ActionResponse> findById(@PathVariable(name="id") Integer id) {
        ActionResponse actionResponse=new ActionResponse();
        try {
            ParentsDTO parentsDTO=this.parentsService.findById(id);
            actionResponse.setSuccessful(true);
            actionResponse.setResult(parentsDTO);
            actionResponse.setException(false);
            return ResponseEntity.ok().body(actionResponse);
        }catch (Exception exception) {
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(exception.getStackTrace());
            exception.getStackTrace(); // later on change to log
            return ResponseEntity.ok().body(actionResponse);
        }
    }}
