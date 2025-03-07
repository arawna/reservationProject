package com.htksoft.reservation.repository;

import com.htksoft.reservation.dto.AppointmentReservationRequest;
import com.htksoft.reservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByCompanyName(String companyName);
    Boolean existsByEmail(String email);
    Boolean existsByCompanyName(String companyName);


} 