package com.practice.chatapiserver.domain.controller;

import com.practice.chatapiserver.domain.dto.ProfileDto;
import com.practice.chatapiserver.domain.dto.ResponseDto;
import com.practice.chatapiserver.domain.service.MemberService;
import com.practice.chatapiserver.util.RequestServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;
    @GetMapping("/member/{member_id}/profile")
    public ResponseEntity getProfileInfo(@PathVariable Long member_id){
        ProfileDto profileDto = memberService.getProfilePageInfo(member_id);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "프로필 조회 성공", profileDto), HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity updateProfileInfo(@RequestBody ProfileDto profileDto,
                                            HttpServletRequest request){
        Long member_id = RequestServletUtil.getMemberId(request);
        memberService.updateProfilePage(profileDto, member_id);
        return new ResponseEntity(new ResponseDto(), HttpStatus.OK);
    }

    @PostMapping("/member/{member_id}/profile-image")
    public ResponseEntity updateProfileImage(@RequestPart("profile_image") MultipartFile profile_image,
                                             @PathVariable Long member_id){
        memberService.updateProfileImage(profile_image, member_id);
        return new ResponseEntity(new ResponseDto(), HttpStatus.OK);
    }
}
