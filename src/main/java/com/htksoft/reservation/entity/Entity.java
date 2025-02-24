package com.htksoft.reservation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; // Otomatik atanır

    @CreatedBy
    @Column(updatable = false)
    private String createdBy; // Auditing ile atanır

    @UpdateTimestamp
    private LocalDateTime updatedAt; // Otomatik güncellenir

    @LastModifiedBy
    private String updatedBy; // Auditing ile atanır

    private boolean isDeleted = false; // Varsayılan olarak false
}
