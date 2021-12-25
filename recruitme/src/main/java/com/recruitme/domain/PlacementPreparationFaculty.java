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
 * PlacementPreperationFaculty class hold various properties of placement preperation faculty
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:39:00 AM
 */

@Entity
@Setter
@Getter
@Table(name="placement_preperation_faculty")
@NoArgsConstructor
public class PlacementPreparationFaculty {

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
    @OneToMany(mappedBy="uploadBy")
    private List<Video> videoList;

    @JsonIgnore
    @OneToMany(mappedBy = "placementPreparationFaculty")
    private List<ConfirmationToken> confirmationTokenList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlacementPreparationFaculty that = (PlacementPreparationFaculty) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "PlacementPreparationFaculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", primaryEmail='" + primaryEmail + '\'' +
                ", primaryMobileNumber='" + primaryMobileNumber + '\'' +
                ", passwordKey='" + passwordKey + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", program=" + program +
                ", branch=" + branch +
                ", videoList=" + videoList +
                '}';
    }
}
