package com.recruitme.web.rest.domain.student;

import com.recruitme.domain.Student;
import com.recruitme.enums.error.GlobalError;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.student.StudentService;
import com.recruitme.service.domain.student.dto.StudentDTO;
import com.recruitme.service.domain.student.dto.StudentResponseDTO;
import com.recruitme.service.domain.student.dto.StudentSearchDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Rest Controller for managing {@link Student}
 */
@RestController
@RequestMapping("/api/student")
@Slf4j
public class StudentResource {

    @Autowired
    private StudentService studentService;

    /**
     * To add Student
     * @param studentDTO
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/add-student")
    public ResponseEntity<ActionResponse> addStudent(@RequestBody StudentDTO studentDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.studentService.save(studentDTO);
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
            exception.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @PostMapping("/edit-student")
    public ResponseEntity<ActionResponse> editStudent(@RequestBody StudentDTO studentDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.studentService.edit(studentDTO);
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
            exception.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().body(response);
        }
    }



    /**
     * To get all student or to get filter student
     * @param studentSearchDTO
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}
     */
    @PostMapping("/search")
    public ResponseEntity<Page<StudentDTO>> searchStudents(@RequestBody StudentSearchDTO studentSearchDTO, Pageable pageable) {
            Page<StudentDTO> studentDTOList=this.studentService.search(studentSearchDTO,pageable);
            return ResponseEntity.ok().body(studentDTOList);
    }

    /**
     * To get all student or to get filter student
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}
     */
    @GetMapping("/")
    public ResponseEntity<Page<StudentResponseDTO>> getAll(Pageable pageable) {
        Page<StudentResponseDTO> studentDTOList=this.studentService.findAll(pageable);
        return ResponseEntity.ok().body(studentDTOList);
    }

    @GetMapping("/get-student/{id}")
    public ResponseEntity<ActionResponse> findStudentById(@PathVariable(name="id") Long id) {
        log.debug("Request to get student by id");
        ActionResponse actionResponse=new ActionResponse();
        try {
            StudentResponseDTO studentResponseDTO=this.studentService.findById(id);
            actionResponse.setSuccessful(true);
            actionResponse.setResult(studentResponseDTO);
            actionResponse.setException(false);
            return ResponseEntity.ok().body(actionResponse);
        } catch (Exception exception) {
            log.debug("Excetion occur while fetching student by id ");
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(exception.getStackTrace());
            exception.printStackTrace(); // later on change to log
            return ResponseEntity.ok().body(actionResponse);
        }
    }

    @PostMapping("/blacklist")
    public ResponseEntity<ActionResponse> blacklistStudent(@RequestBody StudentDTO studentDTO) {
        log.debug("Request to get student by id");
        ActionResponse actionResponse=new ActionResponse();
        try {
            this.studentService.blacklistStudent(studentDTO.getId(),studentDTO.getReasonForBlacklist());
            actionResponse.setSuccessful(true);
            actionResponse.setResult(null);
            actionResponse.setException(false);
            return ResponseEntity.ok().body(actionResponse);
        }catch (RecruitmeException recruitmeException) {
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(recruitmeException.getExceptions());
            return ResponseEntity.ok().body(actionResponse);
        } catch (Exception exception) {
            log.debug("Excetion occur while fetching student by id ");
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(exception.getStackTrace());
            exception.printStackTrace(); // later on change to log
            return ResponseEntity.ok().body(actionResponse);
        }
    }

    @PostMapping("/un-blacklist/{id}")
    public ResponseEntity<ActionResponse> unblacklistStudent(@PathVariable(name="id") Long id) {
        log.debug("Request to unblacklist student by id");
        ActionResponse actionResponse=new ActionResponse();
        try {
            this.studentService.unblacklistStudent(id);
            actionResponse.setSuccessful(true);
            actionResponse.setResult(null);
            actionResponse.setException(false);
            return ResponseEntity.ok().body(actionResponse);
        }catch (RecruitmeException recruitmeException) {
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(recruitmeException.getExceptions());
            return ResponseEntity.ok().body(actionResponse);
        } catch (Exception exception) {
            log.debug("Excetion occur while fetching student by id ");
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(exception.getStackTrace());
            exception.printStackTrace(); // later on change to log
            return ResponseEntity.ok().body(actionResponse);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ActionResponse> deleteStudent(@PathVariable("id") Long id) {
        log.debug("Request to delete student by id");
        ActionResponse actionResponse=new ActionResponse();
        try {
            this.studentService.delete(id);
            actionResponse.setSuccessful(true);
            actionResponse.setResult(null);
            actionResponse.setException(false);
            return ResponseEntity.ok().body(actionResponse);
        }catch (Exception exception) {
            log.debug("Excetion occur while deleting student by id ");
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(exception.getStackTrace());
            exception.printStackTrace(); // later on change to log
            return ResponseEntity.ok().body(actionResponse);
        }
    }

}
