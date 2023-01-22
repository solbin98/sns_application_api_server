package com.practice.chatapiserver.domain.service;

import com.practice.chatapiserver.ErrorCodeEnum;
import com.practice.chatapiserver.domain.dto.JoinDto;
import com.practice.chatapiserver.domain.embeddable.TimeData;
import com.practice.chatapiserver.domain.entity.Friend;
import com.practice.chatapiserver.domain.entity.Member;
import com.practice.chatapiserver.domain.repository.FriendRepo;
import com.practice.chatapiserver.domain.repository.MemberRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class JoinService {
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private FriendRepo friendRepo;

    final static private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public void saveMember(JoinDto joinDto) throws Exception{
        Member member = memberRepo.findByUserName(joinDto.getUsername());
        if(member != null){ // 이미 존재하는 회원 아이디로는 가입 불가능
            throw new IllegalArgumentException(ErrorCodeEnum.MEMBER_ALREADY_EXISTS.getMsg());
        }

        if(!joinDto.getPassword().equals(joinDto.getPasswordAgain())){ // 비밀번호 재입력 확인하기
            throw new IllegalArgumentException(ErrorCodeEnum.PASSWORD_INCONSISTENCY.getMsg());
        }

        Member newMember = new Member();
        newMember.setAddress(joinDto.getAddress());
        newMember.setEmail(joinDto.getEmail());
        newMember.setPassword(passwordEncoder.encode(joinDto.getPassword())); // 인코딩 해서 저장하기
        newMember.setNickname(joinDto.getNickname());
        newMember.setUsername(joinDto.getUsername());
        newMember.setTimeData(new TimeData());

        Friend newFriend = new Friend();
        newFriend.setMember(new Member());
        memberRepo.saveMember(newMember);
    }
}
