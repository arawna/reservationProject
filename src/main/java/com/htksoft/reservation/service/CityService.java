package com.htksoft.reservation.service;

import com.htksoft.reservation.core.response.Response;
import com.htksoft.reservation.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public Response<Object> getAll() {
        return Response.builder()
                .statusCode(200)
                .message("Data Listed")
                .messageCode("success")
                .data(cityRepository.findAll())
                .build();
    }
}
