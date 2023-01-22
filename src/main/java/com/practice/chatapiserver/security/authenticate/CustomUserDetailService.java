package com.practice.chatapiserver.security.authenticate;

import com.practice.chatapiserver.domain.entity.Member;
import com.practice.chatapiserver.domain.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    MemberRepo memberRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepo.findByUserName(username);
        CustomUserDetail customUserDetail = new CustomUserDetail(member);
        return customUserDetail;
    }
}
