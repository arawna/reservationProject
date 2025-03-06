package com.htksoft.reservation.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "appointments")
public class Appointment extends com.htksoft.reservation.entity.Entity {
    @Id
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    @Size(max =11)
    private Long identity_number;
    @Size(max = 20)
    private String first_name;
    @Size(max = 20)
    private String last_name;
    private LocalDate birth_date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;
    private LocalDateTime appointment_datetime;


}
