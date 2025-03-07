package com.htksoft.reservation.service;

import com.htksoft.reservation.dto.DoctorLeaveRequestDto;
import com.htksoft.reservation.entity.Appointment;
import com.htksoft.reservation.entity.DoctorLeave;
import com.htksoft.reservation.entity.User;
import com.htksoft.reservation.repository.AppointmentRepository;
import com.htksoft.reservation.repository.DoctorLeaveRepository;
import com.htksoft.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.http11.Constants;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorLeaveService {
    private final DoctorLeaveRepository doctorLeaveRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    public List<DoctorLeave> findByUserId(Long userId) {
        return doctorLeaveRepository.findByUserId(userId);
    }


    public List<DoctorLeave> getLeavesByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return doctorLeaveRepository.findByUserIdAndStartDateBetween(userId, startDate, endDate);
    }

    public void addLeave(DoctorLeaveRequestDto doctorLeaveRequestDto,String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Sıkıntııı user bulunamadı"));
//İZİN RANDEVULARINI BUL
        List<Appointment> affectedAppointments = findAppointmentsInLeaveRange(
        user.getId(),
                doctorLeaveRequestDto.getStartDate(),
                doctorLeaveRequestDto.getEndDate()
        );
        //BULUNAN RANDEVULAR VARSA
        if(!affectedAppointments.isEmpty()){
            System.out.println("İzin tarihlerinde " + affectedAppointments.size() + " randevu bulundu.");
   // Randevuları yeniden planla
            rescheduleAppointments(affectedAppointments, user, doctorLeaveRequestDto.getEndDate());
        }

        DoctorLeave doctorLeave = new DoctorLeave();
        doctorLeave.setUser(user);
        doctorLeave.setStartDate(doctorLeaveRequestDto.getStartDate());
        doctorLeave.setEndDate(doctorLeaveRequestDto.getEndDate());
        doctorLeave.setStatuses(doctorLeaveRequestDto.getStatuses());
        doctorLeave.setDescription(doctorLeaveRequestDto.getDescription());
        doctorLeaveRepository.save(doctorLeave);
    }

    //--
    public void updateLeave(DoctorLeaveRequestDto doctorLeaveRequestDto,String email,Long leaveId){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Sıkıntııı user bulunamadı"));
        DoctorLeave doctorLeave = doctorLeaveRepository.findById(leaveId).orElseThrow(() -> new RuntimeException("izin yok böyle bi"));

        if(!doctorLeave.getUser().getId().equals(user.getId())){
            throw new RuntimeException("yetkin yok");
        }
        doctorLeave.setStartDate(doctorLeaveRequestDto.getStartDate());
        doctorLeave.setEndDate(doctorLeaveRequestDto.getEndDate());
        doctorLeave.setStatuses(doctorLeaveRequestDto.getStatuses());
        doctorLeave.setDescription(doctorLeaveRequestDto.getDescription());
        doctorLeaveRepository.save(doctorLeave);
    }
    public void deleteLeave(String email,Long leaveId){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user yook"));
        DoctorLeave doctorLeave = this.doctorLeaveRepository.findById(leaveId).orElseThrow(() -> new RuntimeException("İzin yok"));

        if(!doctorLeave.getUser().getId().equals(user.getId())){
            throw new RuntimeException("yetkin yok");
        }
        doctorLeaveRepository.deleteById(leaveId);
    }
    //07.03.2025
    public List<Appointment>findAppointmentsInLeaveRange(Long userId,LocalDate startDate,LocalDate endDate){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user yok"));

        //localdatetimeye ceviriyor
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23,59,59);

        // tarih aralığındaki randevuları getiriyor
        return appointmentRepository.findAppointmentsByUserIdAndDateTimeBetween(
                user,startDateTime,endDateTime
        );
    }


    //Can alıcı nokta

    public void rescheduleAppointments (List<Appointment> appointments , User doctor, LocalDate leaveEndDate){
        //izin bitiş tarihinden sonrası günleri kontrol

        LocalDate startCheckDate = leaveEndDate.plusDays(1);

        for(Appointment appointment:appointments){
            boolean rescheduled = false;
            LocalDate checkDate = startCheckDate;

            //en fazla 30 gün ileriye kadar kontrol et
            for (int i = 0;i<30&&!rescheduled;i++){
                // doktorun çalışma günümü
                List<LocalTime> availableTimes = doctor.availableHours(checkDate);

                if(!availableTimes.isEmpty()){
                    //o gün için mevcut randevuları kontrol et
                    LocalDateTime startOfDay = checkDate.atStartOfDay();
                    LocalDateTime endOfDay = checkDate.atTime(23,59,59);

                    List<Appointment> existingAppointments = appointmentRepository
                            .findAppointmentsByUserIdAndDateTimeBetween(doctor, startOfDay, endOfDay);

                    //mevcut randevuların saatlerini cıkart
                    for(Appointment existingAppointment : existingAppointments){
                        LocalTime appointmentTime = existingAppointment.getAppointment_datetime().toLocalTime();
                        availableTimes.removeIf(time->time.equals(appointmentTime));

                    }
                    //Eğer Müsait Bir Saat varsa randevuyu tekrar planla

                    if(!availableTimes.isEmpty()){
                        LocalTime newTime = availableTimes.get(0);// ilk müsait saateal
                        LocalDateTime newDateTime= checkDate.atTime(newTime);

                        //randevu güncelle
                        appointment.setAppointment_datetime(newDateTime);
                        appointmentRepository.save(appointment);

                        rescheduled=true;
                        System.out.println("Randevu yeniden planlandı: " + appointment.getId() +
                                " - Yeni tarih/saat: " + newDateTime);
                    }

                }

                    checkDate = checkDate.plusDays(1);
            }
            if(!rescheduled){
                System.out.println("Randevu için uygun zaman bulunamadı: " + appointment.getId());
                // Burada bildirim gönderilebilir veya başka bir işlem yapılabilir
            }
        }

    }



}
