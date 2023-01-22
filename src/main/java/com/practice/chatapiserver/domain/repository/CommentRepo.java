package com.practice.chatapiserver.domain.repository;

import com.practice.chatapiserver.domain.entity.Comment;
import com.practice.chatapiserver.util.Paging;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CommentRepo {
    @PersistenceContext
    private EntityManager em;

    public List<Comment> getCommentWithBoardIdAndPaging(Long board_id, int page) throws RuntimeException{
        return em.createQuery("select c from Comment c where c.board.id =: board_id")
                .setFirstResult(Paging.getOffset(page))
                .setMaxResults(Paging.getLimit())
                .getResultList();
    }

    public void deleteComment(Long comment_id) {
        Comment comment = em.find(Comment.class, comment_id);
        em.remove(comment);
    }

    public void saveComment(Comment comment) {
        em.persist(comment);
    }

    public void updateComment(Comment comment){
        Comment prevComment = em.find(Comment.class, comment.getId());
        prevComment.setContent(comment.getContent());
        prevComment.getTimeData().setModified_at(LocalDateTime.now());
    }
}
