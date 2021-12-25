package com.recruitme.enums;

public enum MarksType {

    CGPA("CGPA"),
    PERCENTAGE("PERCENTAGE"),
    NA("NA");

    private String marksType;
    MarksType(String marksType) { this.marksType=marksType; }
    public String getMarksType() { return this.marksType; }

}
