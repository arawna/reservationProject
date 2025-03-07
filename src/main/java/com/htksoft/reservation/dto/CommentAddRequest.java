package com.htksoft.reservation.dto;

import com.htksoft.reservation.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentAddRequest {
    private Long userId;
    private String commenterName;
    private String comment;

}
