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
 * Branch class hold various properties of branch
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:15:00 AM
 */

@Entity
@Setter
@Getter
@Table(name="branch")
@NoArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false,length = 500)
    private String name;

    @Column(nullable = false,length = 30)
    private String branchCode;

    @ManyToOne
    private Program program;

    @JsonIgnore
    @OneToMany(mappedBy = "branch")
    private List<PlacementPreparationFaculty> placementPreparationFacultyList;

    @JsonIgnore
    @OneToMany(mappedBy = "branch")
    private List<PlacementCoordinator> placementCoordinatorList;

    @JsonIgnore
    @OneToMany(mappedBy = "ugBranch")
    private List<Education> ugEducationList;


    @JsonIgnore
    @OneToMany(mappedBy = "pgBranch")
    private List<Education> pgEducationList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return getId().equals(branch.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", program=" + program +
                ", placementPreparationFacultyList=" + placementPreparationFacultyList +
                ", placementCoordinatorList=" + placementCoordinatorList +
                ", ugEducationList=" + ugEducationList +
                ", pgEducationList=" + pgEducationList +
                '}';
    }
}
