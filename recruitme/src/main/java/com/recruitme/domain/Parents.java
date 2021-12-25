package com.recruitme.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


/**
 * Parents class hold various properties of parents
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:35:00 AM
 */

@Entity
@Setter
@Getter
@Table(name = "parents")
@NoArgsConstructor
public class Parents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false,length = 100)
    private String motherName;

    @Column(nullable = false,length = 100)
    private String fatherName;

    @Column(nullable = false,length = 10)
    private String mobileNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "parents")
    private List<Student> students;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parents parents = (Parents) o;
        return getId().equals(parents.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Parents{" +
                "id=" + id +
                ", motherName='" + motherName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", students=" + students +
                '}';
    }
}
