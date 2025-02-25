package com.htksoft.reservation.entity;

import com.htksoft.reservation.dto.CityDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@jakarta.persistence.Entity
@Table(name = "citys")
public class City extends Entity {

    @Id
    @Column(nullable = false)
    private Long id; // Plaka kodu için

    @Column(nullable = false, unique = true)
    private String name; // Şehir adı

    public CityDto toDto() {
        return CityDto.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
