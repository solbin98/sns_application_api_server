package com.practice.chatapiserver.domain.controller;

import com.practice.chatapiserver.domain.dto.BoardDto;
import com.practice.chatapiserver.domain.dto.BoardWriteDto;
import com.practice.chatapiserver.domain.dto.ResponseDto;
import com.practice.chatapiserver.domain.service.BoardService;
import com.practice.chatapiserver.util.RequestServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board*")
    public ResponseEntity getFriendsBoard(@RequestParam(name = "page") int page,
                                          HttpServletRequest request){
        Long member_id = RequestServletUtil.getMemberId(request);
        List<BoardDto> list = boardService.getFriendBoardWithMemberIdAndPaging(member_id, page);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "게시글 조회 성공", list), HttpStatus.OK);
    }

    @GetMapping("/member/{member_id}/board*")
    public ResponseEntity getSpecificMembersBoard(@RequestParam(name = "page") int page,
                                                  @PathVariable(name = "member_id") Long member_id){
        List<BoardDto> list = boardService.getBoardWithMemberIdAndPaging(member_id, page);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "특정 멤버 게시글 조회 성공", list), HttpStatus.OK);
    }

    @PostMapping("/board")
    public ResponseEntity addBoard(@RequestPart String title,
                                   @RequestPart String content,
                                   @RequestPart @Nullable List<MultipartFile> imageList,
                                   HttpServletRequest request){
        BoardWriteDto boardWriteDto = new BoardWriteDto(title, content, imageList);
        Long member_id = RequestServletUtil.getMemberId(request);
        boardService.addBoard(boardWriteDto, member_id);
        return new ResponseEntity(new ResponseDto(HttpStatus.CREATED.value(), "게시글 작성 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/member/{member_id}/board/{board_id}")
    public ResponseEntity deleteBoard(@PathVariable Long board_id,
                                      @PathVariable(name = "member_id") Long request_member_id,
                                      HttpServletRequest request) {

        Long member_id = RequestServletUtil.getMemberId(request);
        boardService.deleteBoard(board_id, member_id, request_member_id);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "게시글 삭제 성공", null), HttpStatus.OK);
    }

    @PostMapping("/board/{board_id}")
    public ResponseEntity updateBoard(@RequestBody BoardDto boardDto, HttpServletRequest request, @PathVariable String board_id){
        Long member_id = RequestServletUtil.getMemberId(request);
        boardService.updateBoard(boardDto, member_id);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "게시글 수정 성공", null), HttpStatus.OK);
    }

    @PostMapping("board/{board_id}/likes")
    public ResponseEntity addLike(@PathVariable Long board_id, HttpServletRequest request){
        Long member_id = RequestServletUtil.getMemberId(request);
        boardService.addLike(board_id, member_id);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "좋아요 요청 성공", null), HttpStatus.OK);
    }

}
