package com.practice.chatapiserver.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Chat_Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;
    @Column(length = 45)
    private String title;
    @OneToMany(mappedBy = "chat_room")
    private List<Chat_Room_Member> chat_room_memberList;
    @OneToMany(mappedBy = "chat_room")
    private List<Chat_Room_History> chat_room_historyList;
    public Chat_Room() {}
}
