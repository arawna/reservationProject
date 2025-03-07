package com.htksoft.reservation.repository;

import com.htksoft.reservation.entity.Appointment;
import com.htksoft.reservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment a WHERE a.user_id = :userId AND a.appointment_datetime BETWEEN :startDate AND :endDate")
    List<Appointment> findAppointmentsByUserIdAndDateTimeBetween(
            @Param("userId") User userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


}
