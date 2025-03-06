package com.htksoft.reservation.entity;

import com.htksoft.reservation.dto.CityDto;
import com.htksoft.reservation.dto.UserDto;
import com.htksoft.reservation.repository.AppointmentRepository;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;


import java.time.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "companyName"),
                @UniqueConstraint(columnNames = "email")
        })

public class User extends com.htksoft.reservation.entity.Entity {
    @NotBlank
    @Size(max = 20)
    private String companyName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;


    private Duration reservationDuration;
    private Duration breakAfterReservation;
    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private LocalTime lunchStartTime;
    private LocalTime lunchEndTime;
    private String profilePhoto;
    @ElementCollection(targetClass = WorkDay.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_work_days", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "work_day")
    private Set<WorkDay> workDays;


    public UserDto toDto() {
        return UserDto.builder()
                .companyName(this.getCompanyName())
                .email(this.email)
                .branch(this.branch)
                .city(this.city)
                .build();
    }

    private WorkDay convertDayOfWeekToWorkDay(java.time.DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return WorkDay.MONDAY;
            case TUESDAY:
                return WorkDay.TUESDAY;
            case WEDNESDAY:
                return WorkDay.WEDNESDAY;
            case THURSDAY:
                return WorkDay.THURSDAY;
            case FRIDAY:
                return WorkDay.FRIDAY;
            case SATURDAY:
                return WorkDay.SATURDAY;
            case SUNDAY:
                return WorkDay.SUNDAY;
            default:
                throw new IllegalArgumentException("Geçersiz gün: " + dayOfWeek);
        }
    }

    public List<LocalTime> availableHours(LocalDate date) {
        List<LocalTime> availableTimes = new ArrayList<>();

        WorkDay selectedDay = convertDayOfWeekToWorkDay(date.getDayOfWeek());
        if (!workDays.contains(selectedDay)) {
            return availableTimes; // boş liste dön
        }

        for (LocalTime currentTime = workStartTime; currentTime.isBefore(workEndTime); currentTime = currentTime.plus(reservationDuration)) {
            if (currentTime.isBefore(lunchStartTime) || currentTime.isAfter(lunchEndTime)) {
                availableTimes.add(currentTime);
            }
        }

        return availableTimes;
    }










}