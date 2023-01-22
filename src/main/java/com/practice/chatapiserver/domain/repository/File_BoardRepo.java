package com.practice.chatapiserver.domain.repository;

import com.practice.chatapiserver.domain.entity.File;
import com.practice.chatapiserver.domain.entity.File_Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class File_BoardRepo {
    @PersistenceContext
    private EntityManager em;

    public File_Board findByFileId(Long file_id){
        return em.find(File_Board.class, file_id);
    }

    public List<File_Board> findByBoardId(Long board_id) {
        String query = "select fb from File_Board fb where fb.board.id =: board_id";
        return em.createQuery(query).setParameter("board_id", board_id).getResultList();
    }

    public void save(File_Board file_board) {
        em.persist(file_board);
    }
}
