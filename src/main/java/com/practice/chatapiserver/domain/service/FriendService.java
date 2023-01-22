package com.practice.chatapiserver.domain.service;

import com.practice.chatapiserver.ErrorCodeEnum;
import com.practice.chatapiserver.domain.dto.FriendMemberDto;
import com.practice.chatapiserver.domain.entity.Friend;
import com.practice.chatapiserver.domain.entity.Member;
import com.practice.chatapiserver.domain.repository.FriendRepo;
import com.practice.chatapiserver.domain.repository.MemberRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class FriendService {
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private FriendRepo friendRepo;
    public void deleteFriendShip(Long member_id, Long friend_id) throws IllegalArgumentException{
       Friend friend = friendRepo.findById(friend_id);
       if(friend.getMember().getId() != member_id){ // 삭제 요청을 보낸 사람이 본인인지 확인하기
           throw new IllegalArgumentException(ErrorCodeEnum.INVALID_ARGUMENT.getMsg());
       }
       friendRepo.removeById(friend_id);
    }

    public List<Member> getMemberList(Long member_id){
        List<Friend> friendShipList = friendRepo.getFriendListAndFetchJoinWithFriendMember(member_id);
        return getMemberListFromFriendList(friendShipList);
    }

    public List<FriendMemberDto> getFriendMemberDtoList(Long member_id){
        List<Friend> friendShipList = friendRepo.getFriendListAndFetchJoinWithFriendMember(member_id);
        return getFriendMemberDtoListFromFriendList(friendShipList);
    }

    public List<FriendMemberDto> getFriendMemberDtoListWithPageNum(Long member_id, int perPage){
        List<Friend> friendShipList = friendRepo.getFriendListAndFetchJoinWithFriendMemberAndPageInfo(member_id, perPage);
        return getFriendMemberDtoListFromFriendList(friendShipList);
    }

    public List<FriendMemberDto> getFriendMemberDtoListFromFriendList(List<Friend> list){
        List<FriendMemberDto> friendMemberList = new ArrayList<>();
        for(Friend f : list){
            Member friend = f.getFriend_member();
            friendMemberList.add(new FriendMemberDto(f.getId(),
                                    friend.getId(),
                                    friend.getNickname(),
                                    friend.getProfile_msg()));
        }
        return friendMemberList;
    }

    public List<Member> getMemberListFromFriendList(List<Friend> list){
        List<Member> friendMemberList = new ArrayList<>();
        for(Friend f : list){
            Member friend = f.getFriend_member();
            friendMemberList.add(friend);
        }
        return friendMemberList;
    }

    public void addFriend(Long member_id, Long friend_member_id){
        Friend friend = new Friend();
        friend.setMember(new Member(member_id));
        friend.setFriend_member(new Member(friend_member_id));
        friendRepo.add(friend);
    }
}
