package com.practice.chatapiserver.domain.controller;

import com.practice.chatapiserver.domain.dto.FriendMemberDto;
import com.practice.chatapiserver.domain.dto.ResponseDto;
import com.practice.chatapiserver.domain.service.FriendService;
import com.practice.chatapiserver.util.RequestServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendController {
    @Autowired
    private FriendService friendService;

    @GetMapping("/friend")
    public ResponseEntity getAllFriendList(HttpServletRequest request){
        Long member_id = RequestServletUtil.getMemberId(request);
        List<FriendMemberDto> friendList = friendService.getFriendMemberDtoList(member_id);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "친구 목록 요청 성공" ,friendList), HttpStatus.OK);
    }

    @GetMapping("/friend?*")
    public ResponseEntity getFriendListWithPagingInfo(@RequestParam("page") int page,  HttpServletRequest request){
        Long member_id = RequestServletUtil.getMemberId(request);
        List<FriendMemberDto> friendList = friendService.getFriendMemberDtoListWithPageNum(member_id, page);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "친구 목록 요청 성공 - 페이징" ,friendList), HttpStatus.OK);
    }

    @DeleteMapping("/friend/{friend_id}")
    public ResponseEntity deleteFriend(@PathVariable Long friend_id, HttpServletRequest request){
        Long member_id = RequestServletUtil.getMemberId(request);
        friendService.deleteFriendShip(member_id, friend_id);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "친구 삭제 요청 성공"), HttpStatus.OK);
    }

    @PostMapping("/friend*")
    public ResponseEntity addFriend(@RequestParam Long friend_member_id, HttpServletRequest request){
        Long member_id = RequestServletUtil.getMemberId(request);
        friendService.addFriend(member_id, friend_member_id);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "친구 추가 요청 성공"), HttpStatus.OK);
    }
}
