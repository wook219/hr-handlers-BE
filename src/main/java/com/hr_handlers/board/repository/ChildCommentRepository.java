package com.hr_handlers.board.repository;

import com.hr_handlers.board.entity.ChildComment;
import com.hr_handlers.board.entity.ParentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildCommentRepository extends JpaRepository<ChildComment, Long> {
    List<ChildComment> findByParentCommentAndIsDelete(ParentComment parentComment, String isDelete);
}
