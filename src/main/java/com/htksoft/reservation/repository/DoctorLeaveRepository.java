package com.htksoft.reservation.repository;

import com.htksoft.reservation.entity.DoctorLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorLeaveRepository extends JpaRepository<DoctorLeave,Long> {
    List<DoctorLeave> findByUserId(Long userId);
    List<DoctorLeave> findByUserIdAndStartDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT CASE WHEN COUNT(dl) > 0 THEN true ELSE false END FROM DoctorLeave dl " +
            "WHERE dl.user.id = :userId AND :date BETWEEN dl.startDate AND dl.endDate")
    boolean existsByUserIdAndDateBetween(@Param("userId") Long userId, @Param("date") LocalDate date);
    // userin günlerini getiren method yazılıcak(mesela müsait 1 hafta)
    // userin izne cıktığı gün tüm randevular başka günlere saatlere bölünücek ama sırasıyla olucak
}
