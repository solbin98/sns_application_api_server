package com.practice.chatapiserver.domain.repository;

import com.practice.chatapiserver.domain.entity.Friend;
import com.practice.chatapiserver.domain.entity.Member;
import com.practice.chatapiserver.util.Paging;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
public class FriendRepo {
    @PersistenceContext
    private EntityManager em;

    public Friend findById(Long friend_id){
        return em.find(Friend.class, friend_id);
    }

    public List<Friend> getFriendListAndFetchJoinWithFriendMember(Long member_id){
        String query = "select f from Friend f join fetch f.friend_member where f.member.id =: member_id";
        return em.createQuery(query).setParameter("member_id", member_id).getResultList();
    }

    public List<Friend> getFriendListAndFetchJoinWithFriendMemberAndPageInfo(Long member_id, int pageNum){
        String query = "select f from Friend f join fetch f.friend_member where f.member.id =: member_id";
        return em.createQuery(query).setParameter("member_id", member_id)
                .setFirstResult(Paging.getOffset(pageNum))
                .setMaxResults(Paging.getLimit())
                .getResultList();
    }

    public void add(Friend friend){
        em.persist(friend);
    }
    public void removeById(Long friend_id){
        Friend friend = em.find(Friend.class,friend_id);
        em.remove(friend);
    }
}
