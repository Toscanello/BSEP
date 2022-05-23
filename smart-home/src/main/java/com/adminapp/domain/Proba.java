package com.adminapp.domain;

import javax.persistence.*;
import java.lang.annotation.Target;

@Entity
@Table(name = "proba")
public class Proba{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pokusaj")
    private String pokusaj;

    public Proba(){}

    public Proba(String pokusaj) {
        this.pokusaj = pokusaj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPokusaj() {
        return pokusaj;
    }

    public void setPokusaj(String pokusaj) {
        this.pokusaj = pokusaj;
    }
}
