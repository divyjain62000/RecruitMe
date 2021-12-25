package com.recruitme.enums;

public enum SchoolBoard {

    CBSE("CBSE"),
    MP_BOARD("MP_BOARD"),
    ICSE("ICSE");

    private String schoolBoard;
    SchoolBoard(String schoolBoard) { this.schoolBoard=schoolBoard; }
    public String getSchoolBoard() { return this.schoolBoard; }
}
