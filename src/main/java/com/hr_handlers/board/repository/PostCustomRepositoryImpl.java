package com.hr_handlers.board.repository;

import com.hr_handlers.board.entity.Post;
import com.hr_handlers.board.entity.QPost;
import com.querydsl.jpa.impl.JPAQuery;
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

        JPAQuery<Post> query = queryFactory.selectFrom(post)
                .where(post.isDelete.eq("N"));

        // 게시글 리스트 조회와 총 개수 계산
        List<Post> posts = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.fetchCount();

        return new PageImpl<>(posts, pageable, total);
    }

}
