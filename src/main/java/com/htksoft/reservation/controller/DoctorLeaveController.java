package com.htksoft.reservation.controller;

import com.htksoft.reservation.dto.DoctorLeaveRequestDto;
import com.htksoft.reservation.entity.DoctorLeave;
import com.htksoft.reservation.entity.User;
import com.htksoft.reservation.service.DoctorLeaveService;
import com.htksoft.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("api/v1/doctorLeave")
@RestController
@RequiredArgsConstructor
public class DoctorLeaveController {
    private final DoctorLeaveService doctorLeaveService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> addLeave(@RequestBody DoctorLeaveRequestDto doctorLeaveRequestDto,
                                         Authentication authentication) {
        doctorLeaveService.addLeave(doctorLeaveRequestDto, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{leaveId}")
    public ResponseEntity<Void> updateLeave(@PathVariable Long leaveId,
                                            @RequestBody DoctorLeaveRequestDto doctorLeaveRequestDto,
                                            Authentication authentication) {
        doctorLeaveService.updateLeave(doctorLeaveRequestDto, authentication.getName(), leaveId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{leaveId}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long leaveId,
                                            Authentication authentication) {
        doctorLeaveService.deleteLeave(authentication.getName(), leaveId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DoctorLeave>> getLeavesByUserId(@PathVariable Long userId) {
        List<DoctorLeave> leaves = doctorLeaveService.findByUserId(userId);
        return ResponseEntity.ok(leaves);
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<DoctorLeave>> getLeavesByDateRange(
            @PathVariable Long userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<DoctorLeave> leaves = doctorLeaveService.getLeavesByDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(leaves);
    }
}

