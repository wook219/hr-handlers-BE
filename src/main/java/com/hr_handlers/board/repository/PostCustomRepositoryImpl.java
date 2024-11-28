package com.hr_handlers.board.repository;

import com.hr_handlers.board.entity.Post;
import com.hr_handlers.board.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> findActivePosts(Pageable pageable) {
        QPost post = QPost.post;

        // 삭제되지 않은 게시글만 조회
        List<Post> posts = queryFactory.selectFrom(post)
                .where(post.isDelete.eq("N"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 게시글 수 계산
        long total = queryFactory.selectFrom(post)
                .where(post.isDelete.eq("N"))
                .fetchCount();

        return new PageImpl<>(posts, pageable, total);
    }
}
