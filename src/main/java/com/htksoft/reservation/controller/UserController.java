package com.htksoft.reservation.controller;

import com.htksoft.reservation.core.response.Response;
import com.htksoft.reservation.dto.UserAppointmentRequest;
import com.htksoft.reservation.dto.UserProfileUpdateRequest;
import com.htksoft.reservation.entity.User;
import com.htksoft.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PutMapping("/updateProfile")
    public void updateProfileDetails(@RequestBody UserProfileUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userService.updateProfileDetails(email, request);
    }


    @GetMapping("/getAll")
    public Response<Object> getAll() {
        return userService.getAll();
    }

    @GetMapping("/user/{userId}/available-times")
    public ResponseEntity<List<LocalTime>> getAvailableTimes(@PathVariable Long userId,
                                                             @RequestParam LocalDate date) {
        return ResponseEntity.ok(userService.getAvailableTimes(userId,date));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    @GetMapping("/getAuthUser")
    public ResponseEntity<User> getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getAuthUser(email);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/updateAppointmentsByUser")
    public void updateUserByAppointments(@RequestParam Long apoId ,
                                         @RequestBody UserAppointmentRequest userAppointmentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userService.updateUserByAppointments(userAppointmentRequest, email, apoId);
    }
    @DeleteMapping("/deleteAppointmentsByUser")
    public void deleteAppointmentsByUser(@RequestParam Long apoId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userService.deleteAppointmentsByUser(email,apoId);
    }
}


