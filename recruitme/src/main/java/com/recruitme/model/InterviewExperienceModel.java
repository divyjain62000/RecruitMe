package com.recruitme.model;

import com.recruitme.domain.InterviewExperience;

import java.util.LinkedList;
import java.util.List;

/**
 * InterviewExperienceModel class contains all DS related to {@link InterviewExperience}
 */
public class InterviewExperienceModel {
    public static List<InterviewExperience> interviewExperienceList;

    private InterviewExperienceModel() { }

    static {
        interviewExperienceList=new LinkedList<>();
    }
}
