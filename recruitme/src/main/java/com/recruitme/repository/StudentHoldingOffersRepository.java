package com.recruitme.repository;

import com.recruitme.domain.StudentHoldingOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentHoldingOffersRepository extends JpaRepository<StudentHoldingOffers,Integer> {
    List<StudentHoldingOffers> findAllByStudentId(Long studentId);
}