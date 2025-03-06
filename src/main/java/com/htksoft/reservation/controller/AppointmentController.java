package com.htksoft.reservation.controller;

import com.htksoft.reservation.dto.AppointmentReservationRequest;
import com.htksoft.reservation.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/addReservation")
    public void appointmentAdd(@RequestBody AppointmentReservationRequest request){
         this.appointmentService.appointmentAdd(request);
    }
}
