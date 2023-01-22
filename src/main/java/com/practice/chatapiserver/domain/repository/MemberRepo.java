package com.practice.chatapiserver.domain.repository;

import com.practice.chatapiserver.ErrorCodeEnum;
import com.practice.chatapiserver.domain.dto.ProfileDto;
import com.practice.chatapiserver.domain.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepo {
    @PersistenceContext
    private EntityManager em;

    public void saveMember(Member member){
        em.persist(member);
    }

    public Member findByUserName(String username) throws RuntimeException{
        try{
            return em.createQuery("select m from Member m where m.username =:username", Member.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }
        catch (NoResultException e){
            throw new NoResultException(ErrorCodeEnum.MEMBER_NOT_FOUND.getMsg());
        }
    }

    public Member findById(Long member_id){
        return em.find(Member.class, member_id);
    }

    public void updateWithProfileInfo(ProfileDto profileDto) {
        Member member = em.find(Member.class, profileDto.getMember_id());
        member.setProfile_msg(profileDto.getProfile_msg());
        member.setNickname(profileDto.getNickName());
    }
}
