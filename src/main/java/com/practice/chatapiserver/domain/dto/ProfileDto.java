package com.practice.chatapiserver.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {
    private Long member_id;
    private String nickName;
    private String profile_msg;
}
