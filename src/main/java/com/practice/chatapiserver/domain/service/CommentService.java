package com.practice.chatapiserver.domain.service;

import com.practice.chatapiserver.ErrorCodeEnum;
import com.practice.chatapiserver.domain.dto.CommentDto;
import com.practice.chatapiserver.domain.dto.CommentUpdateDto;
import com.practice.chatapiserver.domain.embeddable.TimeData;
import com.practice.chatapiserver.domain.entity.Board;
import com.practice.chatapiserver.domain.entity.Comment;
import com.practice.chatapiserver.domain.entity.Member;
import com.practice.chatapiserver.domain.repository.CommentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;

    public List<CommentDto> getCommentDtoWithBoardIdAndPaging(Long board_id, int page) throws RuntimeException{
        List<Comment> list = commentRepo.getCommentWithBoardIdAndPaging(board_id, page);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(Comment c : list) commentDtoList.add(getCommentDtoFromComment(c));
        return commentDtoList;
    }

    public CommentDto getCommentDtoFromComment(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setComment_id(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setNickname(comment.getMember().getNickname());
        commentDto.setTimeData(comment.getTimeData());
        commentDto.setMember_id(comment.getMember().getId());
        return commentDto;
    }


    public void deleteComment(Long comment_id, Long member_id, Long request_member_id) {
        if(!member_id.equals(request_member_id)){
            throw new IllegalArgumentException(ErrorCodeEnum.INVALID_ARGUMENT.getMsg());
        }
        commentRepo.deleteComment(comment_id);
    }

    public void addComment(String content, Long board_id, Long member_id) {
        Comment comment = new Comment();
        comment.setBoard(new Board(board_id));
        comment.setContent(content);
        comment.setMember(new Member(member_id));
        comment.setTimeData(new TimeData());
        commentRepo.saveComment(comment);
    }

    public void updateComment(CommentUpdateDto commentUpdateDto, Long member_id, Long request_member_id) {
        if(!member_id.equals(request_member_id)){
            throw new IllegalArgumentException(ErrorCodeEnum.INVALID_ARGUMENT.getMsg());
        }
        Comment comment = new Comment();
        comment.setContent(commentUpdateDto.getContent());
        comment.setId(commentUpdateDto.getComment_id());
        commentRepo.updateComment(comment);
    }

}
