package com.recruitme.service.domain.program;

import com.recruitme.service.domain.program.dto.ProgramDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing Program
 */
public interface ProgramService {
    public Page<ProgramDTO> findAll(Pageable pageable);
}
