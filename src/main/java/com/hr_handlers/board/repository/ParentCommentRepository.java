package com.hr_handlers.board.repository;

import com.hr_handlers.board.entity.ParentComment;
import com.hr_handlers.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentCommentRepository extends JpaRepository<ParentComment, Long> {
    List<ParentComment> findByPostAndIsDelete(Post post, String isDelete);
}
