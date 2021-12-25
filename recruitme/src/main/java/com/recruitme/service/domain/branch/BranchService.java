package com.recruitme.service.domain.branch;

import com.recruitme.service.domain.branch.dto.BranchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing Branch
 */
public interface BranchService {
    public Page<BranchDTO> findAll(Integer programId, Pageable pageable);
}
