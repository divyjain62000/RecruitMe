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
 * State class hold various properties of state
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:43:00 AM
 */

@Entity
@Setter
@Getter
@Table(name="state")
@NoArgsConstructor
public class State {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false,length = 100)
    private String name;

    @ManyToOne
    private Country country;

    @JsonIgnore
    @OneToMany(mappedBy = "state")
    private List<Address> addresses;

    @JsonIgnore
    @OneToMany(mappedBy = "state")
    private List<City> cityList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return getId().equals(state.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                ", addresses=" + addresses +
                ", cityList=" + cityList +
                '}';
    }
}
