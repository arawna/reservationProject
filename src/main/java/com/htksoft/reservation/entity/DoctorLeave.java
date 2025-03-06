package com.htksoft.reservation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@jakarta.persistence.Entity
@Table(name = "doctor_leaves")
public class DoctorLeave extends Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate startDate;
    private LocalDate endDate;

    @ElementCollection(targetClass = LeaveStatus.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "doctor_leave_statuses", joinColumns = @JoinColumn(name = "leave_id"))
    @Column(name = "leave_status")
    private Set<LeaveStatus> statuses;

    private String description;
}
