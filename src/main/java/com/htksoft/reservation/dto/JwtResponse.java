package com.htksoft.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String companyName;
    private String email;

    public JwtResponse(String token, Long id, String companyName, String email) {
        this.token = token;
        this.id = id;
        this.companyName = companyName;
        this.email = email;
    }
} 