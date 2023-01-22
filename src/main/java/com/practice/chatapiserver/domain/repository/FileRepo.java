package com.practice.chatapiserver.domain.repository;

import com.practice.chatapiserver.domain.entity.File;
import com.practice.chatapiserver.domain.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepo {
    @PersistenceContext
    private EntityManager em;

    public File findByMemberId(Long member_id){

        return em.find(File.class, member_id);
    }
}
