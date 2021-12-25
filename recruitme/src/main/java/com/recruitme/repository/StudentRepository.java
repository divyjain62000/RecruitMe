package com.recruitme.repository;

import com.recruitme.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Student entity.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

}
