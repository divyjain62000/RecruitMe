package com.recruitme.enums;

public enum Role {

    PLACEMENT_COORDINATOR("PLACEMENT_COORDINATOR"),
    PLACEMENT_PREPARATION_FACULTY("PLACEMENT_PREPARATION_FACULTY"),
    STUDENT("STUDENT"),
    ADMIN("ADMIN");

    private String role;
    Role(String role) { this.role = role; }
    public String getRole() { return this.role; }

}
