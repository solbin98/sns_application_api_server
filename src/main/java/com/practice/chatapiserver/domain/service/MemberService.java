package com.practice.chatapiserver.domain.service;

import com.practice.chatapiserver.ErrorCodeEnum;
import com.practice.chatapiserver.domain.dto.ProfileDto;
import com.practice.chatapiserver.domain.embeddable.TimeData;
import com.practice.chatapiserver.domain.entity.File_Profile;
import com.practice.chatapiserver.domain.entity.Member;
import com.practice.chatapiserver.domain.repository.File_ProfileRepo;
import com.practice.chatapiserver.domain.repository.MemberRepo;
import com.practice.chatapiserver.util.ImageUploadManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MemberService {
    @Autowired
    MemberRepo memberRepo;
    @Autowired
    File_ProfileRepo file_profileRepo;

    public ProfileDto getProfilePageInfo(Long member_id) {
        Member member = memberRepo.findById(member_id);
        ProfileDto profileDto = new ProfileDto();
        profileDto.setProfile_msg(member.getProfile_msg());
        profileDto.setNickName(member.getNickname());
        profileDto.setMember_id(member.getId());
        return profileDto;
    }

    public void updateProfilePage(ProfileDto profileDto, Long member_id) {
        if(!profileDto.getMember_id().equals(member_id)){
            throw new IllegalArgumentException(ErrorCodeEnum.INVALID_ARGUMENT.getMsg());
        }
        memberRepo.updateWithProfileInfo(profileDto);
    }

    public void updateProfileImage(MultipartFile profileImage, Long member_id) {
        // 기존의 프로필 이미지 파일을 가르키는 row 들의 used 칼럼을 모두 false 로 바꿈
        // -> 추후에 스케쥴러 만들어서 used == false 인 row 들 제거하고, 그에 대응하는 이미지 파일들도 삭제 시켜 줄 것
        List<File_Profile> list = file_profileRepo.findByMemberId(member_id);
        for(File_Profile fp : list) fp.setUsed(false);

        File_Profile file_profile = new File_Profile();
        file_profile.setMember(new Member(member_id));
        file_profile.setUsed(true);
        file_profile.setSize((double) profileImage.getSize());
        file_profile.setName(UUID.randomUUID().toString() + "_" + profileImage.getName());
        file_profile.setTimeData(new TimeData());
        file_profile.setPath(""); // 이 부분 s3 공부한 다음에 연동
        ImageUploadManager.saveImage(); // 이 부분도

        file_profileRepo.save(file_profile);
    }
}
