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
 * City class hold various properties of city
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:18:00 AM
 */

@Entity
@Setter
@Getter
@Table(name="city")
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false,length = 100)
    private String name;

    @ManyToOne
    private State state;

    @JsonIgnore
    @OneToMany(mappedBy = "city")
    private List<Address> addressList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return getId().equals(city.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", addressList=" + addressList +
                '}';
    }
}
