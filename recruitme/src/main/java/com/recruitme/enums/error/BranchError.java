package com.recruitme.enums.error;

/**
 * BranchError holds error related to branch
 *
 * @author Divy Jain
 * @version 1.0
 * @since Sept 03,2021 06:40:00 AM
 */
public enum BranchError {

    BRANCH_DETAIL_REQUIRED("Branch details requried"),
    BRANCH_NAME_REQUIRED("Branch name required"),
    BRANCH_CODE_REQUIRED("Branch code required"),
    BRANCH_NOT_EXISTS("Branch not exists");


    private String branchError;
    BranchError(String branchError) { this.branchError=branchError; }

    public String getBranchError() { return this.branchError; }
}
