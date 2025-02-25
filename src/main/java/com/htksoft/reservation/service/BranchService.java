package com.htksoft.reservation.service;

import com.htksoft.reservation.core.response.Response;
import com.htksoft.reservation.dto.BranchDto;
import com.htksoft.reservation.entity.Branch;
import com.htksoft.reservation.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    public Response<Object> getAll() {

        List<BranchDto> branchDtoList = branchRepository.findAll().stream().map(Branch::toDto).toList();

        return Response.builder()
                .statusCode(200)
                .message("Data Listed")
                .messageCode("success")
                .data(branchDtoList)
                .build();
    }

}
