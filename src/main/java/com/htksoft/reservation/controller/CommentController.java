package com.htksoft.reservation.controller;

import com.htksoft.reservation.dto.CommentAddRequest;
import com.htksoft.reservation.dto.CommentDto;
import com.htksoft.reservation.entity.Comment;
import com.htksoft.reservation.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

@PostMapping("/addComment")
    public void addComment(CommentAddRequest commentAddRequest){
        commentService.addComment(commentAddRequest);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getUserComments(@PathVariable Long userId) {
        List<Comment> comments = commentService.getUserComments(userId);
        List<CommentDto> commentDtos = comments.stream()
                .map(Comment::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDtos);
    }

    @DeleteMapping("/commentDeleteByAuth")
    public void deleteCommentUser(Long commentId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        commentService.deleteCommentUser(commentId,email);
    }
}
