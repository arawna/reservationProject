package com.htksoft.reservation.dto;

import com.htksoft.reservation.entity.Branch;
import com.htksoft.reservation.entity.City;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String companyName;
    private String email;
    private City city;
    private Branch branch;
}
