package com.adminapp.domain;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

//    protected long createdAt = System.currentTimeMillis();

    @Column(columnDefinition = "boolean default true")
    protected boolean active = true;

    public BaseEntity() { }

    public BaseEntity(Long id, long createdAt, boolean active) {
        super();
        this.id = id;
//        this.createdAt = createdAt;
        this.active = active;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
//    public long getCreatedAt() {
//        return createdAt;
//    }
//    public void setCreatedAt(long createdAt) {
//        this.createdAt = createdAt;
//    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}
