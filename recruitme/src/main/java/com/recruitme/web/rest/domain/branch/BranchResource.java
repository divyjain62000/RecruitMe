package com.recruitme.web.rest.domain.branch;

import com.recruitme.domain.Branch;

import com.recruitme.enums.error.GlobalError;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.branch.BranchService;
import com.recruitme.service.domain.branch.dto.BranchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * REST controller for managing {@link Branch}
 */
@RestController
@RequestMapping("/api")
public class BranchResource {

    @Autowired
    BranchService branchService;

    /**
     * Get all the branch of particular program
     * @param programId
     * @param pageable
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}
     */
    @GetMapping("/branches/{programId}")
    public ResponseEntity<?> getAllProgram(@PathVariable(name="programId") Integer programId, Pageable pageable) {
        ActionResponse response=new ActionResponse();
        try {
            Page<BranchDTO> branches = this.branchService.findAll(programId,pageable);
            return ResponseEntity.ok().body(branches);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().body(response);
        }

    }
}
