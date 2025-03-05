package com.htksoft.reservation.dto;

import com.htksoft.reservation.entity.WorkDay;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileUpdateRequest {


    @NotNull
    private Duration reservationDuration;
    @NotNull
    private Duration breakAfterReservation;
    @NotNull
    private LocalTime workStartTime;
    @NotNull
    private LocalTime workEndTime;
    @NotNull
    private LocalTime lunchStartTime;
    @NotNull
    private LocalTime lunchEndTime;
    @NotNull
    private String profilePhoto;
    @NotNull
    private Set<WorkDay> workDays;




}
