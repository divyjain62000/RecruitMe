package com.recruitme.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * Address class hold various properties of address
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:10:00 AM
 */
@Entity
@Setter
@Getter
@Table(name="address")
@NoArgsConstructor
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false,length=300)
    private String addressLine1;

    @Column(length=300)
    private String addressLine2;

    @ManyToOne
    private City city;

    @ManyToOne
    private State state;

    @ManyToOne
    private Country country;

    @Column(nullable = false)
    private String pinCode;

    @JsonIgnore
    @OneToMany(mappedBy = "address")
    private List<Student> students;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return getId().equals(address.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city=" + city +
                ", state=" + state +
                ", country=" + country +
                ", pinCode=" + pinCode +
                ", students=" + students +
                '}';
    }
}
