package com.recruitme.repository;

import com.recruitme.domain.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Integer> {

    ConfirmationToken findByToken(String token);
    List<ConfirmationToken> findAllByStudentId(Long studentId);
}
