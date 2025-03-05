package com.htksoft.reservation.service;

import com.htksoft.reservation.core.response.Response;
import com.htksoft.reservation.dto.UserDto;
import com.htksoft.reservation.dto.UserProfileUpdateRequest;
import com.htksoft.reservation.entity.Appointment;
import com.htksoft.reservation.entity.User;
import com.htksoft.reservation.repository.AppointmentRepository;
import com.htksoft.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.htksoft.reservation.security.JwtUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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


}


