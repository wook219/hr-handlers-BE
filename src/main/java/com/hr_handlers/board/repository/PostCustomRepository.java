package com.hr_handlers.board.repository;

import com.hr_handlers.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {
    Page<Post> findActivePosts(Pageable pageable); // 활성화된 게시글 목록 조회
}
