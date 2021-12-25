package com.recruitme.service.domain.parents;

import com.recruitme.domain.Parents;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.service.domain.parents.dto.ParentsDTO;
import org.hibernate.annotations.Parent;

/**
 * Service interface for managing Parents
 */
public interface ParentsService {

    Integer save(ParentsDTO parentsDTO) throws RecruitmeException;
    void edit(ParentsDTO parentsDTO) throws RecruitmeException;
    ParentsDTO findById(Integer id);
    void delete(Parents parents);
}
