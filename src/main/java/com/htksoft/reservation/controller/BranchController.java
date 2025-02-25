package com.htksoft.reservation.controller;

import com.htksoft.reservation.core.response.Response;
import com.htksoft.reservation.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/getAll")
    public Response<Object> getAll() {
        return branchService.getAll();
    }
}
