package com.recruitme.domain;

import com.recruitme.enums.JobType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.internal.Pair;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * StudentHoldingOffers class hold various properties of student holding offers
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:50:00 AM
 */
@Entity
@Setter
@Getter
@Table(name="student_holding_offers")
@NoArgsConstructor
public class StudentHoldingOffers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Student student;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private JobType jobType;

    @Column(nullable = false)
    private BigDecimal salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentHoldingOffers that = (StudentHoldingOffers) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "StudentHoldingOffers{" +
                "id=" + id +
                ", student=" + student +
                ", company='" + company + '\'' +
                ", jobType=" + jobType +
                ", salary=" + salary +
                '}';
    }
}
