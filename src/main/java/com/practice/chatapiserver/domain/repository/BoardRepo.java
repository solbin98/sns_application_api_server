package com.practice.chatapiserver.domain.repository;

import com.practice.chatapiserver.domain.entity.Board;
import com.practice.chatapiserver.util.Paging;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
public class BoardRepo {
    @PersistenceContext
    private EntityManager em;

    public List<Board> getFriendsBoardWithMemberIdAndPaging(Long member_id, int page){
        String query = "select b from Friend f join fetch Board b on b.member.id = f.friend_member.id where f.friend_member.id =: member_id";
        return em.createQuery(query)
                .setParameter("member_id", member_id)
                .setFirstResult(Paging.getOffset(page))
                .setMaxResults(Paging.getLimit())
                .getResultList();
    }

    public List<Board> getBoardWithMemberId(Long member_id, int page){
        String query = "select b from Board b where b.member.id =: member_id";
        return em.createQuery(query)
                .setParameter("member_id", member_id)
                .setFirstResult(Paging.getOffset(page))
                .setMaxResults(Paging.getLimit())
                .getResultList();
    }

    public void saveBoard(Board board){
        em.persist(board);
    }

    public void deleteById(Long board_id){
        Board board = em.find(Board.class, board_id);
        em.remove(board);
    }
    public void updateBoard(Board board) {
        Board prevBoard = em.find(Board.class, board.getId());
        prevBoard.setContent(board.getContent());
        prevBoard.setTitle(board.getTitle());
        prevBoard.getTimeData().setModified_at(LocalDateTime.now());
    }

    public void flush(){
        em.flush();
    }

    public void clear(){
        em.clear();
    }
}
