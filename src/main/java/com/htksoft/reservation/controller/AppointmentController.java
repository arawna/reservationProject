package com.htksoft.reservation.controller;

import com.htksoft.reservation.dto.AppointmentReservationRequest;
import com.htksoft.reservation.dto.UserAppointmentRequest;
import com.htksoft.reservation.entity.User;
import com.htksoft.reservation.service.AppointmentService;
import com.htksoft.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserService userService;

    @PostMapping("/addReservation")
    public void appointmentAdd(@RequestBody AppointmentReservationRequest request){
         this.appointmentService.appointmentAdd(request);
    }

    @GetMapping("/available-times/{userId}")
    public ResponseEntity<List<LocalTime>> getAvailableTimes(
            @PathVariable Long userId,
            @RequestParam LocalDate date) {
        List<LocalTime> availableTimes = userService.getAvailableTimes(userId, date);
        return ResponseEntity.ok(availableTimes);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Void> updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody UserAppointmentRequest request,
            Authentication authentication) {
        userService.updateUserByAppointments(request, authentication.getName(), appointmentId);
        return ResponseEntity.ok().build();
    }



}

