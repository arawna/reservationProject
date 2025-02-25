package com.htksoft.reservation.service;

import com.htksoft.reservation.core.response.Response;
import com.htksoft.reservation.dto.CityDto;
import com.htksoft.reservation.entity.City;
import com.htksoft.reservation.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public Response<Object> getAll() {

        List<CityDto> cities = cityRepository.findAll().stream().map(City::toDto).collect(Collectors.toList());

        return Response.builder()
                .statusCode(200)
                .message("Data Listed")
                .messageCode("success")
                .data(cities)
                .build();
    }
}
