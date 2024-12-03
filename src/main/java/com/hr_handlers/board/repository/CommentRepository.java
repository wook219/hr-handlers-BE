package com.hr_handlers.board.repository;

import com.hr_handlers.board.entity.Comment;
import com.hr_handlers.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostAndParentIsNullAndIsDelete(Post post, String isDelete);
    List<Comment> findByPostAndIsDelete(Post post, String isDelete);
}
