package com.htksoft.reservation.dto;

import com.htksoft.reservation.entity.LeaveStatus;
import com.htksoft.reservation.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorLeaveRequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<LeaveStatus> statuses;
    private String description;
}
