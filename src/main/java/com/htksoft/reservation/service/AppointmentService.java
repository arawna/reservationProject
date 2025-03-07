package com.htksoft.reservation.service;

import com.htksoft.reservation.dto.AppointmentReservationRequest;
import com.htksoft.reservation.entity.Appointment;
import com.htksoft.reservation.entity.User;
import com.htksoft.reservation.repository.AppointmentRepository;
import com.htksoft.reservation.repository.DoctorLeaveRepository;
import com.htksoft.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorLeaveRepository doctorLeaveRepository;

    //    public void appointmentAdd(AppointmentReservationRequest request) {
//        User user = userRepository.findById(request.getUser_id().getId())
//                .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));
//        LocalDateTime appointmentDateTime = request.getAppointment_datetime();
//        //-
//        LocalDate appointmentDate = appointmentDateTime.toLocalDate();
//        LocalTime appointmentTime = appointmentDateTime.toLocalTime();
//        LocalDateTime startOfDay = appointmentDate.atStartOfDay();
//        LocalDateTime endOfDay = appointmentDate.atTime(23, 59, 59);
//      //--
//        List<Appointment> existingAppointments = appointmentRepository
//                .findAppointmentsByUserIdAndDateTimeBetween(user, startOfDay, endOfDay);
//        boolean isTimeSlotTaken = existingAppointments.stream()
//                .anyMatch(apt -> apt.getAppointment_datetime().toLocalTime().equals(appointmentTime));
//        if (isTimeSlotTaken) {
//            throw new RuntimeException("Bu saat dolu! Başka bir saat seçiniz.");
//        }
//        Appointment appointment = new Appointment();
//        appointment.setIdentity_number(request.getIdentity_number());
//        appointment.setFirst_name(request.getFirst_name());
//        appointment.setLast_name(request.getLast_name());
//        appointment.setBirth_date(request.getBirth_date());
//        appointment.setUser_id(user);
//        appointment.setAppointment_datetime(appointmentDateTime);
//
//        appointmentRepository.save(appointment);
//    }
public void appointmentAdd(AppointmentReservationRequest request) {
    User user = userRepository.findById(request.getUser_id().getId())
            .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));
    LocalDateTime appointmentDateTime = request.getAppointment_datetime();

    // Tarih ve saat bilgilerini ayır
    LocalDate appointmentDate = appointmentDateTime.toLocalDate();
    LocalTime appointmentTime = appointmentDateTime.toLocalTime();

    // Doktorun o gün çalışıp çalışmadığını kontrol et
    List<LocalTime> availableTimes = user.availableHours(appointmentDate);
    if (availableTimes.isEmpty()) {
        throw new RuntimeException("Doktor bu tarihte çalışmıyor!");
    }

    // Seçilen saatin doktorun çalışma saatleri içinde olup olmadığını kontrol et
    boolean isTimeAvailable = availableTimes.stream()
            .anyMatch(time -> time.equals(appointmentTime));
    if (!isTimeAvailable) {
        throw new RuntimeException("Seçilen saat doktorun çalışma saatleri dışında!");
    }

    // Doktorun o gün izinli olup olmadığını kontrol et
    boolean isDoctorOnLeave = doctorLeaveRepository.existsByUserIdAndDateBetween(
            user.getId(), appointmentDate);
    if (isDoctorOnLeave) {
        throw new RuntimeException("Doktor bu tarihte izinli!");
    }

    // Randevunun dolu olup olmadığını kontrol et
    LocalDateTime startOfDay = appointmentDate.atStartOfDay();
    LocalDateTime endOfDay = appointmentDate.atTime(23, 59, 59);

    List<Appointment> existingAppointments = appointmentRepository
            .findAppointmentsByUserIdAndDateTimeBetween(user, startOfDay, endOfDay);
    boolean isTimeSlotTaken = existingAppointments.stream()
            .anyMatch(apt -> apt.getAppointment_datetime().toLocalTime().equals(appointmentTime));
    if (isTimeSlotTaken) {
        throw new RuntimeException("Bu saat dolu! Başka bir saat seçiniz.");
    }

    // Randevuyu kaydet
    Appointment appointment = new Appointment();
    appointment.setIdentity_number(request.getIdentity_number());
    appointment.setFirst_name(request.getFirst_name());
    appointment.setLast_name(request.getLast_name());
    appointment.setBirth_date(request.getBirth_date());
    appointment.setUser_id(user);
    appointment.setAppointment_datetime(appointmentDateTime);

    appointmentRepository.save(appointment);
}


}






