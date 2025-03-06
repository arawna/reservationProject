package com.htksoft.reservation.service;

import com.htksoft.reservation.dto.DoctorLeaveRequestDto;
import com.htksoft.reservation.entity.Appointment;
import com.htksoft.reservation.entity.DoctorLeave;
import com.htksoft.reservation.entity.User;
import com.htksoft.reservation.repository.DoctorLeaveRepository;
import com.htksoft.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorLeaveService {
    private final DoctorLeaveRepository doctorLeaveRepository;
    private final UserRepository userRepository;

    public List<DoctorLeave> findByUserId(Long userId) {
        return doctorLeaveRepository.findByUserId(userId);
    }


    public List<DoctorLeave> getLeavesByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return doctorLeaveRepository.findByUserIdAndStartDateBetween(userId, startDate, endDate);
    }

    public void addLeave(DoctorLeaveRequestDto doctorLeaveRequestDto,String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Sıkıntııı user bulunamadı"));
        DoctorLeave doctorLeave = new DoctorLeave();
        doctorLeave.setUser(user);
        doctorLeave.setStartDate(doctorLeaveRequestDto.getStartDate());
        doctorLeave.setEndDate(doctorLeaveRequestDto.getEndDate());
        doctorLeave.setStatuses(doctorLeaveRequestDto.getStatuses());
        doctorLeave.setDescription(doctorLeaveRequestDto.getDescription());
        doctorLeaveRepository.save(doctorLeave);
    }
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

}
