package com.practice.chatapiserver.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.practice.chatapiserver.domain.embeddable.TimeData;
import com.practice.chatapiserver.domain.enums.GenderTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(length = 15)
    private String nickname;
    @Column(length = 25)
    private String username;
    @Column(length = 25)
    private String password;
    @Column(length = 45)
    private String email;
    @Enumerated(EnumType.STRING)
    private GenderTypeEnum gender;
    @Column(length = 45)
    private String address;
    @Column(length = 150)
    private String profile_msg;
    @Embedded
    private TimeData timeData;
    @DateTimeFormat
    private LocalDateTime last_visit;

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Likes> likesList = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Board> boardList = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Friend> friendList = new ArrayList<>();
    @OneToMany(mappedBy = "friend_member")
    @JsonIgnore
    private List<Friend> friend_memberList = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Chat_Room_Member> chat_room_memberList = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Chat_Room_History> chat_room_historyList = new ArrayList<>();

    public Member() {}
    public Member(Long member_id){
        this.id = member_id;
    }

    @Override
    public String toString() {
        return "Member{" +
                "member_id=" + id +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", profile_msg='" + profile_msg + '\'' +
                ", timeData=" + timeData +
                ", last_visit=" + last_visit +
                ", likesList=" + likesList +
                ", boardList=" + boardList +
                ", commentList=" + commentList +
                ", friendList=" + friendList +
                ", friend_memberList=" + friend_memberList +
                ", chat_room_memberList=" + chat_room_memberList +
                ", chat_room_historyList=" + chat_room_historyList +
                '}';
    }
}
