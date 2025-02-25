package com.htksoft.reservation.controller;

import com.htksoft.reservation.core.response.Response;
import com.htksoft.reservation.service.CityService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/getAll")
    @PermitAll
    public Response<Object> getAll() {
        return cityService.getAll();
    }
}
