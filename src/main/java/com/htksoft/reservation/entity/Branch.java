package com.htksoft.reservation.entity;

import com.htksoft.reservation.dto.BranchDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@jakarta.persistence.Entity
@Table(name = "branches")
public class Branch extends Entity {

    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public BranchDto toDto() {
        return BranchDto.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
