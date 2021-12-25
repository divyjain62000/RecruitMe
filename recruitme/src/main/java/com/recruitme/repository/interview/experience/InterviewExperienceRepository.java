package com.recruitme.repository.interview.experience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.recruitme.domain.InterviewExperience;

import java.util.List;

/**
 * Spring Data repository for the {@link InterviewExperience} entity.
 */
@Repository
public interface InterviewExperienceRepository extends JpaRepository<InterviewExperience,Integer> {
    List<InterviewExperience> findAllByStudentId(Long studentId);
}
