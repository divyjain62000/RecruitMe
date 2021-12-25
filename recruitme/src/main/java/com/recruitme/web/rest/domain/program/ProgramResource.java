package com.recruitme.web.rest.domain.program;

import com.recruitme.domain.Program;

import com.recruitme.enums.error.GlobalError;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.program.ProgramService;
import com.recruitme.service.domain.program.dto.ProgramDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for managing {@link Program}
 */
@RestController
@RequestMapping("/api")
public class ProgramResource {

    @Autowired
    ProgramService programService;

    /**
     * {@code GET /programs} : get all the Program
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}
     */
    @GetMapping("/programs")
    public ResponseEntity<?> getAllProgram(Pageable pageable) {
        ActionResponse response=new ActionResponse();
        try {
            Page<ProgramDTO> programs = this.programService.findAll(pageable);
            return ResponseEntity.ok().body(programs);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().body(response);
        }

    }
}
