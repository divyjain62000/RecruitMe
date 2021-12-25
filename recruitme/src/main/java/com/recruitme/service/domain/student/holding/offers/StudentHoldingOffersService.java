package com.recruitme.service.domain.student.holding.offers;

import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.service.domain.student.holding.offers.dto.StudentHoldingOfferResponseDTO;
import com.recruitme.service.domain.student.holding.offers.dto.StudentHoldingOffersDTO;

import java.util.List;
import java.util.Map;

/**
 * The interface Student holding offers service.
 */
public interface StudentHoldingOffersService {
    /**
     * Save.
     *
     * @param studentHoldingOffersDTO the student holding offers dto
     * @throws RecruitmeException the recruitme exception
     */
    void save(StudentHoldingOffersDTO studentHoldingOffersDTO) throws RecruitmeException;

    /**
     * Edit.
     *
     * @param studentHoldingOffersDTO the student holding offers dto
     * @throws RecruitmeException the recruitme exception
     */
    void edit(StudentHoldingOffersDTO studentHoldingOffersDTO) throws RecruitmeException;


    /**
     * Delete by student id.
     *
     * @param studentId the student id
     */
    void deleteByStudentId(Long studentId);


    /**
     * Find all student holding offers.
     *
     * @return the list
     */
    List<StudentHoldingOfferResponseDTO> findAll();


    /**
     * Find student holding offers by student id.
     *
     * @param studentId the student id
     * @return the list
     */
    List<StudentHoldingOffersDTO> findByStudentId(Long studentId);

}