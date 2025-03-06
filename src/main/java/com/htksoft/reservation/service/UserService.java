package com.htksoft.reservation.service;

import com.htksoft.reservation.core.response.Response;
import com.htksoft.reservation.dto.UserAppointmentRequest;
import com.htksoft.reservation.dto.UserDto;
import com.htksoft.reservation.dto.UserProfileUpdateRequest;
import com.htksoft.reservation.entity.Appointment;
import com.htksoft.reservation.entity.User;
import com.htksoft.reservation.repository.AppointmentRepository;
import com.htksoft.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.htksoft.reservation.security.JwtUtils;

import java.security.PrivateKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtils jwt;
    private final AppointmentRepository appointmentRepository;

    public void updateProfileDetails(String email, UserProfileUpdateRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        user.setReservationDuration(request.getReservationDuration());
        user.setBreakAfterReservation(request.getBreakAfterReservation());
        user.setWorkStartTime(request.getWorkStartTime());
        user.setWorkEndTime(request.getWorkEndTime());
        user.setLunchStartTime(request.getLunchStartTime());
        user.setLunchEndTime(request.getLunchEndTime());
        user.setProfilePhoto(request.getProfilePhoto());
        user.setWorkDays(request.getWorkDays());
        userRepository.save(user);
    }

    public Response<Object> getAll() {

        List<UserDto> userDtoList = userRepository.findAll().stream()
                .map(User::toDto)
                .toList();


        return Response.builder()
                .statusCode(200)
                .message("Users Listed Successfully")
                .messageCode("success")
                .data(userDtoList)
                .build();
    }

    public List<LocalTime> getAvailableTimes(Long userId, LocalDate date) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));


        List<LocalTime> availableTimes = user.availableHours(date);


        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<Appointment> existingAppointments = appointmentRepository
                .findAppointmentsByUserIdAndDateTimeBetween(user, startOfDay, endOfDay);

        for (Appointment appointment : existingAppointments) {
            LocalTime appointmentTime = appointment.getAppointment_datetime().toLocalTime();
            availableTimes.removeIf(time -> time.equals(appointmentTime));
        }

        return availableTimes;
    }
    public Optional<User> getUserById(Long userId){
        Optional<User> userList = userRepository.findById(userId);
        return userList;
    }

    public void updateUserByAppointments(UserAppointmentRequest userAppointmentRequest,String email,Long apoId){
        Appointment appointment = this.appointmentRepository.findById(apoId)
                .orElseThrow(() -> new RuntimeException("Yokki böyle bir Randevu"));

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));


        appointment.setIdentity_number(userAppointmentRequest.getIdentity_number());
        appointment.setFirst_name(userAppointmentRequest.getFirst_name());
        appointment.setLast_name(userAppointmentRequest.getLast_name());
        appointment.setAppointment_datetime(userAppointmentRequest.getAppointment_datetime());

        appointmentRepository.save(appointment);

    }

    public User getAuthUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + email));
    }
    public void deleteAppointmentsByUser(String email,Long apoId){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        Appointment appointment = this.appointmentRepository.findById(apoId)
                .orElseThrow(() -> new RuntimeException("Yokki böyle bir Randevu"));

        appointmentRepository.deleteById(apoId);
    }





}


