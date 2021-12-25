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
 * Country class hold various properties of country
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:20:00 AM
 */

@Entity
@Setter
@Getter
@Table(name="country")
@NoArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false,length = 2)
    private String countryCode;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(nullable = false,length = 4)
    private String phoneCode;

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private List<Address> addressList;

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private List<State> stateList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return getId().equals(country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryCode='" + countryCode + '\'' +
                ", name='" + name + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                ", addressList=" + addressList +
                ", stateList=" + stateList +
                '}';
    }
}
