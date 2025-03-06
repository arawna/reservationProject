package com.htksoft.reservation.controller;

import com.htksoft.reservation.dto.DoctorLeaveRequestDto;
import com.htksoft.reservation.entity.DoctorLeave;
import com.htksoft.reservation.entity.User;
import com.htksoft.reservation.service.DoctorLeaveService;
import com.htksoft.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/doctorLeave")
@RestController
@RequiredArgsConstructor
public class DoctorLeaveController {
    private final DoctorLeaveService doctorLeaveService;
    private final UserService userService;
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DoctorLeave>> getDoctorLeavesByUserId(@PathVariable Long userId) {
        List<DoctorLeave> leaves = doctorLeaveService.findByUserId(userId);
        return ResponseEntity.ok(leaves);
    }

    @GetMapping("/my-leaves")
    public ResponseEntity<List<DoctorLeave>> getMyLeaves() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getAuthUser(email);
        List<DoctorLeave> leaves = doctorLeaveService.findByUserId(user.getId());
        return ResponseEntity.ok(leaves);
    }
    @PostMapping("/addLeave")
    public void addLeave(DoctorLeaveRequestDto doctorLeaveRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        doctorLeaveService.addLeave(doctorLeaveRequestDto,email);
    }
    @PutMapping("/updateLeave/{leaveId}")
    public void updateLeave(@RequestBody DoctorLeaveRequestDto doctorLeaveRequestDto,
                            @PathVariable Long leaveId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        doctorLeaveService.updateLeave(doctorLeaveRequestDto, email, leaveId);
    }
    @DeleteMapping("/deleteLeave")
    public void deleteLeave(@RequestParam Long leaveId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        doctorLeaveService.deleteLeave(email,leaveId);

    }
}

