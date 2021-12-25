package com.recruitme.enums;

/**
 *Gender enum holds various gender
 *
 * @author Divy Jain
 * @version 1.0
 * @since Aug 28, 2021 6:45:00 AM
 */
public enum Gender {

    MALE('M'),
    FEMALE('F'),
    OTHER('O');

    private Character gender;
    Gender(Character gender) { this.gender=gender; }
    public Character getGender() { return this.gender; }
}
