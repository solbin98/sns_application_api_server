package com.practice.chatapiserver.domain.repository;

import com.practice.chatapiserver.domain.entity.File;
import com.practice.chatapiserver.domain.entity.File_Profile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class File_ProfileRepo {
    @PersistenceContext
    private EntityManager em;

    public File_Profile findByFileId(Long file_id){
        return em.find(File_Profile.class, file_id);
    }

    public List<File_Profile> findByMemberId(Long member_id){
        String query = "select fp From File_Profile fp where fp.member.id =: member_id";
        return em.createQuery(query).setParameter("member_id", member_id).getResultList();
    }

    public void save(File_Profile fileProfile) {
        em.persist(fileProfile);
    }
}
