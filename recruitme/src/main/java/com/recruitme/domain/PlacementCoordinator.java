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
 * PlacementCoordinator class hold various properties of placement coordinator
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:37:00 AM
 */

@Entity
@Setter
@Getter
@Table(name="placement_coordinator")
@NoArgsConstructor
public class PlacementCoordinator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,length =100)
    private String name;

    @Column(nullable = false, length=30, unique = true)
    private String primaryEmail;

    @Column(nullable = false,length = 10,unique = true)
    private String primaryMobileNumber;

    @Column(nullable = false)
    private String passwordKey;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,length=1)
    private Character gender;

    @ManyToOne
    private Program program;

    @ManyToOne
    private Branch branch;

    @JsonIgnore
    @OneToMany(mappedBy = "uploadedBy")
    private List<Drive> driveList;

    @JsonIgnore
    @OneToMany(mappedBy = "placementCoordinator")
    private List<ConfirmationToken> confirmationTokenList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlacementCoordinator that = (PlacementCoordinator) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "PlacementCoordinator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", primaryEmail='" + primaryEmail + '\'' +
                ", primaryMobileNumber='" + primaryMobileNumber + '\'' +
                ", passwordKey='" + passwordKey + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", program=" + program +
                ", branch=" + branch +
                ", driveList=" + driveList +
                '}';
    }
}
