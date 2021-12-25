package com.recruitme.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * StudentRegistredInDrive class hold various properties of student registred in drive
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:52:00 AM
 */
@Entity
@Setter
@Getter
@Table(name="student_registered_in_drive")
@NoArgsConstructor
public class StudentRegisteredInDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @ManyToOne
    private Drive drive;

    /**
     * In this list we store student id of alll those students who apply for this particular drive
     */
    @ManyToOne
    private Student student;
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRegisteredInDrive that = (StudentRegisteredInDrive) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "StudentRegistredInDrive{" +
                "id=" + id +
                ", drive=" + drive +
                ", student=" + student +
                '}';
    }
}
