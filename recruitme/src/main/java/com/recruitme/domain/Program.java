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
 * Program class hold various properties of program
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:41:00 AM
 */

@Entity
@Setter
@Getter
@Table(name="program")
@NoArgsConstructor
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false,length = 10)
    private String programCode;

    @JsonIgnore
    @OneToMany(mappedBy = "program")
    private List<PlacementPreparationFaculty> placementPreparationFacultyList;

    @JsonIgnore
    @OneToMany(mappedBy = "program")
    private List<PlacementCoordinator> placementCoordinatorList;

    @JsonIgnore
    @OneToMany(mappedBy = "ugProgram")
    private List<Education> ugEducationList;

    @JsonIgnore
    @OneToMany(mappedBy = "pgProgram")
    private List<Education> pgEducationList;

    @JsonIgnore
    @OneToMany(mappedBy = "program")
    private List<Branch> branchList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return getId().equals(program.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", programCode='" + programCode + '\'' +
                ", placementPreparationFacultyList=" + placementPreparationFacultyList +
                ", placementCoordinatorList=" + placementCoordinatorList +
                ", ugEducationList=" + ugEducationList +
                ", pgEducationList=" + pgEducationList +
                ", branchList=" + branchList +
                '}';
    }
}
