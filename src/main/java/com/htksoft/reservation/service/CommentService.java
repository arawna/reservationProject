package com.htksoft.reservation.service;

import com.htksoft.reservation.dto.CommentAddRequest;
import com.htksoft.reservation.entity.Comment;
import com.htksoft.reservation.entity.User;
import com.htksoft.reservation.repository.CommentRepository;
import com.htksoft.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public void addComment(CommentAddRequest commentAddRequest){
      User user = userRepository.findById(commentAddRequest.getUserId()).orElseThrow(() -> new  RuntimeException("USER YOK"));
      Comment comment = new Comment();
      comment.setUser(this.userRepository.findById(commentAddRequest.getUserId()).get());
      comment.setCommenterName(commentAddRequest.getCommenterName());
      comment.setComment(commentAddRequest.getComment());
      comment.setCommentDate(LocalDate.now());
      commentRepository.save(comment);

    }

    public List<Comment> getUserComments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        return commentRepository.findByUserId(userId);
    }

    public void deleteCommentUser(Long commentId,String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Sıkıntııı user bulunamadı"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment bulunamadı"));
        if(!comment.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Hayırdır?");
        }
        commentRepository.deleteById(commentId);
    }


}
