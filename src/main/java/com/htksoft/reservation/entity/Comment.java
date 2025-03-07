package com.htksoft.reservation.entity;

import com.htksoft.reservation.dto.CommentDto;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String commenterName;

    private String comment;

    private LocalDate commentDate;

    public CommentDto toDto() {
        return CommentDto.builder()
                .id(this.id)
                .userId(this.user.getId())
                .commenterName(this.commenterName)
                .comment(this.comment)
                .commentDate(this.commentDate)
                .build();
    }

}
