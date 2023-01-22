package com.practice.chatapiserver.domain.controller;

import com.practice.chatapiserver.domain.dto.CommentDto;
import com.practice.chatapiserver.domain.dto.CommentUpdateDto;
import com.practice.chatapiserver.domain.dto.ResponseDto;
import com.practice.chatapiserver.domain.service.CommentService;
import com.practice.chatapiserver.util.RequestServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/board/{board_id}/comment*")
    public ResponseEntity getComment(@PathVariable Long board_id, @RequestParam int page) throws RuntimeException {
        List<CommentDto> commentList = commentService.getCommentDtoWithBoardIdAndPaging(board_id, page);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "댓글 불러오기 성공", commentList), HttpStatus.OK);
    }

    @DeleteMapping("/member/{member_id}/comment/{comment_id}")
    public ResponseEntity deleteComment(@PathVariable(name = "member_id") Long request_member_id,
                                        @PathVariable Long comment_id, HttpServletRequest request) throws RuntimeException {
        Long member_id = RequestServletUtil.getMemberId(request);
        commentService.deleteComment(comment_id, member_id, request_member_id);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "댓글 삭제 성공", null), HttpStatus.OK);
    }

    @PostMapping("/board/{board_id}/comment")
    public ResponseEntity addComment(@RequestBody String content,
                                     @PathVariable Long board_id,
                                     HttpServletRequest request) throws RuntimeException {
        Long member_id = RequestServletUtil.getMemberId(request);
        commentService.addComment(content, board_id, member_id);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "댓글 추가 성공", null), HttpStatus.OK);
    }

    @PostMapping("member/{member_id}/comment/{comment_id}")
    public ResponseEntity updateComment(@RequestBody String content,
                                        @PathVariable(name = "member_id") Long request_member_id,
                                        @PathVariable Long comment_id,
                                        HttpServletRequest request) throws RuntimeException {
        Long member_id = RequestServletUtil.getMemberId(request);
        commentService.updateComment(new CommentUpdateDto(comment_id, content), member_id, request_member_id);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "댓글 업데이트 성공", null), HttpStatus.OK);
    }
}
