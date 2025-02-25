package com.htksoft.reservation.entity;

import jakarta.persistence.Table;
import lombok.*;

@jakarta.persistence.Entity
@Table(name = "citys")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City extends Entity {
    private String name;
}
