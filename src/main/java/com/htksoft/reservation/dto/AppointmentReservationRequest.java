package com.htksoft.reservation.dto;

import com.htksoft.reservation.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentReservationRequest {
    private Long identity_number;
    private String first_name;
    private String last_name;
    private LocalDate birth_date;
    private User user_id;
    private LocalDateTime appointment_datetime;

}
