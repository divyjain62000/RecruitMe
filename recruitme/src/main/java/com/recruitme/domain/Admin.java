package com.recruitme.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;


/**
 *Admin class hold various properties of admin
 * @author Divy Jain
 * @version 1.0
 * @since Sept 01,2021 11:13:00 AM
 */
@Entity
@Setter
@Getter
@Table(name="admin")
@NoArgsConstructor
public class Admin {


        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;

        @Column(nullable = false,length = 25,unique = true)
        private String username;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String passwordKey;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Admin admin = (Admin) o;
                return getId().equals(admin.getId());
        }

        @Override
        public int hashCode() {
                return Objects.hash(getId());
        }

        @Override
        public String toString() {
                return "Admin{" +
                        "id=" + id +
                        ", username='" + username + '\'' +
                        ", password='" + password + '\'' +
                        ", passwordKey='" + passwordKey + '\'' +
                        '}';
        }
}
