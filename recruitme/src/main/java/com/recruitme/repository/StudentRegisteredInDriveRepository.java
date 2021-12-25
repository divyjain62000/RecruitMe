package com.recruitme.repository;

import com.recruitme.domain.StudentRegisteredInDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRegisteredInDriveRepository extends JpaRepository<StudentRegisteredInDrive,Integer> {
    List<StudentRegisteredInDrive> findAllByStudentId(Long studentId);
}
