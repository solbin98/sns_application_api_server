package com.practice.chatapiserver.domain.dto;

import com.practice.chatapiserver.domain.embeddable.TimeData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentDto {
    private Long comment_id;
    private Long board_id;
    private String content;
    private String nickname;
    private Long member_id;
    private TimeData timeData;
}
