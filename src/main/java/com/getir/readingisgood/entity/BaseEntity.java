package com.getir.readingisgood.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdDate;

    @Column(name = "updated_date", insertable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedDate;
}
