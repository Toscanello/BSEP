package com.adminapp.domain;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
//@Table(name = "users")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
//@Where(clause = "deleted = false")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

//    @Column(columnDefinition = "boolean default false", nullable = false)
//    private boolean deleted;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private UserType type;

}
