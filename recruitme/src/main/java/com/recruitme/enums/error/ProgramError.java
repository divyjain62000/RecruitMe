package com.recruitme.enums.error;

/**
 * ProgramError holds error related to Program
 *
 * @author Divy Jain
 * @version 1.0
 * @since Sept 03,2021 06:51:00 AM
 */
public enum ProgramError {

    PROGRAM_DETAIL_REQUIRED("Program details required"),
    PROGRAM_NAME_REQUIRED("Program name required"),
    PROGRAM_CODE_REQUIRED("Program code required"),
    PROGRAM_NOT_EXISTS("Program not exists");

    private String programError;
    ProgramError(String programError) { this.programError=programError; }
    public String getProgramError() { return this.programError; }
}
