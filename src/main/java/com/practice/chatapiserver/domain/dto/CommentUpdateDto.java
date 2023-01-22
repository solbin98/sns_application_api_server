package com.practice.chatapiserver.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateDto {
    private String content;
    private Long comment_id;

    public CommentUpdateDto(Long comment_id, String content) {
        this.content = content;
        this.comment_id = comment_id;
    }
}
