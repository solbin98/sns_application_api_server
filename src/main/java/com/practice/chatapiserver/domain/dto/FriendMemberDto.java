package com.practice.chatapiserver.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FriendMemberDto {
    private Long friend_id;
    private Long member_id;
    private String nickname;
    private String profile_msg;

    public FriendMemberDto(Long id, Long memberId, String nickName, String profileMsg) {
        this.friend_id = id;
        this.member_id = memberId;
        this.nickname = nickName;
        this.profile_msg = profileMsg;
    }
}
