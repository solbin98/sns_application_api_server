package com.practice.chatapiserver.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "PROFILE")
@Getter
@Setter
public class File_Profile extends File{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    public File_Profile(){}
}
