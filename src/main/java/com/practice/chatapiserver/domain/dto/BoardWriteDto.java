package com.practice.chatapiserver.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardWriteDto {
    private String title;
    private String content;
    private List<MultipartFile> imageList;

    public BoardWriteDto(String title, String content, List<MultipartFile> imageList) {
        this.title = title;
        this.content = content;
        this.imageList = imageList;
    }
}
