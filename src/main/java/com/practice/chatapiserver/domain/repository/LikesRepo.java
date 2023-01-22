package com.practice.chatapiserver.domain.repository;

import com.practice.chatapiserver.domain.entity.Likes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LikesRepo {
    @PersistenceContext
    private EntityManager em;
    public void saveLikes(Likes likes) {
        em.persist(likes);
    }

    public Likes findByMemberIdAndBoardId(Long member_id, Long board_id){
        return em.createQuery("select lk from Likes lk where lk.member.id =: member_id and lk.board.id =: board_id", Likes.class)
                .setParameter("member_id", member_id)
                .setParameter("board_id",board_id)
                .getSingleResult();
    }

    public void remove(Likes likes) {
        em.remove(likes);
    }
}
