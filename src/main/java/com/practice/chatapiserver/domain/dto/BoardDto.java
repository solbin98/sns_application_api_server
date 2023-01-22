package com.practice.chatapiserver.domain.dto;

import com.practice.chatapiserver.domain.embeddable.TimeData;
import com.practice.chatapiserver.domain.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class BoardDto {
    private Long board_id;
    private String title;
    private String content;
    private Long member_id;
    private String nickname;
    private TimeData timeData;

    public BoardDto(Board board){
        this.board_id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.member_id = board.getMember().getId();
        this.nickname = board.getMember().getNickname();
        this.timeData = board.getTimeData();
    }
}
